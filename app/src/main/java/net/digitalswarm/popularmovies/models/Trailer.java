package net.digitalswarm.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Trailer implements Parcelable{
    //basic Strings for trailer data
    private final String tId;
    private final String tKey;
    private final String tName;
    private final String tSite;
    private final String tType;

    //default constructor
    public Trailer(String id, String key, String name, String site, String type) {
        this.tId = id;
        this.tKey = key;
        this.tName = name;
        this.tSite = site;
        this.tType = type;
    }

    //parcel constructor
    private Trailer(Parcel parcel) {
        this.tId = parcel.readString();
        this.tKey = parcel.readString();
        this.tName = parcel.readString();
        this.tSite = parcel.readString();
        this.tType = parcel.readString();
    }

    //trailer object creator from parcel
    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    //getters and setters
    private String gettId() {
        return tId;
    }

    public String gettKey() {
        return tKey;
    }

    public String gettName() {
        return tName;
    }

    private String gettSite() {
        return tSite;
    }

    private String gettType() {
        return tType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //package object for parcel
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.gettId());
        parcel.writeString(this.gettKey());
        parcel.writeString(this.gettName());
        parcel.writeString(this.gettSite());
        parcel.writeString(this.gettType());
    }
}
