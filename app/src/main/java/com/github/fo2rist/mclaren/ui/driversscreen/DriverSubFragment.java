package com.github.fo2rist.mclaren.ui.driversscreen;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;

import com.github.fo2rist.mclaren.R;
import com.github.fo2rist.mclaren.repository.remoteconfig.DriversRepository;
import com.github.fo2rist.mclaren.ui.models.Driver;
import com.github.fo2rist.mclaren.ui.models.DriverId;
import com.github.fo2rist.mclaren.ui.models.DriverProperty;
import com.github.fo2rist.mclaren.ui.widgets.InformationLineView;
import com.github.fo2rist.mclaren.utils.ResourcesUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    public void onAttach(@NonNull Context context) {
        injectDependencies();
        super.onAttach(context);
        if (context instanceof OnDriverSubFragmentInteractionListener) {
            listener = (OnDriverSubFragmentInteractionListener) context;
        } else {
            Timber.e("%s must implement OnDriverSubFragmentInteractionListener", context.toString());
        }
    }

    @VisibleForTesting
    protected void injectDependencies() {
        AndroidSupportInjection.inject(this);
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
        subtitleTextView.setText(makePlaceAndPointsText(driver));

        ImageView portraitView = rootView.findViewById(R.id.driver_portrait_image);
        portraitView.setImageURI(makePortraitResUri(driver));

        FloatingActionButton teamLinkButton = rootView.findViewById(R.id.team_link_button);
        if (driver.get(DriverProperty.TeamPageLink) != null) {
            ((View) teamLinkButton).setVisibility(View.VISIBLE);
            teamLinkButton.setOnClickListener(this);

            int helmetImageResId = makeHelmetImageRes(driver);
            if (helmetImageResId != 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                teamLinkButton.setForeground(getResources().getDrawable(helmetImageResId, null));
            }
        }
        FloatingActionButton heritageLinkButton = rootView.findViewById(R.id.heritage_link_button);
        if (driver.get(DriverProperty.HeritagePageLink) != null) {
            ((View) heritageLinkButton).setVisibility(View.VISIBLE);
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
    private String makePlaceAndPointsText(Driver driverModel) {
        String place = driverModel.get(DriverProperty.Place);
        String points = driverModel.get(DriverProperty.Points);

        if (isEmpty(place) || isEmpty(points)) {
            return "";
        } else {
            return place + " / " + points;
        }
    }

    @NonNull
    private Uri makePortraitResUri(Driver driverModel) {
        return ResourcesUtils.getDriverPortraitResUri(driverModel.getFilesystemId());
    }

    @DrawableRes
    private int makeHelmetImageRes(Driver driverModel) {
        return ResourcesUtils.getHelmetResId(getResources(), driverModel.getFilesystemId());
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
