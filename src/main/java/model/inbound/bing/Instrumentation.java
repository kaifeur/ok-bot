
package model.inbound.bing;

import serializer.Serializer;

public class Instrumentation {

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return Serializer.getInstance().toJson(this);
    }

}
