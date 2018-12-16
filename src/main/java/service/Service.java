package service;

import config.ServiceConfig;
import model.inbound.message.MessageEntity;
import model.inbound.subscription.Confirmation;
import model.outbound.subscription.Subscription;
import model.outbound.unsubscription.Unsubscription;
import one.nio.http.*;
import one.nio.net.ConnectionString;
import org.apache.log4j.Logger;
import serializer.Serializer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.gson.JsonSyntaxException;

import static properties.PropsList.*;

public class Service extends HttpServer {
    private final TaskFactory factory;
    private final ExecutorService localThreadPool;
    private Properties botProp;
    private Properties templates;
    private Properties serverProp;
    private HttpClient subscriber;
    private final String API0 = "/v0/api";
    private long executorShutdownTimeout = 5;

    private final Logger logger = Logger.getLogger(this.getClass());

    public Service(final ServiceConfig config, final TaskFactory factory) throws IOException {
        super(config.httpServerConfig);
        this.botProp = config.bot;
        this.templates = config.templates;
        this.serverProp = config.host;
        this.factory = factory;

        int threads = 0;

        try {
            threads = Integer.parseInt(this.serverProp.getProperty(SERVER_EXECUTOR_THREADS));
            if (threads < 0) throw new IllegalArgumentException();
        } catch (IllegalArgumentException iAE) {
            threads = 0;
            this.logger.warn("Threads number incorrect, now equals " + Runtime.getRuntime().availableProcessors());
        }

        this.localThreadPool = Executors.newFixedThreadPool(
                threads > 0 ? threads : Runtime.getRuntime().availableProcessors()
        );

        long executorTimeout = 0;

        try {
            executorTimeout = Long.parseLong(serverProp.getProperty(SERVER_EXECUTOR_SHUTDOWN_TIMEOUT));
            if (executorTimeout < 0) throw new IllegalArgumentException();
            this.executorShutdownTimeout = executorTimeout;
        } catch (NumberFormatException nFE) {
            this.logger.warn("Executor shutdown timeout: " + executorShutdownTimeout);
        } catch (IllegalArgumentException iAE) {
            this.logger.warn("Executor shutdown timeout less then 0");
        }

        String endPoint = new StringBuilder()
                .append(templates.getProperty(SCHEMA_HTTP))
                .append(templates.getProperty(DOMAIN))
                .toString();


        this.subscriber = new HttpClient(new ConnectionString(endPoint));
    }

    @Override
    public synchronized void start() {
        this.logger.info("Hi! One-nio ok-bot is here!");
        super.start();
        this.logger.info("Service is up");
        try {
            init();
        } catch (Exception e) {
            super.stop();
            this.logger.info("Server is down");
            return;
        }
    }

    @Override
    public synchronized void stop() {
        tearDown();
        this.logger.info("Stoping service...");
        super.stop();
        this.logger.info("Service is down");
        try {
            this.logger.info("ExecutorService is shutting down("
                    + this.executorShutdownTimeout
                    + " "
                    + TimeUnit.SECONDS.name()
                    + ")...");
            this.localThreadPool.awaitTermination(this.executorShutdownTimeout, TimeUnit.SECONDS);
            this.logger.info("ExecutorService is down");
        } catch (InterruptedException iE) {
            this.logger.error("Can't stop ExecutorService: " + iE.toString());
        }
        this.logger.info("Bye!");
    }

    private void init() throws IllegalStateException{
        try {
            Subscription subscription = new Subscription();
            subscription.setUrl(
                    new StringBuilder()
                            .append(templates.getProperty(SCHEMA_HTTP))
                            .append(serverProp.getProperty(SERVER_HOST))
                            .append(templates.getProperty(HOST_SPLITTER))
                            .append(serverProp.getProperty(SERVER_PORT))
                            .append(serverProp.getProperty(SERVER_URI_V0))
                            .toString());

            String uri = new StringBuilder()
                    .append(templates.getProperty(API_GRAPH_ME))
                    .append(templates.getProperty(API_SUBSCRIBE))
                    .append(templates.getProperty(QUERY_SPLITTER))
                    .append(templates.getProperty(API_ACCESS_TOKEN))
                    .append(botProp.getProperty(OK_BOT_TOKEN))
                    .toString();

            this.logger.info("Subscribing: " + subscription.getUrl());

            Response response =
                    this.subscriber
                            .post(uri,
                                    subscription.toString().getBytes(),
                                    templates.getProperty(HTTP_POST_HEADER));

            Confirmation confirmation = Serializer.getInstance().fromJson(response.getBodyUtf8(), Confirmation.class);

            this.logger.info("Subscribed: " + confirmation.isSuccess());

            if (!confirmation.isSuccess()) throw new IllegalStateException();

        } catch (Exception e) {
            this.logger.error("Subscribed: " + false);
            this.logger.error(e.getMessage());
            throw new IllegalStateException(e.getMessage());
        }
    }

