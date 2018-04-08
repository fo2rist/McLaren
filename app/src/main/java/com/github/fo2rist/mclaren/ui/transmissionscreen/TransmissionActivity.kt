package com.github.fo2rist.mclaren.ui.transmissionscreen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.ui.utils.animateReveal
import com.github.fo2rist.mclaren.ui.utils.animateUnreveal

class TransmissionActivity : AppCompatActivity() {

    private lateinit var rootLayout: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transmission)
        rootLayout = findViewById(R.id.root_layout)

        if (savedInstanceState == null) {
            animateReveal(rootLayout, intent)
        }
    }

    override fun onBackPressed() {
        animateUnreveal(rootLayout) {
            supportFinishAfterTransition()
        }
    }

}
