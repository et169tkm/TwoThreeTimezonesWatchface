/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.erictang.twothreetimezoneswatchface;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.format.Time;
import android.view.SurfaceHolder;
import android.view.WindowInsets;

import java.util.Date;

/**
 * Digital watch face with seconds. In ambient mode, the seconds aren't displayed. On devices with
 * low-bit ambient mode, the text is drawn without anti-aliasing in ambient mode.
 */
public class ThreeTimezoneWatchface extends TwoTimezoneWatchface {

    @Override
    public Engine onCreateEngine() {
        return new Engine();
    }

    private class Engine extends TwoTimezoneWatchface.Engine {
        Time mTime3;

        float mXOffset3, mYOffset3;
        float mMinHorzintalMargin;

        Paint mTextPaint3;

        @Override
        public void onCreate(SurfaceHolder holder) {
            super.onCreate(holder);

            mTextPaint3 = new Paint(mTextPaint2);
            mMinHorzintalMargin = getResources().getDimension(R.dimen.margin_horizontal_min);
        }

        @Override
        public void onApplyWindowInsets(WindowInsets insets) {
            super.onApplyWindowInsets(insets);
            mTextPaint3.setTextSize(mTextPaint2.getTextSize());
            mYOffset3 = mYOffset2;
        }

        @Override
        public void onDraw(Canvas canvas, Rect bounds) {
            super.onDraw(canvas, bounds);
            if (!mAmbient) {
                long dT = new Date().getTime() - mLastAmbientInteractiveTransitionTime;
                if (dT < 400) {
                    mTextPaint3.setAlpha(0);
                } else if (dT < 800) {
                    mTextPaint3.setAlpha((int)(255*(dT - 400)/400));
                } else {
                    mTextPaint3.setAlpha(255);
                }

                String text = getText(mTime);

                mTime3.setToNow();
                String text3 = getText2(mTime3);

                // align to the right of the main time,
                // but make sure time3 doesn't overlap with time2 and there is a margin between them
                float text2Right = mXOffset2 + mTextPaint2.measureText(getText2(mTime2));
                mXOffset3 = mXOffset + mTextPaint.measureText(text)
                        - mTextPaint3.measureText(text3);
                if (mXOffset3 < text2Right + mMinHorzintalMargin) {
                    mXOffset3 = text2Right + mMinHorzintalMargin;
                }
                canvas.drawText(text3, mXOffset3, mYOffset3, mTextPaint3);
            }
        }

        @Override
        void setTimezone() {
            super.setTimezone();

            mTime3 = getTimeWithTimezoneID(ConfigUtil.getTimezoneId(ThreeTimezoneWatchface.this, 2));
        }
    }
}
