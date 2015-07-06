package ru.unatco.rss.data.local;

import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;

import ru.unatco.rss.model.Subscription;

public class RssDataStore {

    private final StorIOSQLite mIo;

    public RssDataStore(RssDatabaseHelper dbHelper) {
        mIo = DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(dbHelper)
                .addTypeMapping(Subscription.class, new SQLiteTypeMapping<Subscription>() {

                })
                .build();
    }
}
