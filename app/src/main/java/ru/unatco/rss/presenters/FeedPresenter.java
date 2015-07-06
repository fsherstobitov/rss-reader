package ru.unatco.rss.presenters;

import android.net.Uri;
import android.util.Xml;

import com.android.volley.RequestQueue;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.SimpleXMLConverter;
import ru.unatco.rss.data.Feed;
import ru.unatco.rss.data.RssService;
import ru.unatco.rss.model.Item;

public class FeedPresenter {

    public interface FeedListener {
        void onFetchSuccess(List<Item> items);

        void onFetchError(Throwable error);
    }

    private FeedListener mListener;
    private Map<String, List<Item>> mItemsCache;

    public FeedPresenter() {
        mItemsCache = new HashMap<>();
    }

    public void setListener(FeedListener listener) {
        mListener = listener;
    }

    public void clearListener() {
        mListener = null;
    }

    public void fetchItems(String url) {
        if (mItemsCache.containsKey(url) && mListener != null) {
            mListener.onFetchSuccess(mItemsCache.get(url));
        } else {
            doFetchItems(url);
        }
    }

    private void doFetchItems(final String url) {
        Uri uri = Uri.parse(url);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(uri.getScheme() + "://" + uri.getHost())
                .setConverter(new SimpleXMLConverter())
                .build();

        String path = uri.getPath().startsWith("/") ? uri.getPath().substring(1) : uri.getPath();

        RssService rssService = restAdapter.create(RssService.class);
        rssService.getItems(path, new Callback<Feed>() {
            @Override
            public void success(Feed feed, Response response) {
                mItemsCache.put(url, feed.getmChannel().getItems());
                mListener.onFetchSuccess(mItemsCache.get(url));
            }

            @Override
            public void failure(RetrofitError error) {
                mListener.onFetchError(error);
            }
        });
    }
}
