package com.github.fo2rist.mclaren.ui.widgets;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.fo2rist.mclaren.R;
import com.github.fo2rist.mclaren.utils.LinkUtils;
import com.github.fo2rist.mclaren.utils.IntentUtils;
import com.luseen.autolinklibrary.AutoLinkMode;
import com.luseen.autolinklibrary.AutoLinkOnClickListener;
import com.luseen.autolinklibrary.AutoLinkTextView;

public class DriverDetailsLineView extends LinearLayout implements AutoLinkOnClickListener {

    private TextView titleView;
    private AutoLinkTextView valueView;

    public DriverDetailsLineView(Context context) {
        this(context, null);
    }

    public DriverDetailsLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_driver_details_line, this);
        titleView = findViewById(R.id.title);
        valueView = findViewById(R.id.value);

        valueView.addAutoLinkMode(AutoLinkMode.MODE_MENTION);
        valueView.setAutoLinkOnClickListener(this);
        valueView.setMentionModeColor(ContextCompat.getColor(getContext(), R.color.textSecondaryWhite));
    }

    public void setContent(CharSequence title, CharSequence value) {
        titleView.setText(title);
        valueView.setAutoLinkText(value.toString());
    }

    @Override
    public void onAutoLinkTextClick(AutoLinkMode autoLinkMode, String autoLink) {
        IntentUtils.openInBrowser(getContext(), LinkUtils.getTwitterPageLink(autoLink));
    }
}
