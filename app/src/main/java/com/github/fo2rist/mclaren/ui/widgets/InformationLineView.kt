package com.github.fo2rist.mclaren.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.utils.IntentUtils
import com.github.fo2rist.mclaren.utils.LinkUtils
import io.github.armcha.autolink.AutoLinkItem
import io.github.armcha.autolink.AutoLinkTextView
import io.github.armcha.autolink.MODE_MENTION

/**
 * Single line with title/data content.
 */
class InformationLineView(
    context: Context,
    attrs: AttributeSet?
) : LinearLayout(context, attrs) {
    private var titleView: TextView
    private var valueView: AutoLinkTextView

    constructor(context: Context) : this(context, null as AttributeSet?) {
        this.layoutParams = createDefaultLayoutParams()
    }

    @Suppress("Unused")
    constructor(context: Context, layoutParams: LayoutParams) : this(context, null) {
        this.layoutParams = layoutParams
    }

    init {
        View.inflate(context, R.layout.view_details_line, this)
        titleView = findViewById(R.id.title)
        valueView = findViewById(R.id.value)
        valueView.addAutoLinkMode(MODE_MENTION)
        valueView.onAutoLinkClick(this::onAutoLinkTextClick);
        valueView.mentionModeColor = ContextCompat.getColor(context, R.color.textSecondaryWhite)
    }

    fun setContent(@StringRes title: Int, value: CharSequence) {
        titleView.setText(title)
        valueView.setText(value)
    }

    private fun createDefaultLayoutParams(): LayoutParams {
        return LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
            setMargins(
                    context.resources.getDimensionPixelSize(R.dimen.margin_five),  //left
                    context.resources.getDimensionPixelSize(R.dimen.margin_half),  //top
                    context.resources.getDimensionPixelSize(R.dimen.margin_five),  //right
                    context.resources.getDimensionPixelSize(R.dimen.margin_half)   //bottom
            )
        }
    }

    private fun onAutoLinkTextClick(autoLink: AutoLinkItem) {
        IntentUtils.openInBrowser(context, LinkUtils.getTwitterPageLink(autoLink.originalText))
    }
}
