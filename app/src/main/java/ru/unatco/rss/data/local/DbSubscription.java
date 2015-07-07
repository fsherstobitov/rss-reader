package ru.unatco.rss.data.local;

import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

@StorIOSQLiteType(table = "subscriptions")
public class DbSubscription {
    @StorIOSQLiteColumn(name = "_id", key = true)
    Long mId;
    @StorIOSQLiteColumn(name = "title")
    String mTitle;
    @StorIOSQLiteColumn(name = "url")
    String mUrl;

    DbSubscription() {}
}
