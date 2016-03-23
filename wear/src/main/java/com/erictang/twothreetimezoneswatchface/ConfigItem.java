package com.erictang.twothreetimezoneswatchface;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * TODO: document your custom view class.
 */
public class ConfigItem extends LinearLayout {
    private TextView mTitleView, mValueView;

    public ConfigItem(Context context) {
        super(context);
        View.inflate(context, R.layout.config_item_layout, this);

        mTitleView = (TextView) findViewById(R.id.title);
        mValueView = (TextView) findViewById(R.id.value);
    }

    public void setTitle(String title) {
        mTitleView.setText(title);
    }

    public void setValue(String value) {
        mValueView.setText(value);
    }
}
