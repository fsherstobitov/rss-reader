package ru.unatco.rss.data;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface RssService {
    @GET("/{path}")
    void getItems(@Path("path") String path, Callback<Feed> callback);
}
