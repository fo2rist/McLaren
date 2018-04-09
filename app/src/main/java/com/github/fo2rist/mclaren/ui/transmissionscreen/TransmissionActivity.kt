package com.github.fo2rist.mclaren.ui.transmissionscreen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.models.TransmissionItem
import com.github.fo2rist.mclaren.mvp.TransmissionContract
import com.github.fo2rist.mclaren.ui.utils.animateReveal
import com.github.fo2rist.mclaren.ui.utils.animateUnreveal
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_transmission.*
import javax.inject.Inject

class TransmissionActivity : AppCompatActivity(), TransmissionContract.View {

    private lateinit var adapter: TransmissionAdapter

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
        transmission_list.layoutManager = LinearLayoutManager(this)
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

    override fun displayTransmission(transmissionItems: List<TransmissionItem>) {
        adapter.setItems(transmissionItems)
    }

    override fun displayCurrentSession(session: TransmissionItem.Session) {
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
    }
}
