package com.erictang.twothreetimezoneswatchface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.text.TextUtils;
import android.view.ViewGroup;

import java.util.TimeZone;


public class TimeZoneChooserActivity extends Activity implements WearableListView.ClickListener {
    public static final String ACTION = "CHOOSE_TIMEZONE";
    public static final String EXTRA_CHOSEN_TIMEZONE = "EXTRA_CHOSEN_TIMEZONE";
    static final String[] tzids = TimeZone.getAvailableIDs();

    class TimeZoneChooserAdapter extends WearableListView.Adapter {
        @Override
        public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ListViewSingleLineItem item = new ListViewSingleLineItem(parent.getContext());
            return new WearableListView.ViewHolder(item);
        }

        @Override
        public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
            ListViewSingleLineItem item = (ListViewSingleLineItem) holder.itemView;
            if (position == 0) {
                item.setTitle("Device time zone");
            } else {
                item.setTitle(tzids[position-1]);
            }
        }

        @Override
        public int getItemCount() {
            return tzids.length + 1;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_time_zone_chooser);

        WearableListView listView = (WearableListView) findViewById(R.id.list_view);
        listView.setHasFixedSize(true);
        listView.setClickListener(this);
        listView.setAdapter(new TimeZoneChooserAdapter());

        String chosenTzid = getIntent().getStringExtra(EXTRA_CHOSEN_TIMEZONE);
        for (int i = 0; i < tzids.length; i++) {
            if (TextUtils.equals(tzids[i], chosenTzid)) {
                listView.scrollToPosition(i+1);
                break;
            }
        }
    }

    //// BEGIN WearableListView.ClickListener
    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        Intent intent = new Intent(getIntent());
        if (viewHolder.getPosition() == 0) {
            intent.removeExtra(EXTRA_CHOSEN_TIMEZONE);
        } else {
            intent.putExtra(EXTRA_CHOSEN_TIMEZONE, tzids[viewHolder.getPosition() - 1]);
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onTopEmptyRegionClick() {

    }
    //// END WearableListView.ClickListener
}
