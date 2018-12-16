import java.util.Properties;

import config.ServiceConfig;
import factory.BingImageFactory;
import one.nio.server.AcceptorConfig;
import service.Service;

import static properties.PropsList.*;

public class Main {
    private final static String CONFIG_BOT_DEFAULT = "bot.properties";
    private final static String CONFIG_SERVER_DEFAULT = "server.properties";
    private final static String TEMPLATES_DEFAULT = "templates.properties";
    private final static String BING_DEFAULT = "bing.properties";

    public static void main(String[] args) {
        ServiceConfig config = new ServiceConfig();
        try {
            config.bot.load(ClassLoader.getSystemResourceAsStream(CONFIG_BOT_DEFAULT));
            config.templates.load(ClassLoader.getSystemResourceAsStream(TEMPLATES_DEFAULT));
            config.host.load(ClassLoader.getSystemResourceAsStream(CONFIG_SERVER_DEFAULT));
            AcceptorConfig acceptorConfig = new AcceptorConfig();
            acceptorConfig.port = Integer.parseInt(config.host.getProperty(SERVER_PORT));
            config.httpServerConfig.acceptors = new AcceptorConfig[]{acceptorConfig};
            Properties bingProps = new Properties();
            bingProps.load(ClassLoader.getSystemResourceAsStream(BING_DEFAULT));
            Service service = new Service(config,
                    new BingImageFactory(config.templates,
                            bingProps,
                            config.bot.getProperty(OK_BOT_TOKEN)));
            service.start();
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    service.stop();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
