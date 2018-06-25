package net.digitalswarm.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Review object created from tmdb
 */

public class Review implements Parcelable{
    //basic Strings for review data
    private final String rId;
    private final String rUrl;
    private final String rAuthor;
    private final String rContent;

    //default constructor
    public Review(String id, String url, String author, String content) {
        this.rId = id;
        this.rUrl = url;
        this.rAuthor = author;
        this.rContent = content;
    }

    //parcel constructor
    private Review(Parcel parcel) {
        this.rId = parcel.readString();
        this.rUrl = parcel.readString();
        this.rAuthor = parcel.readString();
        this.rContent = parcel.readString();
    }

    //review object creator from parcel
    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    //getters and setters
    private String getrId() {
        return rId;
    }

    private String getrUrl() {
        return rUrl;
    }

    public String getrAuthor() {
        return rAuthor;
    }

    public String getrContent() {
        return rContent;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    //package object for parcel
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.getrId());
        parcel.writeString(this.getrUrl());
        parcel.writeString(this.getrAuthor());
        parcel.writeString(this.getrContent());
    }
}


