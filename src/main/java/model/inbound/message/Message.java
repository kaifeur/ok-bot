
package model.inbound.message;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;

import serializer.Serializer;

public class Message {

    private String text;
    private String seq;
    private List<Attachment> attachments = null;
    private String mid;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    @Override
    public String toString() {
        return Serializer.getInstance().toJson(this);
    }

}
