
package model.outbound.response;

import org.apache.commons.lang3.builder.ToStringBuilder;

import serializer.Serializer;

public class Attachment {

    private String type;
    private Payload payload;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return Serializer.getInstance().toJson(this);
    }

}
