package com.github.fo2rist.mclaren.ui.transmissionscreen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.models.TransmissionItem
import com.github.fo2rist.mclaren.mvp.TransmissionContract
import com.github.fo2rist.mclaren.ui.utils.animateReveal
import com.github.fo2rist.mclaren.ui.utils.animateUnreveal
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_transmission.*
import javax.inject.Inject

/**
 * Displays live transmission as the list of messages.
 */
class TransmissionActivity : AppCompatActivity(), TransmissionContract.View {

    private lateinit var adapter: TransmissionAdapter
    private lateinit var transmissionLayoutManager: LinearLayoutManager

    @Inject
    lateinit var presenter: TransmissionContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transmission)
        setupViews()

        if (savedInstanceState == null) {
            animateReveal(root_layout, intent)
        }

        presenter.onStart(this)
    }

    private fun setupViews() {
        adapter = TransmissionAdapter()
        transmissionLayoutManager = LinearLayoutManager(this)
        transmission_list.layoutManager = transmissionLayoutManager
        transmission_list.adapter = adapter

        root_layout.setOnClickListener {
            exit()
        }
    }

    override fun onBackPressed() {
        exit()
    }

    private fun exit() {
        animateUnreveal(root_layout) {
            supportFinishAfterTransition()
        }
    }

    override fun onPause() {
        if (isFinishing) {
            presenter.onStop()
        }
        super.onPause()
    }

    override fun displayTransmission(transmissionMessages: List<TransmissionItem>) {
        val hasNewerItems = adapter.setItems(transmissionMessages)
        if (hasNewerItems && firstItemIsVisible()) {
            transmission_list.scrollToPosition(0)
        }
    }

    override fun setNoTransmissionStubVisible(visible: Boolean) {
        empty_list_text.visibility = if (visible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    //TODO duplication of [BaseFeedFragment] extract it.
    private fun firstItemIsVisible() = (transmissionLayoutManager.findFirstVisibleItemPosition() <= 0)


    override fun displayCurrentSession(sessionName: String) {
        title_text.text = sessionName
    }

    override fun showProgress() {
        progress_bar.visibility = VISIBLE
    }

    override fun hideProgress() {
        progress_bar.visibility = INVISIBLE
    }
}
