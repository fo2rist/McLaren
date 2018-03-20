package com.github.fo2rist.mclaren.ui.widgets;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.fo2rist.mclaren.R;
import com.github.fo2rist.mclaren.utils.IntentUtils;
import com.github.fo2rist.mclaren.utils.LinkUtils;
import com.luseen.autolinklibrary.AutoLinkMode;
import com.luseen.autolinklibrary.AutoLinkOnClickListener;
import com.luseen.autolinklibrary.AutoLinkTextView;

/**
 * Single line with title/data content.
 */
public class InformationLineView extends LinearLayout implements AutoLinkOnClickListener {

    private TextView titleView;
    private AutoLinkTextView valueView;

    public InformationLineView(Context context) {
        this(context, (AttributeSet) null);
        this.setLayoutParams(getDefaultLayoutParams());
    }

    public InformationLineView(Context context, LinearLayout.LayoutParams layoutParams) {
        this(context, (AttributeSet) null);
        this.setLayoutParams(layoutParams);
    }

    public InformationLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_details_line, this);
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

    @NonNull
    private LayoutParams getDefaultLayoutParams() {
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(
                getContext().getResources().getDimensionPixelSize(R.dimen.margin_five), //left
                getContext().getResources().getDimensionPixelSize(R.dimen.margin_half), //top
                getContext().getResources().getDimensionPixelSize(R.dimen.margin_five), //right
                getContext().getResources().getDimensionPixelSize(R.dimen.margin_half)  //bottom
        );
        return layoutParams;
    }

    @Override
    public void onAutoLinkTextClick(AutoLinkMode autoLinkMode, String autoLink) {
        IntentUtils.openInBrowser(getContext(), LinkUtils.getTwitterPageLink(autoLink));
    }
}
