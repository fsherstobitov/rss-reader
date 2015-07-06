package ru.unatco.rss.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel parcel) {
            return new Item(parcel);
        }

        @Override
        public Item[] newArray(int i) {
            return new Item[i];
        }
    };

    private String mTitle;
    private String mDescription;

    public Item() {

    }

    public Item(Parcel in) {
        mTitle = in.readString();
        mDescription = in.readString();
    }

    public Item(String title, String description) {
        mTitle = title;
        mDescription = description;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(mTitle);
        out.writeString(mDescription);
    }
}
