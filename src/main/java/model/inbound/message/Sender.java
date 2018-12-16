
package model.inbound.message;

import org.apache.commons.lang3.builder.ToStringBuilder;

import serializer.Serializer;

public class Sender {
    private String name;
    private String user_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return Serializer.getInstance().toJson(this);
    }

}
