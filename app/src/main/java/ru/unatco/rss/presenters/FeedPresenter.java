package ru.unatco.rss.presenters;

import android.util.Xml;

import com.android.volley.RequestQueue;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.SimpleXMLConverter;
import ru.unatco.rss.data.RadioTItem;
import ru.unatco.rss.data.RadioTItems;
import ru.unatco.rss.data.RssAdapter;
import ru.unatco.rss.model.Item;

public class FeedPresenter {

    public interface FeedListener {
        void onFetchSuccess(List<Item> items);

        void onFetchError(Throwable error);
    }

    private final RequestQueue mQueue;

    private FeedListener mListener;
    private Map<String, List<Item>> mItemsCache;

    public FeedPresenter(RequestQueue queue) {
        mQueue = queue;
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
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://feeds.rucast.net")
                .setConverter(new SimpleXMLConverter())
                .build();

        RssAdapter rssAdapter = restAdapter.create(RssAdapter.class);
        rssAdapter.getItems(new Callback<RadioTItems>() {
            @Override
            public void success(RadioTItems radioTItems, Response response) {
                System.out.println(radioTItems.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });
//        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    mItemsCache.put(url, parseResponse(response));
//                    if (mListener != null) {
//                        mListener.onFetchSuccess(mItemsCache.get(url));
//                    }
//                } catch (XmlPullParserException | IOException e) {
//                    if (mListener != null) {
//                        mListener.onFetchError(e);
//                    }
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                if (mListener != null) {
//                    mListener.onFetchError(error);
//                }
//            }
//        });
//        mQueue.add(request);
    }

    private List<Item> parseResponse(String response) throws XmlPullParserException, IOException {
        XmlPullParser parser = Xml.newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(new StringReader(response));
        parser.nextTag();
        parser.nextTag();
        List<Item> items = new ArrayList<>();
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if ("item".equals(name)) {
                items.add(parseItem(parser));
            } else {
                skip(parser);
            }
        }
        return items;
    }

    private Item parseItem(XmlPullParser parser) throws IOException, XmlPullParserException {
        String title = null;
        String description = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if ("title".equals(name)) {
                title = parseText(parser);
            } else if ("description".equals(name)) {
                description = parseText(parser);
            } else {
                skip(parser);
            }
        }

        Item item = new Item();
        item.setmTitle(title);
        item.setmDescription(description);
        return item;
    }

    private String parseText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String text = "";
        if (parser.next() == XmlPullParser.TEXT) {
            text = parser.getText();
            parser.nextTag();
        }
        return text;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }


}
