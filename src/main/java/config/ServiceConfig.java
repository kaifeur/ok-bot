package config;

import java.util.Properties;

import one.nio.http.HttpServerConfig;

public class ServiceConfig {
    public Properties bot = new Properties();
    public Properties templates = new Properties();
    public Properties host = new Properties();
    public HttpServerConfig httpServerConfig;

    public ServiceConfig() {
        httpServerConfig = new HttpServerConfig();
    }
}