    private void tearDown() {
        try {
            Unsubscription unsubscription = new Unsubscription();
            unsubscription.setUrl(
                    new StringBuilder()
                            .append(templates.getProperty(SCHEMA_HTTP))
                            .append(serverProp.getProperty(SERVER_HOST))
                            .append(templates.getProperty(HOST_SPLITTER))
                            .append(serverProp.getProperty(SERVER_PORT))
                            .append(serverProp.getProperty(SERVER_URI_V0))
                            .toString());

            this.logger.info("Unsubscribing: " + unsubscription.getUrl());

            Response response =
                    this.subscriber
                            .post(new StringBuilder()
                                            .append(templates.getProperty(API_GRAPH_ME))
                                            .append(templates.getProperty(API_UNSUBSCRIBE))
                                            .append(templates.getProperty(QUERY_SPLITTER))
                                            .append(templates.getProperty(API_ACCESS_TOKEN))
                                            .append(botProp.getProperty(OK_BOT_TOKEN))
                                            .toString(),
                                    unsubscription.toString().getBytes(),
                                    templates.getProperty(HTTP_POST_HEADER));

            Confirmation confirmation = Serializer.getInstance().fromJson(response.getBodyUtf8(), Confirmation.class);
            
            if (!confirmation.isSuccess()) throw new IllegalStateException();

            this.logger.info("Unsubscribed: " + confirmation.isSuccess());

        } catch (Exception e) {
            this.logger.error("Unsubscribed: false");
            this.logger.error(e.getMessage());
        }
    }

    @Path("/status")
    public void status(Request request, HttpSession session) throws IOException{
        if (request.getMethod() == Request.METHOD_GET) {
            session.sendResponse(new Response(Response.OK, Response.EMPTY));
        } else {
            session.sendError(Response.BAD_REQUEST, null);
        }
    }

    @Path(API0)
    public void api0(Request request, HttpSession session) throws IOException, IllegalAccessException {
        try {
            if (!isInRange(session.getRemoteHost())) {
                throw new IllegalAccessException("Host ip isn't in valid range.");
            }

            if (request.getMethod() != Request.METHOD_POST) {
                session.sendError(Response.BAD_REQUEST, null);
                return;
            }

            session.sendResponse(new Response(Response.OK, Response.EMPTY));

            MessageEntity message = Serializer.getInstance().fromJson(new String(request.getBody()), MessageEntity.class);

            this.logger.info("Message by " + message.getSender().getUser_id()
                    + " has text: " + (message.getMessage().getText() != null)
                    + " has attachment: " + (message.getMessage().getAttachments() != null));

            this.localThreadPool.execute(this.factory.produce(message));
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
        }
    }

    private boolean isInRange(String remoteHost) {
        long result = 0;
        String[] ipAddressInArray = remoteHost.split("\\.");

        long[] validIp = new long[3];
        validIp[0] = Long.parseLong(serverProp.getProperty(SERVER_VALID_IP_1));
        validIp[1] = Long.parseLong(serverProp.getProperty(SERVER_VALID_IP_2));
        validIp[2] = Long.parseLong(serverProp.getProperty(SERVER_VALID_IP_3));

        for (int i = 3; i >= 0; i--) {
            long ip = Long.parseLong(ipAddressInArray[3 - i]);
            result |= ip << (i * 8);
        }

        for (int i = 0; i < validIp.length; i++) {
            if ((result >= validIp[i]) && (result <= (validIp[i] + 15))) {
                return true;
            }
        }

        return false;
    }
}
