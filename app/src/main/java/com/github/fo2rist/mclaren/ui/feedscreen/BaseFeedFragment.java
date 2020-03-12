package com.github.fo2rist.mclaren.ui.feedscreen;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.github.fo2rist.mclaren.R;
import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.mvp.FeedContract;
import com.github.fo2rist.mclaren.ui.previewscreen.PreviewActivity;
import dagger.android.support.AndroidSupportInjection;
import java.util.List;
import javax.inject.Inject;

import static com.github.fo2rist.mclaren.utils.IntentUtils.openInBrowser;

/**
 * Displays feed of {@link FeedItem}s.
 * Handle refresh, loading and links clicking.
 */
public class BaseFeedFragment
        extends Fragment
        implements FeedContract.View, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    FeedContract.Presenter presenter;

    private RecyclerView feedRecyclerView;
    private LinearLayoutManager feedLayoutManger;
    private SwipeRefreshLayout listRefreshLayout;
    private ProgressBar progressBar;

    private FeedAdapter feedAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);

        bindViews(rootView);
        setupViews();

        presenter.onStart();
        return rootView;
    }

    private void bindViews(View rootView) {
        listRefreshLayout = rootView.findViewById(R.id.list_refresh_layout);
        feedRecyclerView = rootView.findViewById(R.id.feed_list);
        progressBar = rootView.findViewById(R.id.progress_bar);
    }

    private void setupViews() {
        feedLayoutManger = new LinearLayoutManager(getContext());
        feedRecyclerView.setLayoutManager(feedLayoutManger);
        feedAdapter = new FeedAdapter(getContext(), presenter, presenter);
        feedRecyclerView.setAdapter(feedAdapter);
        listRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        presenter.onRefreshRequested();
    }

    @Override
    public void onDestroyView() {
        presenter.onStop();
        super.onDestroyView();
    }

    @Override
    public void displayFeed(@NonNull final List<FeedItem> feedItems) {
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
    public void navigateToBrowser(@NonNull String link) {
        openInBrowser(getContext(), link);
    }

    @Override
    public void navigateToPreview(@NonNull String link) {
        startActivity(PreviewActivity.createUrlIntent(getContext(), link));
    }

    @Override
    public void navigateToPreview(@NonNull FeedItem item) {
        startActivity(PreviewActivity.createFeedItemIntent(getContext(), item));
    }
}
