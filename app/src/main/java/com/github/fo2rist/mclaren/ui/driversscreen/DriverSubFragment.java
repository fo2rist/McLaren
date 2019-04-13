package com.github.fo2rist.mclaren.ui.driversscreen;

import android.content.Context;
import android.net.Uri;
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
import com.github.fo2rist.mclaren.repository.DriversRepository;
import com.github.fo2rist.mclaren.ui.models.DriverId;
import com.github.fo2rist.mclaren.ui.models.DriverProperty;
import com.github.fo2rist.mclaren.ui.models.Driver;
import com.github.fo2rist.mclaren.ui.widgets.InformationLineView;
import dagger.android.support.AndroidSupportInjection;
import javax.inject.Inject;
import timber.log.Timber;

import static android.text.TextUtils.isEmpty;

public class DriverSubFragment extends Fragment implements View.OnClickListener {

    public interface OnDriverSubFragmentInteractionListener {
        void onDriverSubFragmentInteraction(Uri uri);
    }

    private static final String ARG_DRIVER = "ARG_DRIVER";

    private static final DriverProperty[] PROPERTIES_TO_DISPLAY_IN_LIST = {
            DriverProperty.Name,
            DriverProperty.DateOfBirth,
            DriverProperty.Nationality,
            DriverProperty.Twitter,
            DriverProperty.WorldChampionships,
            DriverProperty.BestFinish,
            DriverProperty.Podiums,
            DriverProperty.PolePositions,
            DriverProperty.FastestLaps
    };

    @Inject
    DriversRepository driversRepository;

    private OnDriverSubFragmentInteractionListener listener;

    private Driver driver;

    public static DriverSubFragment newInstance(DriverId driverId) {
        DriverSubFragment fragment = new DriverSubFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DRIVER, driverId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        if (context instanceof OnDriverSubFragmentInteractionListener) {
            listener = (OnDriverSubFragmentInteractionListener) context;
        } else {
            Timber.e("%s must implement OnDriverSubFragmentInteractionListener", context.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
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
            return;
        }
        DriverId driverId = (DriverId) args.getSerializable(ARG_DRIVER);
        driver = driversRepository.getDriver(driverId);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_driver_item,
                container,
                false);

        populateViews(rootView);
        return rootView;
    }

    private void populateViews(View rootView) {
        if (driver == null) {
            Timber.e("No driver model provided to display");
            return;
        }
        TextView driverNumberTextView = rootView.findViewById(R.id.driver_number_text);
        driverNumberTextView.setText(driver.get(DriverProperty.Tag));

        TextView subtitleTextView = rootView.findViewById(R.id.driver_result_text);
        subtitleTextView.setText(
                getPlaceAndPointsText());

        ImageView portraitView = rootView.findViewById(R.id.driver_portrait_image);
        portraitView.setImageURI(
                getPortraitImageUri());

        View teamLinkButton = rootView.findViewById(R.id.team_link_button);
        if (driver.get(DriverProperty.TeamPageLink) != null) {
            teamLinkButton.setVisibility(View.VISIBLE);
            teamLinkButton.setOnClickListener(this);
        }
        View heritageLinkButton = rootView.findViewById(R.id.heritage_link_button);
        if (driver.get(DriverProperty.HeritagePageLink) != null) {
            heritageLinkButton.setVisibility(View.VISIBLE);
            heritageLinkButton.setOnClickListener(this);
        }

        LinearLayout propertiesList = rootView.findViewById(R.id.properties_linearlayout);
        for(DriverProperty property: PROPERTIES_TO_DISPLAY_IN_LIST) {
            String propertyValue = driver.get(property);
            if (propertyValue == null) {
                continue;
            }

            InformationLineView propertyView = new InformationLineView(getContext());
            propertyView.setContent(property.getNameResId(), propertyValue);
            propertiesList.addView(propertyView);
        }
    }

    @NonNull
    private String getPlaceAndPointsText() {
        String place = driver.get(DriverProperty.Place);
        String points = driver.get(DriverProperty.Points);

        if (isEmpty(place) || isEmpty(points)) {
            return "";
        } else {
            return place + " / " + points;
        }
    }

    private Uri getPortraitImageUri() {
        return Uri.parse("android.resource://com.github.fo2rist.mclaren/drawable/driver_" + driver.getId());
    }

    @Override
    public void onClick(View sender) {
        switch (sender.getId()) {
            case R.id.team_link_button:
                openLink(driver.get(DriverProperty.TeamPageLink));
                break;
            case R.id.heritage_link_button:
                openLink(driver.get(DriverProperty.HeritagePageLink));
                break;
            default:
                throw new IllegalArgumentException("Unknown view clicked. Id: " + sender.getId());
        }
    }

    private void openLink(String uriString) {
        if (listener != null) {
            listener.onDriverSubFragmentInteraction(Uri.parse(uriString));
        }
    }
}
