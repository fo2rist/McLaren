package com.github.fo2rist.mclaren.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.fo2rist.mclaren.R;
import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.mvp.MainScreenContract;
import com.github.fo2rist.mclaren.ui.adapters.FeedAdapter;
import com.github.fo2rist.mclaren.ui.models.CalendarEvent;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import javax.inject.Inject;
import timber.log.Timber;

import static com.github.fo2rist.mclaren.utils.IntentUtils.createMcLarenAppIntent;
import static com.github.fo2rist.mclaren.utils.IntentUtils.launchSafely;
import static com.github.fo2rist.mclaren.utils.IntentUtils.openInBrowser;
import static com.github.fo2rist.mclaren.utils.LinkUtils.getMediaLink;


public class MainActivity extends AppCompatActivity
        implements HasSupportFragmentInjector,
        MainScreenContract.View,
        NavigationView.OnNavigationItemSelectedListener,
        CircuitsFragment.OnCircuitsFragmentInteractionListener,
        DriversFragment.OnDriversFragmentInteractionListener,
        DriverSubFragment.OnDriverSubFragmentInteractionListener,
        FeedAdapter.OnFeedInteractionListener,
        View.OnClickListener
{
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;
    @Inject
    MainScreenContract.Presenter presenter;

    private NavigationView navigationViewMain;
    private DrawerLayout menuDrawer;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();

        if (savedInstanceState == null) {
            //Populate main content area only on the first launch
            presenter.onStart(this);
        }
    }

    @Override
    protected void onPause() {
        if (isFinishing()) {
            presenter.onStop();
        }
        super.onPause();
    }

    private void setupViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        menuDrawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, menuDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        menuDrawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationViewMain = findViewById(R.id.nav_view_main);
        NavigationView navigationViewFooter = findViewById(R.id.nav_view_footer);
        FloatingActionButton floatingButtonTransmission = findViewById(R.id.floatig_button_transmission);

        navigationViewMain.setNavigationItemSelectedListener(this);
        navigationViewFooter.setNavigationItemSelectedListener(this);
        floatingButtonTransmission.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        if (menuDrawer.isDrawerOpen(GravityCompat.START)) {
            menuDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            presenter.onAboutClicked();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        menuDrawer.closeDrawer(GravityCompat.START);

        int id = item.getItemId();
        if (id == R.id.nav_news_feed) {
            presenter.onNewsFeedClicked();
        } else if (id == R.id.nav_stories) {
            presenter.onStoriesClicked();
        } else if (id == R.id.nav_circuits) {
            presenter.onCircuitsClicked();
        } else if (id == R.id.nav_drivers) {
            presenter.onDriversClicked();
        } else if (id == R.id.nav_car) {
            presenter.onCarClicked();
        } else if (id == R.id.nav_official_site) {
            presenter.onOfficialSiteClicked();
        } else {
            Timber.e("Unknown menu item clicked: %s", item);
        }

        return true;
    }

    @Override
    public void onClick(View sender) {
        if (sender.getId() == R.id.floatig_button_transmission) {
            presenter.onTransmissionCenterClicked();
        }
    }

    @Override
    public void onCircuitSelected(CalendarEvent event, int number) {
        startActivity(CircuitDetailsActivity.createIntent(this, event));
    }

    @Override
    public void onDriversFragmentInteraction(Uri uri) {
        //empty
    }

    @Override
    public void onDriverSubFragmentInteraction(Uri uri) {
        openInBrowser(this, uri);
    }

    @Override
    public void openStories() {
        openNewFragment(
                StoriesFeedFragment.newInstance());
    }

    @Override
    public void openCircuits() {
        openNewFragment(
                CircuitsFragment.newInstanceForColumns(2));
    }

    @Override
    public void openDrivers() {
        openNewFragment(
                DriversFragment.newInstance());
    }

    @Override
    public void openNewsFeed() {
        openNewFragment(
                McLarenFeedFragment.newInstance());
    }

    @Override
    public void openAboutScreen() {
        startActivity(PreviewActivity.createUrlIntent(this, "file:///android_asset/about.html"));
    }

    @Override
    public void openTransmissionCenter() {
        //TODO navigate to transmission center once the race is live.
        launchSafely(MainActivity.this, createMcLarenAppIntent(MainActivity.this));
    }

    @Override
    public void navigateTo(String externalUrl) {
        openInBrowser(this, externalUrl);
    }

    private void openNewFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_content_frame, fragment)
                .commit();
    }

    @Override
    public void onItemDetailsRequested(FeedItem item) {
        if (item.type == FeedItem.Type.Video) {
            //TODO just play video like all other media types
            openInBrowser(this, getMediaLink(item));
        } else {
            startActivity(PreviewActivity.createFeedItemIntent(this, item));
        }
    }
}
