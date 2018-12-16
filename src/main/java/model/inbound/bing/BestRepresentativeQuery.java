
package model.inbound.bing;

import serializer.Serializer;

public class BestRepresentativeQuery {

    private String text;
    private String displayText;
    private String webSearchUrl;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public String getWebSearchUrl() {
        return webSearchUrl;
    }

    public void setWebSearchUrl(String webSearchUrl) {
        this.webSearchUrl = webSearchUrl;
    }

    @Override
    public String toString() {
        return Serializer.getInstance().toJson(this);
    }

}
