
package model.outbound.response;

import org.apache.commons.lang3.builder.ToStringBuilder;

import serializer.Serializer;

public class Payload {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return Serializer.getInstance().toJson(this);
    }

}
