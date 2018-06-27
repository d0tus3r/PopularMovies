package net.digitalswarm.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by us3r on 5/1/2018.
 * Describes a movie object that will be created from JSON data from TMDB api
 */

public class Movie implements Parcelable{
    //basic Strings for movie data
    private final String ogName;
    private final String posterUrl;
    private final String releaseDate;
    private final String userRating;
    private final String plotSynopsis;
    private final String id;
    //Favorite
    private Boolean isFavorite;

    //default constructor
    public Movie(String ogName, String posterUrl, String releaseDate, String userRating, String plotSynopsis, String id, Boolean isFavorite) {
        this.ogName = ogName;
        this.posterUrl = posterUrl;
        this.releaseDate = releaseDate;
        this.userRating = userRating;
        this.plotSynopsis = plotSynopsis;
        this.id = id;
        this.isFavorite = isFavorite;
    }
    //parcel version constructor
    private Movie(Parcel parcel){
        this.ogName = parcel.readString();
        this.posterUrl = parcel.readString();
        this.releaseDate = parcel.readString();
        this.userRating = parcel.readString();
        this.plotSynopsis = parcel.readString();
        this.id = parcel.readString();
        //true if != 0
        this.isFavorite = parcel.readByte() != 0;
    }
    //movie object creator from parcel
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    //getters and setters
    public String getOgName() {
        return ogName;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getUserRating() {
        return userRating;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public String getId() {
        return id;
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    //package object for parcel
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.getOgName());
        parcel.writeString(this.getPosterUrl());
        parcel.writeString(this.getReleaseDate());
        parcel.writeString(this.getUserRating());
        parcel.writeString(this.getPlotSynopsis());
        parcel.writeString(this.getId());
        //1 true, 0 false
        parcel.writeByte((byte) (isFavorite ? 1 : 0));
    }


}
