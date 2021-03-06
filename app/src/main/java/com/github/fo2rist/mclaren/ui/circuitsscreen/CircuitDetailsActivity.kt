package com.github.fo2rist.mclaren.ui.circuitsscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.analytics.Events
import com.github.fo2rist.mclaren.analytics.Analytics
import com.github.fo2rist.mclaren.repository.remoteconfig.RaceCalendarRepository
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * Display information about particular circuit including the map.
 */
class CircuitDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var raceCalendarRepository: RaceCalendarRepository

    @Inject
    lateinit var analytics: Analytics

    private val circuitFragmentsPager by lazy { findViewById<ViewPager>(R.id.circuits_pager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)

        setupToolBar()
        setContentView(R.layout.activity_circuit_details)

        val eventNumber = intent.getIntExtra(KEY_EVENT_NUMBER, 0)
        circuitFragmentsPager.adapter = CircuitDetailsAdapter(supportFragmentManager,
                raceCalendarRepository.loadCalendar())
        circuitFragmentsPager.setCurrentItem(eventNumber, false)

        analytics.overrideScreenName(Events.Screen.CIRCUIT)
    }

    private fun setupToolBar() {
        val actionBar = requireNotNull(supportActionBar)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setBackgroundDrawable(resources.getDrawable(R.drawable.background_app_bar, null))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    companion object {

        private const val KEY_EVENT_NUMBER = "event_number"

        @JvmStatic
        fun createIntent(context: Context, eventNumber: Int): Intent {
            val intent = Intent(context, CircuitDetailsActivity::class.java)
            intent.putExtra(KEY_EVENT_NUMBER, eventNumber)
            return intent
        }
    }
}
