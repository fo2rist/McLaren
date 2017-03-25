package com.github.fo2rist.mclaren.widgets;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.fo2rist.mclaren.R;

public class DriverDetailsLineView extends LinearLayout {

    private TextView titleView;
    private TextView valueView;

    public DriverDetailsLineView(Context context) {
        this(context, null);
    }

    public DriverDetailsLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_driver_details_line, this);

        titleView = (TextView) findViewById(R.id.title);
        valueView = (TextView) findViewById(R.id.value);
    }

    public void setContent(CharSequence title, CharSequence value) {
        titleView.setText(title);
        valueView.setText(value);
    }
}
