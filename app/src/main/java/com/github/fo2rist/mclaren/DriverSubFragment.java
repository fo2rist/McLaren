package com.github.fo2rist.mclaren;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DriverSubFragment extends Fragment {
    public static final String ARG_DRIVER_NAME = "object";

    public static DriverSubFragment newInstance(String driverName) {
        DriverSubFragment fragment = new DriverSubFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DRIVER_NAME, driverName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.item_circuit,
                container,
                false);
        Bundle args = getArguments();

        String driverName = args.getString(ARG_DRIVER_NAME, "");
        TextView titleTextView = (TextView) rootView.findViewById(R.id.text_name);
        titleTextView.setText(driverName);
        return rootView;
    }

    public String getTitle() {
        return "test";
    }
}
