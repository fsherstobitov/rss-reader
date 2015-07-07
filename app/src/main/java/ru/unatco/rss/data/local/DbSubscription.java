package ru.unatco.rss.data.local;

import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

@StorIOSQLiteType(table = "subscriptions")
public class DbSubscription {
    @StorIOSQLiteColumn(name = "title")
    String mTitle;
    @StorIOSQLiteColumn(name = "url", key = true)
    String mUrl;

    DbSubscription() {}
}
