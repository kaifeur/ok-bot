package service;

import model.inbound.message.MessageEntity;
import one.nio.http.HttpClient;

abstract public class TaskFactory {
    public abstract Runnable produce(MessageEntity message);
}
