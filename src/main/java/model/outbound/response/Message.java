
package model.outbound.response;

import org.apache.commons.lang3.builder.ToStringBuilder;

import serializer.Serializer;

public class Message {

    private String text;
    private Attachment attachment;

    public Message(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return Serializer.getInstance().toJson(this);
    }

}
