package com.github.fo2rist.mclaren.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.fo2rist.mclaren.R;
import com.github.fo2rist.mclaren.ui.models.CalendarEvent;
import com.github.fo2rist.mclaren.ui.widgets.InformationLineView;
import com.github.fo2rist.mclaren.utils.IntentUtils;
import timber.log.Timber;

import static com.github.fo2rist.mclaren.utils.ResourcesUtils.getCircuitDetailedImageUriById;


public class CircuitDetailsFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_EVENT = "event";

    @Nullable
    private CalendarEvent event;

    @NonNull
    public static Bundle createLaunchBundle(CalendarEvent event) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_EVENT, event);
        return args;
    }

    public static CircuitDetailsFragment newInstance(Bundle args) {
        CircuitDetailsFragment fragment = new CircuitDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchBundleParameters();
    }

    private void fetchBundleParameters() {
        Bundle args = getArguments();
        if (args == null) {
            Timber.e("No arguments provided for fragment");
            //TODO forward error-level logging to Crashlytics. 2018-02-25
            return;
        }

        event = (CalendarEvent) args.getSerializable(ARG_EVENT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_circuit_item, container, false);
        populateView(rootView);
        return rootView;
    }

    private void populateView(View rootView) {
        if (event == null) {
            Timber.e("No circuit model provided to display");
            return;
        }

        ImageView circuitImageView = rootView.findViewById(R.id.circuit_image);
        circuitImageView.setImageURI(getCircuitDetailedImageUriById(event.circuitId));

        TextView circuitTitleView = rootView.findViewById(R.id.circuit_title);
        circuitTitleView.setText(event.grandPrixName);
        circuitTitleView.setOnClickListener(this);

        TextView circuitDetailsView = rootView.findViewById(R.id.circuit_details);
        circuitDetailsView.setText(getString(R.string.circuit_details_format, event.city, event.trackName));

        LinearLayout propertiesList = rootView.findViewById(R.id.properties_linearlayout);

        addInformationLine(propertiesList, "Laps", String.valueOf(event.laps));
        addInformationLine(propertiesList, "Length", getString(R.string.distance_km_format, event.length));
        addInformationLine(propertiesList, "Total Distance", getString(R.string.distance_km_format, event.distance));
        addInformationLine(propertiesList, "Seasons", event.seasons);
        addInformationLine(propertiesList, "Grand Prix Held", String.valueOf(event.gpHeld));
    }

    private void addInformationLine(LinearLayout propertiesList, String propertyName, String propertyValue) {
        InformationLineView propertyView = new InformationLineView(getContext());
        propertyView.setContent(propertyName, propertyValue);
        propertiesList.addView(propertyView);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.circuit_title) {
            IntentUtils.launchSafely(getContext(), IntentUtils.createBrowserIntent(event.wikiLink));
        }
    }
}
