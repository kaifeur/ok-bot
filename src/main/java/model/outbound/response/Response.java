
package model.outbound.response;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.Gson;

import serializer.Serializer;

public class Response {

    private Recipient recipient;
    private Message message;

    public Response(Recipient recipient, Message message) {
        this.recipient = recipient;
        this.message = message;
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

    @Override
    public String toString() {
        return Serializer.getInstance().toJson(this);
    }

}
