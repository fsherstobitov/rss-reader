package ru.unatco.rss.data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import ru.unatco.rss.model.Item;

@Root(name = "item", strict = false)
public class FeedItem {
    @Element(name = "title", required = false)
    private String mTitle;
    @Element(name = "description", required = false)
    private String mDescription;

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public Item toItem() {
        return new Item(mTitle, mDescription);
    }
}
