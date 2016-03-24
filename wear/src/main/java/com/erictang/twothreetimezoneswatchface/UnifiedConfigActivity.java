package com.erictang.twothreetimezoneswatchface;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.ViewGroup;

public class UnifiedConfigActivity extends Activity implements WearableListView.ClickListener {

    class UnifiedConfigSettingsAdapter extends WearableListView.Adapter {
        @Override
        public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ConfigItem item = new ConfigItem(parent.getContext());
            return new WearableListView.ViewHolder(item);
        }

        @Override
        public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
            ConfigItem view = (ConfigItem) holder.itemView;
            String tzid = ConfigUtil.getTimezoneId(UnifiedConfigActivity.this, position);
            if (tzid == null) {
                tzid = getString(R.string.device_timezone);
            }
            view.setValue(tzid);
            switch (position) {
                case 0:
                    view.setTitle("1st timezone");
                    break;
                case 1:
                    view.setTitle("2nd timezone");
                    break;
                case 2:
                    view.setTitle("3rd timezone");
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

    UnifiedConfigSettingsAdapter mAdapter;
    BroadcastReceiver mTimeZoneUpdatedReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unified_config);
        WearableListView listView = (WearableListView) findViewById(R.id.list_view);
        listView.setHasFixedSize(true);
        listView.setClickListener(this);
        mAdapter = new UnifiedConfigSettingsAdapter();
        listView.setAdapter(mAdapter);

        mTimeZoneUpdatedReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                UnifiedConfigActivity.this.timezoneUpdated();
            }
        };
        IntentFilter intentFilter = new IntentFilter(ConfigUtil.ACTION_TIMEZONE_UPDATED);
        registerReceiver(mTimeZoneUpdatedReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mTimeZoneUpdatedReceiver);
        super.onDestroy();
    }

    //// BEGIN WearableListView.ClickListener
    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        String tzid = ConfigUtil.getTimezoneId(this, viewHolder.getPosition());
        Intent intent = new Intent(this, TimeZoneChooserActivity.class);
        intent.putExtra(TimeZoneChooserActivity.EXTRA_CHOSEN_TIMEZONE, tzid);
        startActivityForResult(intent, viewHolder.getPosition());
    }

    @Override
    public void onTopEmptyRegionClick() {

    }
    //// END WearableListView.ClickListener


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String tzid = data.getStringExtra(TimeZoneChooserActivity.EXTRA_CHOSEN_TIMEZONE);
            Log.d("eric", "position: " + requestCode + ", chosen timezone: " + tzid);
            ConfigUtil.setTimezoneId(this, requestCode, tzid);
        }
    }

    private void timezoneUpdated() {
        mAdapter.notifyDataSetChanged();
    }
}
