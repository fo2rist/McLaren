package com.github.fo2rist.mclaren.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.fo2rist.mclaren.R;
import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.mvp.NewsfeedContract;
import com.github.fo2rist.mclaren.ui.adapters.FeedAdapter;
import dagger.android.support.AndroidSupportInjection;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Shows social feed.
 * Parent activity must implement {@link FeedAdapter.OnFeedInteractionListener}
 */
public class NewsfeedFragment extends Fragment implements NewsfeedContract.View, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView listFeed;
    private SwipeRefreshLayout listRefreshLayout;

    private FeedAdapter feedAdapter;

    private FeedAdapter.OnFeedInteractionListener listener;
    @Inject
    NewsfeedContract.Presenter presenter;

    public NewsfeedFragment() {
        // Required empty public constructor
    }

    public static NewsfeedFragment newInstance() {
        return new NewsfeedFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FeedAdapter.OnFeedInteractionListener) {
            listener = (FeedAdapter.OnFeedInteractionListener) context;
        } else {
            Timber.e("%s must implement FeedAdapter.OnFeedInteractionListener", context.toString());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_newsfeed, container, false);

        listFeed = rootView.findViewById(R.id.list_feed);
        listRefreshLayout = rootView.findViewById(R.id.list_refresh_layout);

        //setup views
        listFeed.setLayoutManager(new LinearLayoutManager(getContext()));
        feedAdapter = new FeedAdapter(getContext(), listener);
        listFeed.setAdapter(feedAdapter);
        listRefreshLayout.setOnRefreshListener(this);

        /*DEBUG*/debug_handler = new Handler(Looper.getMainLooper());
        presenter.onStart(this);
        return rootView;
    }

    @Override
    public void onRefresh() {
        presenter.onRefreshRequested();
    }

    Handler debug_handler;
    @Override
    public void setFeed(final List<FeedItem> feedItems) {
        /*DEBUG*/
        debug_handler.post(new Runnable() {
            @Override
            public void run() {
                feedAdapter.setItems(feedItems);
                /*DEBUG*///should be also called then request timed out
                listRefreshLayout.setRefreshing(false);
                /*END DEBUG*/
            }
        });
        /*END DEBUG*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
