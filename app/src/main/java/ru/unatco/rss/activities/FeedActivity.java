package ru.unatco.rss.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.toolbox.Volley;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.unatco.rss.R;
import ru.unatco.rss.model.Item;
import ru.unatco.rss.model.Subscription;
import ru.unatco.rss.presenters.FeedPresenter;

public class FeedActivity extends AppCompatActivity implements FeedPresenter.FeedListener {

    public static final String ARG_SUB = "ru.unatco.rss.Subscription";

    private static FeedPresenter mPresenter;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(android.R.id.list)
    ListView mListView;
    @Bind(android.R.id.progress)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);

        mPresenter = new FeedPresenter(Volley.newRequestQueue(getApplicationContext()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.setListener(this);
        Subscription sub = getIntent().getParcelableExtra(ARG_SUB);
        mPresenter.fetchItems(sub.getmUrl());
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.clearListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feed, menu);
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

    @Override
    public void onFetchSuccess(List<Item> items) {
        mListView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFetchError(Throwable error) {

    }
}
