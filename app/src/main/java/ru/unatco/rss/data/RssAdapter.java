package ru.unatco.rss.data;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

public interface RssAdapter {
    @GET("/radio-t")
    void getItems(Callback<RadioTItems> callback);
}
