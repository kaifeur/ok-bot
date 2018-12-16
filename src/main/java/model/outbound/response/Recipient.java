
package model.outbound.response;

import org.apache.commons.lang3.builder.ToStringBuilder;

import serializer.Serializer;

public class Recipient {

    private String chat_id;

    public Recipient(String chatId) {
        this.chat_id = chatId;
    }

    public String getChatId() {
        return chat_id;
    }

    public void setChatId(String chatId) {
        this.chat_id = chatId;
    }

    @Override
    public String toString() {
        return Serializer.getInstance().toJson(this);
    }

}

