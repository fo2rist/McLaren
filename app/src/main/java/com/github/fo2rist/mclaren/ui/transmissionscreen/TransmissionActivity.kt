package com.github.fo2rist.mclaren.ui.transmissionscreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.databinding.ActivityTransmissionBinding
import com.github.fo2rist.mclaren.models.TransmissionItem
import com.github.fo2rist.mclaren.mvp.TransmissionContract
import com.github.fo2rist.mclaren.ui.utils.animateReveal
import com.github.fo2rist.mclaren.ui.utils.animateUnreveal
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * Displays live transmission as the list of messages.
 */
class TransmissionActivity : AppCompatActivity(), TransmissionContract.View {
    private lateinit var binding: ActivityTransmissionBinding

    private lateinit var adapter: TransmissionAdapter
    private lateinit var transmissionLayoutManager: LinearLayoutManager

    @Inject
    lateinit var presenter: TransmissionContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityTransmissionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()

        if (savedInstanceState == null) {
            animateReveal(binding.rootLayout, intent)
        }

        presenter.onStart()
    }

    private fun setupViews() {
        adapter = TransmissionAdapter()
        transmissionLayoutManager = LinearLayoutManager(this)
        binding.transmissionList.layoutManager = transmissionLayoutManager
        binding.transmissionList.adapter = adapter

        binding.rootLayout.setOnClickListener {
            exit()
        }
    }

    override fun onBackPressed() {
        exit()
    }

    private fun exit() {
        animateUnreveal(binding.rootLayout) {
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
            binding.transmissionList.scrollToPosition(0)
        }
    }

    override fun setNoTransmissionStubVisible(visible: Boolean) {
        binding.emptyListText.visibility = if (visible) {
            VISIBLE
        } else {
            GONE
        }
    }

    //TODO duplication of [BaseFeedFragment] extract it.
    private fun firstItemIsVisible() = (transmissionLayoutManager.findFirstVisibleItemPosition() <= 0)


    override fun displayCurrentSession(session: TransmissionItem.Session) {
        binding.titleText.text = when (session) {
            TransmissionItem.Session.UNKNOWN -> ""
            TransmissionItem.Session.PRACTICE_1 -> getString(R.string.transmission_session_practice_1)
            TransmissionItem.Session.PRACTICE_2 -> getString(R.string.transmission_session_practice_2)
            TransmissionItem.Session.PRACTICE_3 -> getString(R.string.transmission_session_practice_3)
            TransmissionItem.Session.QUALIFICATION -> getString(R.string.transmission_session_qualification)
            TransmissionItem.Session.RACE -> getString(R.string.transmission_session_race)
        }
    }

    override fun showProgress() {
        binding.progressBar.visibility = VISIBLE
    }

    override fun hideProgress() {
        binding.progressBar.visibility = INVISIBLE
    }
}
