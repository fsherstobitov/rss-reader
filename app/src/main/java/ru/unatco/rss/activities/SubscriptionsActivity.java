package ru.unatco.rss.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.unatco.rss.R;
import ru.unatco.rss.adapters.SubscriptionsAdapter;
import ru.unatco.rss.data.local.RssDataStore;
import ru.unatco.rss.data.local.RssDatabaseHelper;
import ru.unatco.rss.model.Subscription;


public class SubscriptionsActivity extends AppCompatActivity {

    private RssDataStore mDataStore;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(android.R.id.list)
    ListView mListView;
    @Bind(android.R.id.progress)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriptions);
        ButterKnife.bind(this);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Subscription sub = (Subscription) adapterView.getAdapter().getItem(i);
                Intent intent = new Intent(SubscriptionsActivity.this, FeedActivity.class);
                intent.putExtra(FeedActivity.ARG_SUB, sub);
                startActivity(intent);
            }
        });
        mListView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);

        mDataStore = new RssDataStore(new RssDatabaseHelper(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mListView.setAdapter(new SubscriptionsAdapter(getApplicationContext(), mDataStore.getSubscriptions()));
        mListView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_subscriptions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
