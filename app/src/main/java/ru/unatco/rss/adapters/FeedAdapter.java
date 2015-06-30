package ru.unatco.rss.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.unatco.rss.R;
import ru.unatco.rss.model.Item;

public class FeedAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;
    private final List<Item> mItems;

    public FeedAdapter(LayoutInflater inflater, List<Item> items) {
        mInflater = inflater;
        mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Item getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.feed_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Item item = getItem(position);
        holder.mTitle.setText(item.getmTitle());

        return convertView;
    }

    public static class ViewHolder {
        @Bind(R.id.title)
        TextView mTitle;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
