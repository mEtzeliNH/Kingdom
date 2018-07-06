package com.example.michael.kingdom.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Village implements Parcelable {

    private Village(Parcel in) {

    }

    public static final Creator<Village> CREATOR = new Creator<Village>() {
        @Override
        public Village createFromParcel(Parcel in) {
            return new Village(in);
        }

        @Override
        public Village[] newArray(int size) {
            return new Village[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
