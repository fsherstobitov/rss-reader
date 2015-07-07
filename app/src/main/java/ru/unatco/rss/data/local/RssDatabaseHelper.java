package ru.unatco.rss.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RssDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "rss.sqlite";
    private static final int VERSION = 1;

    public RssDatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table subscriptions (_id integer primary key autoincrement, title text, url text)");
        sqLiteDatabase.execSQL("create table items (_id integer primary key autoincrement, title text, description text, sub_id integer references subscriptions(_id))");

        sqLiteDatabase.execSQL("insert into subscriptions (title, url) values ('Радио-Т', 'http://feeds.rucast.net/radio-t')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
