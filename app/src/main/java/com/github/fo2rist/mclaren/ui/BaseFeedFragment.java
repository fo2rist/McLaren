package com.github.fo2rist.mclaren.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.github.fo2rist.mclaren.R;
import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.mvp.FeedContract;
import com.github.fo2rist.mclaren.ui.adapters.FeedAdapter;
import dagger.android.support.AndroidSupportInjection;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Displays feed of {@link FeedItem}s.
 * Handle refresh, loading and links clicking.
 * Parent activity must implement {@link FeedAdapter.OnFeedInteractionListener}
 */
public class BaseFeedFragment
        extends Fragment
        implements FeedContract.View, SwipeRefreshLayout.OnRefreshListener, FeedAdapter.OnFeedScrollingListener {

    private RecyclerView feedRecyclerView;
    private LinearLayoutManager feedLayoutManger;
    private SwipeRefreshLayout listRefreshLayout;
    private ProgressBar progressBar;

    private FeedAdapter feedAdapter;

    private FeedAdapter.OnFeedInteractionListener listener;
    @Inject
    FeedContract.Presenter presenter;

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
        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);

        listRefreshLayout = rootView.findViewById(R.id.list_refresh_layout);
        feedRecyclerView = rootView.findViewById(R.id.feed_list);
        progressBar = rootView.findViewById(R.id.progress_bar);

        //setup views
        feedLayoutManger = new LinearLayoutManager(getContext());
        feedRecyclerView.setLayoutManager(feedLayoutManger);
        feedAdapter = new FeedAdapter(getContext(), listener, this);
        feedRecyclerView.setAdapter(feedAdapter);
        listRefreshLayout.setOnRefreshListener(this);

        presenter.onStart(this);
        return rootView;
    }

    @Override
    public void onRefresh() {
        presenter.onRefreshRequested();
    }

    @Override
    public void setFeed(final List<FeedItem> feedItems) {
        boolean hasNewerItems = feedAdapter.setItems(feedItems);
        if (hasNewerItems && firstItemIsVisible()) {
            feedRecyclerView.scrollToPosition(0);
        }
    }

    private boolean firstItemIsVisible() {
        return feedLayoutManger.findFirstVisibleItemPosition() <= 0;
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        //refresh layout shows progress by itself we just hide it once request is done
        listRefreshLayout.setRefreshing(false);

        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDestroyView() {
        presenter.onStop();
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onScrolledToSecondThird() {
        presenter.onScrolledToSecondThird();
    }

    @Override
    public void onScrolledToBottom() {
        presenter.onLoadMoreRequested();
    }
}
