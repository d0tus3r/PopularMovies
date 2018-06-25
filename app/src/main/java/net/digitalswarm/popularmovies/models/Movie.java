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
    //private final Boolean isFavorite;

    //default constructor
    public Movie(String ogName, String posterUrl, String releaseDate, String userRating, String plotSynopsis, String id) {
        this.ogName = ogName;
        this.posterUrl = posterUrl;
        this.releaseDate = releaseDate;
        this.userRating = userRating;
        this.plotSynopsis = plotSynopsis;
        this.id = id;
    }
    //parcel version constructor
    private Movie(Parcel parcel){
        this.ogName = parcel.readString();
        this.posterUrl = parcel.readString();
        this.releaseDate = parcel.readString();
        this.userRating = parcel.readString();
        this.plotSynopsis = parcel.readString();
        this.id = parcel.readString();
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
    }


}
