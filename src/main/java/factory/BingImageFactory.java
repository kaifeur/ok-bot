package factory;

import model.inbound.message.MessageEntity;
import model.outbound.response.Attachment;
import model.outbound.response.Message;
import model.outbound.response.Payload;
import model.outbound.response.Recipient;
import one.nio.http.HttpClient;
import one.nio.http.Response;
import one.nio.net.ConnectionString;

import org.apache.log4j.Logger;

import serializer.Serializer;
import service.TaskFactory;

import java.net.URLEncoder;
import java.util.Properties;

import static properties.PropsList.*;

public class BingImageFactory extends TaskFactory {
    private final Properties templates;
    private final Properties bingProps;
    private static String TOKEN;
    private HttpClient bingClient;
    private HttpClient okClient;

    private final static Logger LOGGER = Logger.getLogger(Task.class);

    public BingImageFactory(final Properties templates, final Properties bingProps, final String token) {
        this.templates = templates;
        this.bingProps = bingProps;
        this.TOKEN = token;
        this.bingClient = new HttpClient(new ConnectionString(
                new StringBuilder()
                        .append(templates.getProperty(SCHEMA_HTTPS))
                        .append(bingProps.getProperty(BING_HOST))
                        .toString()
        ));
        this.okClient = new HttpClient(new ConnectionString(
                new StringBuilder()
                        .append(templates.getProperty(SCHEMA_HTTP))
                        .append(templates.getProperty(DOMAIN))
                        .toString()
        ));
    }

    @Override
    public Runnable produce(final MessageEntity message) {
        return new Task(message, this.bingClient, this.okClient);
    }

    private class Task implements Runnable {
        private final HttpClient bingClient;
        private final HttpClient okClient;
        private final MessageEntity message;

        public Task(final MessageEntity message, final HttpClient bingClient, final HttpClient okClient) {
            this.bingClient = bingClient;
            this.okClient = okClient;
            this.message = message;
        }

        @Override
        public void run() {
            model.outbound.response.Response okResponse =
                    new model.outbound.response.Response(
                            new Recipient(message.getRecipient().getChatId()),
                            new model.outbound.response.Message(message.getMessage().getText()));
            try {

                String uriBing = new StringBuilder()
                        .append(bingProps.getProperty(BING_PATH))
                        .append("?q=")
                        .append(URLEncoder.encode(okResponse.getMessage().getText(), "UTF-8"))
                        .append(bingProps.getProperty(BING_COUNT))
                        .toString();
                String bingHeader = bingProps.getProperty(BING_HEADER)
                        + bingProps.getProperty(BING_API_TOKEN);

                Response resultBing =
                        this.bingClient.get(uriBing, bingHeader);

                LOGGER.info("Bing response was getted.");

                if (resultBing.getStatus() != 200) {
                    LOGGER.error("Bing error!");
                    sendMessage(okClient, "Error: " + resultBing.getBodyUtf8(), message.getRecipient().getChatId());
                    throw new IllegalArgumentException(resultBing.toString());
                }

                LOGGER.info("Bing response: ok.");

                model.inbound.bing.Response bingResponse = Serializer.getInstance().fromJson(resultBing.getBodyUtf8(), model.inbound.bing.Response.class);

                for (int i = 0; i < bingResponse.getValue().size(); i++) {
                    LOGGER.info("Image number: " + i);
                    sendMessage(okClient, bingResponse.getValue().get(i).getContentUrl(), message.getRecipient().getChatId());
                }

                LOGGER.info("All images were sent!");

            } catch (IllegalArgumentException iAE) {
                LOGGER.error("Server respond with error: " + iAE.getMessage());

            } catch (Exception e) {
                LOGGER.error(e.toString());
            }
        }

        private void sendMessage(HttpClient client, String text, String chatId) throws Exception {
            String uriOk = new StringBuilder()
                    .append(templates.getProperty(API_GRAPH_ME))
                    .append(templates.getProperty(API_MESSAGES))
                    .append(chatId)
                    .append(templates.getProperty(QUERY_SPLITTER))
                    .append(templates.getProperty(API_ACCESS_TOKEN))
                    .append(TOKEN)
                    .toString();

            Message message = new Message("");
            Attachment attachment = new Attachment();
            Payload payload = new Payload();
            payload.setUrl(text);
            attachment.setType("IMAGE");
            attachment.setPayload(payload);
            message.setAttachment(attachment);
            Recipient recipient = new Recipient(chatId);
            model.outbound.response.Response response = new model.outbound.response.Response(recipient, message);

            Response serverResponse = new Response(
                    this.okClient.post(uriOk,
                            response.toString().getBytes(),
                            templates.getProperty(HTTP_POST_HEADER)));

            if (serverResponse.getStatus() != 200) {
                throw new IllegalArgumentException(serverResponse.toString());
            }

            if (serverResponse.getBodyUtf8().contains("error_code")) {
                LOGGER.error(serverResponse.getBodyUtf8());
            } else {
                LOGGER.info("okServerResponse: ok");
            }
        }

    }
}