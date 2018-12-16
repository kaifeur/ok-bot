
package model.inbound.bing;

import serializer.Serializer;

public class Value {

    private String webSearchUrl;
    private String name;
    private String thumbnailUrl;
    private String datePublished;
    private String contentUrl;
    private String hostPageUrl;
    private String contentSize;
    private String encodingFormat;
    private String hostPageDisplayUrl;
    private int width;
    private int height;
    private Thumbnail thumbnail;
    private String imageInsightsToken;
    private InsightsMetadata insightsMetadata;
    private String imageId;
    private String accentColor;

    public String getWebSearchUrl() {
        return webSearchUrl;
    }

    public void setWebSearchUrl(String webSearchUrl) {
        this.webSearchUrl = webSearchUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getHostPageUrl() {
        return hostPageUrl;
    }

    public void setHostPageUrl(String hostPageUrl) {
        this.hostPageUrl = hostPageUrl;
    }

    public String getContentSize() {
        return contentSize;
    }

    public void setContentSize(String contentSize) {
        this.contentSize = contentSize;
    }

    public String getEncodingFormat() {
        return encodingFormat;
    }

    public void setEncodingFormat(String encodingFormat) {
        this.encodingFormat = encodingFormat;
    }

    public String getHostPageDisplayUrl() {
        return hostPageDisplayUrl;
    }

    public void setHostPageDisplayUrl(String hostPageDisplayUrl) {
        this.hostPageDisplayUrl = hostPageDisplayUrl;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getImageInsightsToken() {
        return imageInsightsToken;
    }

    public void setImageInsightsToken(String imageInsightsToken) {
        this.imageInsightsToken = imageInsightsToken;
    }

    public InsightsMetadata getInsightsMetadata() {
        return insightsMetadata;
    }

    public void setInsightsMetadata(InsightsMetadata insightsMetadata) {
        this.insightsMetadata = insightsMetadata;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getAccentColor() {
        return accentColor;
    }

    public void setAccentColor(String accentColor) {
        this.accentColor = accentColor;
    }

    @Override
    public String toString() {
        return Serializer.getInstance().toJson(this);
    }

}
