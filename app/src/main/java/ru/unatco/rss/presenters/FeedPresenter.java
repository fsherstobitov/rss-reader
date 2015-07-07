package ru.unatco.rss.presenters;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.unatco.rss.data.Feed;
import ru.unatco.rss.data.local.RssDataStore;
import ru.unatco.rss.model.Item;
import ru.unatco.rss.model.Subscription;

public class FeedPresenter {

    public interface FeedListener {
        void onFetchSuccess(List<Item> items);

        void onFetchError(Throwable error);
    }

    private final RssDataStore mDataStore;
    private final RequestQueue mQeueu;

    private FeedListener mListener;
    private Subscription mSubscription;
    private Map<String, List<Item>> mItemsCache;

    public FeedPresenter(RssDataStore dataStore, RequestQueue queue) {
        mDataStore = dataStore;
        mQeueu = queue;
        mItemsCache = new HashMap<>();
    }

    public void setListener(FeedListener listener) {
        mListener = listener;
    }

    public void clearListener() {
        mListener = null;
    }

    public Subscription getSubscription() {
        return mSubscription;
    }

    public void setSubscription(Subscription mSubscription) {
        this.mSubscription = mSubscription;
    }

    public void fetchItems() {
        if (mSubscription == null) {
            throw new IllegalStateException("Subscription is not set. You have to call setSubscription() first");
        }
        mListener.onFetchSuccess(mDataStore.getItems(mSubscription));
        doFetchItems(mSubscription.getmUrl());
    }

    private void doFetchItems(final String url) {
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Serializer serializer = new Persister();
                        try {
                            Feed feed = serializer.read(Feed.class, response);
                            for (Item item : feed.getmChannel().getItems()) {
                                mDataStore.putItem(mSubscription, item);
                            }
                            mListener.onFetchSuccess(mDataStore.getItems(mSubscription));
                        } catch (Exception e) {
                            Log.d("FeedPresenter", e.getMessage());
                            mListener.onFetchError(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("FeedPresenter", error.getMessage());
                        mListener.onFetchError(error);
                    }
                });
        mQeueu.add(request);
    }
}
