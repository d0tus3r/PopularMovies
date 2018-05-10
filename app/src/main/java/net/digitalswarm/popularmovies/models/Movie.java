package net.digitalswarm.popularmovies.models;

/**
 * Created by us3r on 5/1/2018.
 */

public class Movie {
    //basic Strings for movie data
    String ogName;
    String thumbnailUrl;
    String releaseDate;
    String userRating;
    String plotSynopsis;

    //default constructor
    public Movie(String ogName, String thumbnailUrl, String releaseDate, String userRating, String plotSynopsis) {
        this.ogName = ogName;
        this.thumbnailUrl = thumbnailUrl;
        this.releaseDate = releaseDate;
        this.userRating = userRating;
        this.plotSynopsis = plotSynopsis;
    }
    //getters and setters
    public String getOgName() {
        return ogName;
    }

    public void setOgName(String ogName) {
        this.ogName = ogName;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }



}
