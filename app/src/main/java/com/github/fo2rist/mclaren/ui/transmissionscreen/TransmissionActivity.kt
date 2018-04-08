package com.github.fo2rist.mclaren.ui.transmissionscreen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.mvp.TransmissionContract
import com.github.fo2rist.mclaren.ui.utils.animateReveal
import com.github.fo2rist.mclaren.ui.utils.animateUnreveal
import dagger.android.AndroidInjection
import javax.inject.Inject

class TransmissionActivity : AppCompatActivity(), TransmissionContract.View {

    private lateinit var rootLayout: View

    @Inject
    lateinit var presenter: TransmissionContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transmission)
        rootLayout = findViewById(R.id.root_layout)

        if (savedInstanceState == null) {
            animateReveal(rootLayout, intent)
        }

        presenter.onStart(this)
    }

    override fun onBackPressed() {
        animateUnreveal(rootLayout) {
            supportFinishAfterTransition()
        }
    }

    override fun onPause() {
        if (isFinishing) {
            presenter.onStop()
        }
        super.onPause()
    }

    override fun displayTransmission() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
