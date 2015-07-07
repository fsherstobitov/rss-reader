package ru.unatco.rss.data.local;

import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.ArrayList;
import java.util.List;

import ru.unatco.rss.model.Subscription;

public class RssDataStore {

    private final StorIOSQLite mIo;

    public RssDataStore(RssDatabaseHelper dbHelper) {

        SQLiteTypeMapping<DbSubscription> subTypeMapping = SQLiteTypeMapping.<DbSubscription>builder()
                .putResolver(new DbSubscriptionStorIOSQLitePutResolver())
                .getResolver(new DbSubscriptionStorIOSQLiteGetResolver())
                .deleteResolver(new DbSubscriptionStorIOSQLiteDeleteResolver())
                .build();

        SQLiteTypeMapping<DbItem> itemTypeMapping = SQLiteTypeMapping.<DbItem>builder()
                .putResolver(new DbItemStorIOSQLitePutResolver())
                .getResolver(new DbItemStorIOSQLiteGetResolver())
                .deleteResolver(new DbItemStorIOSQLiteDeleteResolver())
                .build();

        mIo = DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(dbHelper)
                .addTypeMapping(DbSubscription.class, subTypeMapping)
                .addTypeMapping(DbItem.class, itemTypeMapping)
                .build();
    }

    public void putSubscription(Subscription s) {
        DbSubscription dbSub = new DbSubscription();
        dbSub.mTitle = s.getmTitle();
        dbSub.mUrl = s.getmUrl();

        mIo.put().object(dbSub).prepare().executeAsBlocking();
    }

    public List<Subscription> getSubscriptions() {
        List<DbSubscription> dbSubs = mIo.get()
                .listOfObjects(DbSubscription.class)
                .withQuery(Query.builder()
                        .table("subscriptions")
                        .build())
                .prepare()
                .executeAsBlocking();

        List<Subscription> subs = new ArrayList<>(dbSubs.size());
        for (DbSubscription dbSub : dbSubs) {
            Subscription s = new Subscription();
            s.setmId(dbSub.mId);
            s.setmTitle(dbSub.mTitle);
            s.setmUrl(dbSub.mUrl);
            subs.add(s);
        }
        return subs;
    }
}
