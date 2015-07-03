package ru.unatco.rss.data;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "rss", strict = false)
public class RadioTItems {
    @ElementList(required = false, name = "channel")
    List<RadioTItem> items;
}
