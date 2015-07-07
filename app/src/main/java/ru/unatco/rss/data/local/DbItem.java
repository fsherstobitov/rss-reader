package ru.unatco.rss.data.local;

import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

@StorIOSQLiteType(table = "items")
public class DbItem {
    @StorIOSQLiteColumn(name = "_id", key = true)
    Long mId;
    @StorIOSQLiteColumn(name = "title")
    String mTitle;
    @StorIOSQLiteColumn(name = "description")
    String mDescription;
    @StorIOSQLiteColumn(name = "sub_id")
    Long mSubId;

    DbItem() {
    }
}
