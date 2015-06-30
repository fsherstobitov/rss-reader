package ru.unatco.rss.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Subscription implements Parcelable {

    public static final Creator<Subscription> CREATOR = new Creator<Subscription>() {
        @Override
        public Subscription createFromParcel(Parcel parcel) {
            return new Subscription(parcel);
        }

        @Override
        public Subscription[] newArray(int i) {
            return new Subscription[i];
        }
    };

    private String mTitle;
    private String mUrl;

    public Subscription() {}

    public Subscription(Parcel in) {
        mTitle = in.readString();
        mUrl = in.readString();
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(mTitle);
        out.writeString(mUrl);
    }
}
