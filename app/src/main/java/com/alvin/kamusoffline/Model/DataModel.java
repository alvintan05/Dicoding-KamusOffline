package com.alvin.kamusoffline.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Alvin Tandiardi on 27/08/2018.
 */

public class DataModel implements Parcelable {

    private int id;
    private String kata;
    private String arti;

    public DataModel() {
    }

    public DataModel(String kata, String arti) {
        this.kata = kata;
        this.arti = arti;
    }

    public DataModel(int id, String kata, String arti) {
        this.id = id;
        this.kata = kata;
        this.arti = arti;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKata() {
        return kata;
    }

    public void setKata(String kata) {
        this.kata = kata;
    }

    public String getArti() {
        return arti;
    }

    public void setArti(String arti) {
        this.arti = arti;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.kata);
        dest.writeString(this.arti);
    }

    protected DataModel(Parcel in) {
        this.id = in.readInt();
        this.kata = in.readString();
        this.arti = in.readString();
    }

    public static final Parcelable.Creator<DataModel> CREATOR = new Parcelable.Creator<DataModel>() {
        @Override
        public DataModel createFromParcel(Parcel source) {
            return new DataModel(source);
        }

        @Override
        public DataModel[] newArray(int size) {
            return new DataModel[size];
        }
    };
}
