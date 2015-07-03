package ru.unatco.rss.data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
public class RadioTItem {
    @Element(name = "title")
    String mTitle;
    @Element(name = "description")
    String mDescription;

    public RadioTItem() {}
}
