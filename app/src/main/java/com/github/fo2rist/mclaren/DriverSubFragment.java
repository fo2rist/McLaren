package com.github.fo2rist.mclaren;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.github.fo2rist.mclaren.models.Driver;
import com.github.fo2rist.mclaren.models.Driver.AdditionalProperty;
import com.github.fo2rist.mclaren.models.Driver.MandatoryProperty;
import com.github.fo2rist.mclaren.models.DriversFactory;
import com.github.fo2rist.mclaren.models.DriversFactory.DriverId;
import com.github.fo2rist.mclaren.widgets.DriverDetailsLineView;

public class DriverSubFragment extends Fragment {
    public static final String ARG_DRIVER = "ARG_DRIVER";

    private static final Driver.Property[] propertiesToDisplayInList = {
            MandatoryProperty.NAME,
            MandatoryProperty.DATE_OF_BIRTH,
            MandatoryProperty.NATIONALITY,
            MandatoryProperty.TWITTER,
            AdditionalProperty.WORLD_CHAMPIONSHIPS,
            AdditionalProperty.BEST_FINISH,
            AdditionalProperty.PODIUMS,
            AdditionalProperty.POLE_POSITIONS,
            AdditionalProperty.FASTEST_LAPS
    };

    private Driver driver_;

    public static DriverSubFragment newInstance(DriverId driverId) {
        DriverSubFragment fragment = new DriverSubFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DRIVER, driverId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        DriverId driverId = (DriverId) args.getSerializable(ARG_DRIVER);
        driver_ = DriversFactory.getDriverModel(driverId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.item_driver,
                container,
                false);

        populateViews(rootView, driver_);
        return rootView;
    }

    private void populateViews(View rootView, Driver driver) {
        TextView titleTextView = (TextView) rootView.findViewById(R.id.driver_number_text);
        titleTextView.setText(driver.getProperty(AdditionalProperty.TAG));

        TextView subtitleTextView = (TextView) rootView.findViewById(R.id.driver_result_text);
        subtitleTextView.setText( driver.getProperty(AdditionalProperty.PLACE)
                + " / "
                + driver.getProperty(AdditionalProperty.POINTS));

        ImageView portraitView = (ImageView) rootView.findViewById(R.id.driver_portrait_image);
        portraitView.setImageURI(
                Uri.parse("android.resource://com.github.fo2rist.mclaren/drawable/driver_" + driver.getId()));

        LinearLayout propertiesList = (LinearLayout) rootView.findViewById(R.id.properties_linearlayout);
        for(Driver.Property property: propertiesToDisplayInList) {
            String propertyValue = driver.getProperty(property);
            if (propertyValue == null) {
                continue;
            }

            DriverDetailsLineView propertyView = new DriverDetailsLineView(getContext());
            propertyView.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            propertyView.setContent(property.getDisplayName(), propertyValue);
            propertiesList.addView(propertyView);

            propertiesList.invalidate();
        }
    }

    public String getTitle() {
        return driver_.getProperty(MandatoryProperty.NAME);
    }
}
