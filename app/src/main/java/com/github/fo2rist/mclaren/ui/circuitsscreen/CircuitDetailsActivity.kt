package com.github.fo2rist.mclaren.ui.circuitsscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.ui.models.CalendarEvent

/**
 * Display information about particular circuit including the map.
 */
class CircuitDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras = intent.extras
        if (extras == null) {
            finish()
            return
        }

        setupToolBar()
        setContentFragment(CircuitDetailsFragment.newInstance(extras))
    }

    private fun setupToolBar() {
        val actionBar = requireNotNull(supportActionBar)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setBackgroundDrawable(resources.getDrawable(R.drawable.background_app_bar, null))
    }

    private fun setContentFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(android.R.id.content, fragment)
                .commit()
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

        @JvmStatic
        fun createIntent(context: Context, event: CalendarEvent): Intent {
            val intent = Intent(context, CircuitDetailsActivity::class.java)
            val args = CircuitDetailsFragment.createLaunchBundle(event)
            intent.putExtras(args)
            return intent
        }
    }
}
