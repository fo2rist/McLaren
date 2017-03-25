package com.github.fo2rist.mclaren;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import static android.text.TextUtils.isEmpty;

public class DriverSubFragment extends Fragment {

    public interface OnDriverSubFragmentInteractionListener {
        void onDriverSubFragmentIneraction(Uri uri);
    }

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

    private OnDriverSubFragmentInteractionListener listener;

    private Driver driver_;

    public static DriverSubFragment newInstance(DriverId driverId) {
        DriverSubFragment fragment = new DriverSubFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DRIVER, driverId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDriverSubFragmentInteractionListener) {
            listener = (OnDriverSubFragmentInteractionListener) context;
        } else {
            Log.e("McLaren",
                    context.toString() + " must implement OnDriverSubFragmentInteractionListener");
        }
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
                R.layout.fragment_driver_item,
                container,
                false);

        populateViews(rootView, driver_);
        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    private void populateViews(View rootView, Driver driver) {
        TextView titleTextView = (TextView) rootView.findViewById(R.id.driver_number_text);
        titleTextView.setText(driver.getProperty(AdditionalProperty.TAG));

        TextView subtitleTextView = (TextView) rootView.findViewById(R.id.driver_result_text);
        subtitleTextView.setText(
                getPlaceAndPointsText(driver));

        ImageView portraitView = (ImageView) rootView.findViewById(R.id.driver_portrait_image);
        portraitView.setImageURI(
                getPortraitImageUri(driver));

        LinearLayout propertiesList = (LinearLayout) rootView.findViewById(R.id.properties_linearlayout);
        for(Driver.Property property: propertiesToDisplayInList) {
            String propertyValue = driver.getProperty(property);
            if (propertyValue == null) {
                continue;
            }

            DriverDetailsLineView propertyView = new DriverDetailsLineView(getContext());
            propertyView.setLayoutParams(getPropertyLineLayout());
            propertyView.setContent(property.getDisplayName(), propertyValue);
            propertiesList.addView(propertyView);

            propertiesList.invalidate();
        }
    }

    @NonNull
    private String getPlaceAndPointsText(Driver driver) {
        String place = driver.getProperty(AdditionalProperty.PLACE);
        String points = driver.getProperty(AdditionalProperty.POINTS);

        if (isEmpty(place) || isEmpty(points)) {
            return "";
        } else {
            return place + " / " + points;
        }
    }

    private Uri getPortraitImageUri(Driver driver) {
        return Uri.parse("android.resource://com.github.fo2rist.mclaren/drawable/driver_" + driver.getId());
    }

    @NonNull
    private LayoutParams getPropertyLineLayout() {
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(
                getContext().getResources().getDimensionPixelSize(R.dimen.margin_five),//left
                getContext().getResources().getDimensionPixelSize(R.dimen.margin_half), //top
                getContext().getResources().getDimensionPixelSize(R.dimen.margin_five),//right
                getContext().getResources().getDimensionPixelSize(R.dimen.margin_half)  //bottom
        );
        return layoutParams;
    }

}
