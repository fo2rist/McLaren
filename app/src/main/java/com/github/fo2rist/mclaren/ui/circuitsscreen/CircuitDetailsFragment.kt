package com.github.fo2rist.mclaren.ui.circuitsscreen

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.databinding.ActivityCircuitDetailsBinding
import com.github.fo2rist.mclaren.databinding.FragmentCircuitItemBinding
import com.github.fo2rist.mclaren.ui.models.CalendarEvent
import com.github.fo2rist.mclaren.ui.widgets.InformationLineView
import com.github.fo2rist.mclaren.utils.IntentUtils
import com.github.fo2rist.mclaren.utils.ResourcesUtils.getCircuitDetailedImageUriById
import com.github.fo2rist.mclaren.utils.ResourcesUtils.getCircuitDrsImageUriById
import com.github.fo2rist.mclaren.utils.ResourcesUtils.getCircuitSectorsImageUriById
import com.github.fo2rist.mclaren.utils.ResourcesUtils.getCircuitTurnsImageUriById

/**
 * Fragment that displays single Circuit's map and detailed info.
 */
class CircuitDetailsFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentCircuitItemBinding

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
    ): View {
        binding = FragmentCircuitItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        populateMapImage()
        populateMainInformation()
        populateDetailedInformationLines()
    }

    private fun populateMapImage() {
        binding.circuitImage.setImageURI(
                getCircuitDetailedImageUriById(event.circuitId))
        binding.circuitDrsImage.setImageURI(
                getCircuitDrsImageUriById(event.circuitId))
        binding.circuitSectorsImage.setImageURI(
                getCircuitSectorsImageUriById(event.circuitId))
        binding.circuitTurnsImage.setImageURI(
                getCircuitTurnsImageUriById(event.circuitId))
    }

    private fun populateMainInformation() {
        binding.circuitTitle.text = event.grandPrixName
        binding.circuitTitle.setOnClickListener(this)

        binding.circuitDetails.text = getString(R.string.circuit_details_format, event.city, event.trackName)
    }

    private fun populateDetailedInformationLines() {
        binding.propertiesLinearlayout.addInformationLine(
                R.string.circuit_details_laps,
                event.laps.toString())
        binding.propertiesLinearlayout.addInformationLine(
                R.string.circuit_details_length,
                getString(R.string.distance_km_format, event.length))
        binding.propertiesLinearlayout.addInformationLine(
                R.string.circuit_details_distance,
                getString(R.string.distance_km_format, event.distance))
        binding.propertiesLinearlayout.addInformationLine(
                R.string.circuit_details_seasons,
                event.seasons)
        binding.propertiesLinearlayout.addInformationLine(
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
         * Create fragment to display circuit/event info.
         */
        @JvmStatic
        fun newInstance(event: CalendarEvent): CircuitDetailsFragment {
            return CircuitDetailsFragment().apply {
                arguments = Bundle().apply { putSerializable(ARG_EVENT, event) }
            }
        }
    }
}
