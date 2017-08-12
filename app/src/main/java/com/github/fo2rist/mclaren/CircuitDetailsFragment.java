package com.github.fo2rist.mclaren;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import static com.github.fo2rist.mclaren.uiutils.ResourcesUtils.getCircuitDetailedImageUriByNumber;


public class CircuitDetailsFragment extends Fragment {

    private static final String ARG_CIRCUIT_NAME = "circuit_name";
    private static final String ARG_CIRCUIT_NUMBER = "circuit_number";

    private String circuitName;
    private int circuitNumber;

    @NonNull
    public static Bundle createLaunchBundle(String circuitName, int circuitNumber) {
        Bundle args = new Bundle();
        args.putString(ARG_CIRCUIT_NAME, circuitName);
        args.putInt(ARG_CIRCUIT_NUMBER, circuitNumber);
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
        fetchParameters(getArguments());
    }

    private void fetchParameters(Bundle bundle) {
        if (bundle == null) {
            throw new IllegalArgumentException("No arguments provided");
        }

        circuitName = bundle.getString(ARG_CIRCUIT_NAME, null);
        circuitNumber = bundle.getInt(ARG_CIRCUIT_NUMBER, -1);

        if (circuitName == null) {
            throw new IllegalArgumentException("No circuit provided to display");
        }
        if (circuitNumber < 0) {
            throw new IllegalArgumentException("No circuit number provided");
        }
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
        ImageView circuitImageView = (ImageView) rootView.findViewById(R.id.circuit_image);
        circuitImageView.setImageURI(getCircuitDetailedImageUriByNumber(circuitNumber));
        TextView circuitTitleView = (TextView) rootView.findViewById(R.id.circuit_title);
        circuitTitleView.setText(circuitName);
    }
}
