package com.github.fo2rist.mclaren;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.fo2rist.mclaren.adapters.FeedAdapter;

import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnNewsfeedFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsfeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsfeedFragment extends Fragment {

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnNewsfeedFragmentInteractionListener {
        // TODO: Update argument type and name
        void onNewsfeedFragmentInteraction(Uri uri);
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView listFeed;
    private SwipeRefreshLayout listRefreshLayout;

    private FeedAdapter feedAdapter_;

    private OnNewsfeedFragmentInteractionListener listener_;

    public NewsfeedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsfeedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsfeedFragment newInstance(String param1, String param2) {
        NewsfeedFragment fragment = new NewsfeedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNewsfeedFragmentInteractionListener) {
            listener_ = (OnNewsfeedFragmentInteractionListener) context;
        } else {
            Timber.e("%s must implement OnDriversFragmentInteractionListener", context.toString());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_newsfeed, container, false);

        listFeed = (RecyclerView) rootView.findViewById(R.id.list_feed);
        listRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.list_refresh_layout);

        //setup views
        listFeed.setHasFixedSize(true);
        listFeed.setLayoutManager(new LinearLayoutManager(getContext()));
        feedAdapter_ = new FeedAdapter(getContext());
        listFeed.setAdapter(feedAdapter_);

        listRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /*DEBUG*/
                Snackbar.make(listFeed, "Not now boy", Snackbar.LENGTH_SHORT).show();
                listRefreshLayout.setRefreshing(false);
                /*END DEBUG*/
            }
        });

        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener_ = null;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (listener_ != null) {
            listener_.onNewsfeedFragmentInteraction(uri);
        }
    }

}
