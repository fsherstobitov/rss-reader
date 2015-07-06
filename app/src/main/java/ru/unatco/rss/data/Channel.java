package ru.unatco.rss.data;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.unatco.rss.model.Item;

@Root(name = "channel", strict = false)
public class Channel {
    @ElementList(inline = true)
    private List<FeedItem> mFeedItems;

    public List<FeedItem> getFeedItems() {
        return mFeedItems;
    }

    public List<Item> getItems() {
        List<Item> items = new ArrayList<>(mFeedItems.size());
        for (FeedItem feedItem : mFeedItems) {
            items.add(feedItem.toItem());
        }
        return items;
    }
}
