
package model.inbound.bing;

import java.util.List;

import serializer.Serializer;

public class Response {

    private String type;
    private Instrumentation instrumentation;
    private String readLink;
    private String webSearchUrl;
    private int totalEstimatedMatches;
    private int nextOffset;
    private List<Value> value = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Instrumentation getInstrumentation() {
        return instrumentation;
    }

    public void setInstrumentation(Instrumentation instrumentation) {
        this.instrumentation = instrumentation;
    }

    public String getReadLink() {
        return readLink;
    }

    public void setReadLink(String readLink) {
        this.readLink = readLink;
    }

    public String getWebSearchUrl() {
        return webSearchUrl;
    }

    public void setWebSearchUrl(String webSearchUrl) {
        this.webSearchUrl = webSearchUrl;
    }

    public int getTotalEstimatedMatches() {
        return totalEstimatedMatches;
    }

    public void setTotalEstimatedMatches(int totalEstimatedMatches) {
        this.totalEstimatedMatches = totalEstimatedMatches;
    }

    public int getNextOffset() {
        return nextOffset;
    }

    public void setNextOffset(int nextOffset) {
        this.nextOffset = nextOffset;
    }

    public List<Value> getValue() {
        return value;
    }

    public void setValue(List<Value> value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Serializer.getInstance().toJson(this);
    }

}
