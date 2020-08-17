package com.github.fo2rist.mclaren.ui.circuitsscreen

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.ui.models.CalendarEvent
import com.github.fo2rist.mclaren.ui.widgets.InformationLineView
import com.github.fo2rist.mclaren.utils.IntentUtils
import com.github.fo2rist.mclaren.utils.ResourcesUtils.getCircuitDetailedImageUriById
import com.github.fo2rist.mclaren.utils.ResourcesUtils.getCircuitDrsImageUriById
import com.github.fo2rist.mclaren.utils.ResourcesUtils.getCircuitSectorsImageUriById
import com.github.fo2rist.mclaren.utils.ResourcesUtils.getCircuitTurnsImageUriById
import kotlinx.android.synthetic.main.fragment_circuit_item.*

/**
 * Fragment that displays single Circuit's map and detailed info.
 */
class CircuitDetailsFragment : Fragment(), View.OnClickListener {

    private lateinit var event: CalendarEvent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchBundleParameters()
    }

    private fun fetchBundleParameters() {
        event = arguments?.getSerializable(ARG_EVENT) as? CalendarEvent
                ?: throw IllegalArgumentException("Required parameter `$ARG_EVENT` not present or not a CalendarEvent")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_circuit_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        populateMapImage()
        populateMainInformation()
        populateDetailedInformationLines()
    }

    private fun populateMapImage() {
        circuit_image.setImageURI(
                getCircuitDetailedImageUriById(event.circuitId))
        circuit_drs_image.setImageURI(
                getCircuitDrsImageUriById(event.circuitId))
        circuit_sectors_image.setImageURI(
                getCircuitSectorsImageUriById(event.circuitId))
        circuit_turns_image.setImageURI(
                getCircuitTurnsImageUriById(event.circuitId))
    }

    private fun populateMainInformation() {
        circuit_title.text = event.grandPrixName
        circuit_title.setOnClickListener(this)

        circuit_details.text = getString(R.string.circuit_details_format, event.city, event.trackName)
    }

    private fun populateDetailedInformationLines() {
        properties_linearlayout.addInformationLine(
                R.string.circuit_details_laps,
                event.laps.toString())
        properties_linearlayout.addInformationLine(
                R.string.circuit_details_length,
                getString(R.string.distance_km_format, event.length))
        properties_linearlayout.addInformationLine(
                R.string.circuit_details_distance,
                getString(R.string.distance_km_format, event.distance))
        properties_linearlayout.addInformationLine(
                R.string.circuit_details_seasons,
                event.seasons)
        properties_linearlayout.addInformationLine(
                R.string.circuit_details_gp_held,
                event.gpHeld.toString())
    }

    private fun LinearLayout.addInformationLine(@StringRes propertyTitle: Int, propertyValue: String) {
        val propertyView = InformationLineView(context)
        propertyView.setContent(propertyTitle, propertyValue)
        addView(propertyView)
    }

    override fun onClick(view: View) {
        @SuppressWarnings("UnsafeCallOnNullableType")
        if (view.id == R.id.circuit_title) {
            IntentUtils.launchSafely(requireContext(), IntentUtils.createBrowserIntent(event.wikiLink))
        }
    }

    companion object {

        private const val ARG_EVENT = "event"

        /**
         * Create bundle with circuit info to be used with [newInstance].
         */
        @JvmStatic
        fun createLaunchBundle(event: CalendarEvent): Bundle {
            val args = Bundle()
            args.putSerializable(ARG_EVENT, event)
            return args
        }

        /**
         * Create fragment to display circuit.
         */
        @JvmStatic
        fun newInstance(args: Bundle): CircuitDetailsFragment {
            val fragment = CircuitDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
