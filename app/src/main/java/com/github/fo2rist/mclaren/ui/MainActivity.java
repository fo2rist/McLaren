package com.github.fo2rist.mclaren.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.github.fo2rist.mclaren.R;
import com.github.fo2rist.mclaren.mvp.MainScreenContract;
import com.github.fo2rist.mclaren.ui.circuitsscreen.CircuitDetailsActivity;
import com.github.fo2rist.mclaren.ui.circuitsscreen.CircuitsFragment;
import com.github.fo2rist.mclaren.ui.driversscreen.DriverSubFragment;
import com.github.fo2rist.mclaren.ui.driversscreen.DriversFragment;
import com.github.fo2rist.mclaren.ui.feedscreen.StoriesFeedFragment;
import com.github.fo2rist.mclaren.ui.feedscreen.TwitterFeedFragment;
import com.github.fo2rist.mclaren.ui.previewscreen.PreviewActivity;
import com.github.fo2rist.mclaren.ui.transmissionscreen.TransmissionActivity;
import com.github.fo2rist.mclaren.ui.widgets.UpcomingEventButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import javax.inject.Inject;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import timber.log.Timber;

import static com.github.fo2rist.mclaren.models.TwitterAccounts.TWITTER_DANIEL_RICCIARDO;
import static com.github.fo2rist.mclaren.models.TwitterAccounts.TWITTER_LANDO_NORRIS;
import static com.github.fo2rist.mclaren.models.TwitterAccounts.TWITTER_MCLAREN_F1;
import static com.github.fo2rist.mclaren.ui.utils.AnimationUtils.startActivityWithRevealAnimation;
import static com.github.fo2rist.mclaren.utils.IntentUtils.openInBrowser;


public class MainActivity extends AppCompatActivity
        implements HasAndroidInjector,
        MainScreenContract.View,
        NavigationView.OnNavigationItemSelectedListener,
        CircuitsFragment.OnCircuitsFragmentInteractionListener,
        DriverSubFragment.OnDriverSubFragmentInteractionListener,
        View.OnClickListener
{
    @Inject
    DispatchingAndroidInjector<Object> fragmentInjector;
    @Inject
    MainScreenContract.Presenter presenter;

    private DrawerLayout menuDrawer;
    private FloatingActionButton floatingButtonTransmission;
    private UpcomingEventButton buttonUpcomingEvent;

    @Override
    public AndroidInjector<Object> androidInjector() {
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
            //otherwise, the fragment fill be restored by system, no need to do anything
            presenter.onStart();
        }
    }

    private void setupViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        menuDrawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, menuDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        menuDrawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationViewMain = findViewById(R.id.nav_view_main);
        navigationViewMain.setNavigationItemSelectedListener(this);

        NavigationView navigationViewFooter = findViewById(R.id.nav_view_footer);
        navigationViewFooter.setNavigationItemSelectedListener(this);

        floatingButtonTransmission = findViewById(R.id.floating_button_transmission);
        floatingButtonTransmission.setOnClickListener(this);

        buttonUpcomingEvent = findViewById(R.id.floating_button_upcoming_event);
        buttonUpcomingEvent.setOnClickListener(this);
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
        if (id == R.id.nav_stories) {
            presenter.onStoriesClicked();
        } else if (id == R.id.nav_twitter_team) {
            presenter.onTeamTwitterClicked();
        } else if (id == R.id.nav_twitter_lando) {
            presenter.onLandoTwitterClicked();
        } else if (id == R.id.nav_twitter_daniel) {
            presenter.onDanielTwitterClicked();
        } else if (id == R.id.nav_season_calendar) {
            presenter.onSeasonCalendarClicked();
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
        switch (sender.getId()) {
            case R.id.floating_button_transmission:
                presenter.onTransmissionCenterClicked();
                break;
            case R.id.floating_button_upcoming_event:
                presenter.onUpcomingEventClicked();
                break;
            default:
                Timber.e("Unknown view clicked: %s", sender);
                break;
        }
    }

    @Override
    public void onCircuitSelected(int eventNumber) {
        navigateToCircuitScreen(eventNumber);
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
    public void openTweetsMcLaren() {
        openNewFragment(TwitterFeedFragment.newInstance(TWITTER_MCLAREN_F1));
    }

    @Override
    public void openTweetsLando() {
        openNewFragment(TwitterFeedFragment.newInstance(TWITTER_LANDO_NORRIS));
    }

    @Override
    public void openTweetsDaniel() {
        openNewFragment(TwitterFeedFragment.newInstance(TWITTER_DANIEL_RICCIARDO));
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
    public void openTransmissionCenter() {
        Intent intent = new Intent(this, TransmissionActivity.class);

        startActivityWithRevealAnimation(this, intent, floatingButtonTransmission);
    }

    @Override
    public void navigateToCircuitScreen(int eventNumber) {
        startActivity(CircuitDetailsActivity.createIntent(this, eventNumber));
    }

    @Override
    public void navigateToAboutScreen() {
        startActivity(PreviewActivity.createUrlIntent(this, "file:///android_asset/about.html"));
    }

    @Override
    public void navigateTo(@NonNull String externalUrl) {
        openInBrowser(this, externalUrl);
    }

    private void openNewFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_content_frame, fragment)
                .commit();
    }

    @Override
    public void showTransmissionButton() {
        floatingButtonTransmission.show();
    }

    @Override
    public void showUpcomingEventButton(@NotNull String grandPrixName, @NotNull DateTime beginningTime) {
        buttonUpcomingEvent.setUpcomingEvent(grandPrixName, beginningTime);
        buttonUpcomingEvent.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFloatingButtons() {
        floatingButtonTransmission.hide();
        buttonUpcomingEvent.setVisibility(View.GONE);
    }
}
