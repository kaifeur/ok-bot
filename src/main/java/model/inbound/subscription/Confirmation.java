package model.inbound.subscription;

import org.apache.commons.lang3.builder.ToStringBuilder;

import serializer.Serializer;

public class Confirmation {
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return Serializer.getInstance().toJson(this);
    }
}
