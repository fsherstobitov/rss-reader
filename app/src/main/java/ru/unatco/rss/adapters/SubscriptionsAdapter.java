package ru.unatco.rss.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.unatco.rss.R;
import ru.unatco.rss.model.Subscription;

public class SubscriptionsAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Subscription> mSubscriptions;

    public SubscriptionsAdapter(Context context, List<Subscription> mSubscriptions) {
        mInflater = LayoutInflater.from(context);
        this.mSubscriptions = mSubscriptions;
    }

    @Override
    public int getCount() {
        return mSubscriptions.size();
    }

    @Override
    public Subscription getItem(int i) {
        return mSubscriptions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = mInflater.inflate(R.layout.subscription, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Subscription sub = getItem(i);
        holder.mTitle.setText(sub.getmTitle());
        
        return view;
    }

    private static class ViewHolder {
        @Bind(R.id.title)
        TextView mTitle;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
