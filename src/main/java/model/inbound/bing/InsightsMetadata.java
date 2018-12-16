
package model.inbound.bing;

import serializer.Serializer;

public class InsightsMetadata {

    private int recipeSourcesCount;
    private BestRepresentativeQuery bestRepresentativeQuery;
    private int pagesIncludingCount;
    private int availableSizesCount;

    public int getRecipeSourcesCount() {
        return recipeSourcesCount;
    }

    public void setRecipeSourcesCount(int recipeSourcesCount) {
        this.recipeSourcesCount = recipeSourcesCount;
    }

    public BestRepresentativeQuery getBestRepresentativeQuery() {
        return bestRepresentativeQuery;
    }

    public void setBestRepresentativeQuery(BestRepresentativeQuery bestRepresentativeQuery) {
        this.bestRepresentativeQuery = bestRepresentativeQuery;
    }

    public int getPagesIncludingCount() {
        return pagesIncludingCount;
    }

    public void setPagesIncludingCount(int pagesIncludingCount) {
        this.pagesIncludingCount = pagesIncludingCount;
    }

    public int getAvailableSizesCount() {
        return availableSizesCount;
    }

    public void setAvailableSizesCount(int availableSizesCount) {
        this.availableSizesCount = availableSizesCount;
    }

    @Override
    public String toString() {
        return Serializer.getInstance().toJson(this);
    }

}
