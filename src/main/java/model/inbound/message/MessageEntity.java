
package model.inbound.message;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.Gson;

import serializer.Serializer;

public class MessageEntity {

    private Sender sender;
    private Recipient recipient;
    private Message message;
    private String timestamp;

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return Serializer.getInstance().toJson(this);
    }

}
