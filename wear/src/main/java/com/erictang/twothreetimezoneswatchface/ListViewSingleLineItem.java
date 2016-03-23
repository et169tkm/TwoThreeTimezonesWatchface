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
public class ListViewSingleLineItem extends LinearLayout {
    private TextView mTitleView;

    public ListViewSingleLineItem(Context context) {
        super(context);
        View.inflate(context, R.layout.list_view_single_line_item, this);

        mTitleView = (TextView) findViewById(R.id.title);
    }

    public void setTitle(String title) {
        mTitleView.setText(title);
    }
}
