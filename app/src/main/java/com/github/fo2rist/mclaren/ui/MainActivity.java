package com.github.fo2rist.mclaren.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.github.fo2rist.mclaren.ui.adapters.FeedAdapter;
import com.github.fo2rist.mclaren.ui.models.CalendarEvent;
import com.github.fo2rist.mclaren.ui.utils.LinkUtils;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import javax.inject.Inject;

import static com.github.fo2rist.mclaren.ui.utils.IntentUtils.openInBrowser;
import static com.github.fo2rist.mclaren.ui.utils.LinkUtils.getMediaLink;


public class MainActivity extends AppCompatActivity
        implements HasSupportFragmentInjector,
        NavigationView.OnNavigationItemSelectedListener,
        CircuitsFragment.OnCircuitsFragmentInteractionListener,
        DriversFragment.OnDriversFragmentInteractionListener,
        DriverSubFragment.OnDriverSubFragmentInteractionListener,
        FeedAdapter.OnFeedInteractionListener
{
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO navigate to transmission center once the race is live.
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationViewMain = findViewById(R.id.nav_view_main);
        navigationViewMain.setNavigationItemSelectedListener(this);
        NavigationView navigationViewFooter = findViewById(R.id.nav_view_footer);
        navigationViewFooter.setNavigationItemSelectedListener(this);

        //Populate main content area on the first launch
        if (savedInstanceState == null) {
            navigationViewMain.setCheckedItem(R.id.nav_newsfeed);
            navigateNewsfeed();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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
            openAboutScreen();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation rootView_ item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_newsfeed) {
            navigateNewsfeed();
        } else if (id == R.id.nav_circuits) {
            navigateCircuits();
        } else if (id == R.id.nav_drivers) {
            navigateDrivers();
        } else if (id == R.id.nav_car) {
            openCarPage();
        } else if (id == R.id.nav_official_site) {
            openMcLarenWebsite();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onCircuitSelected(CalendarEvent event, int number) {
        startActivity(CircuitDetailsActivity.createIntent(this, event));
    }

    @Override
    public void onDriversFragmentInteraction(Uri uri) {

    }


    @Override
    public void onDriverSubFragmentIneraction(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void navigateNewsfeed() {
        navigateToNewFragment(
                NewsfeedFragment.newInstance());
    }

    private void navigateCircuits() {
        navigateToNewFragment(
                CircuitsFragment.newInstanceForColumns(2));
    }

    private void navigateDrivers() {
        navigateToNewFragment(
                DriversFragment.newInstance());
    }

    private void openCarPage() {
        openInBrowser(this, LinkUtils.getMcLarenCarLink());
    }

    private void openMcLarenWebsite() {
        openInBrowser(this, LinkUtils.getMcLarenFormula1Link());
    }

    private void openAboutScreen() {
        startActivity(PreviewActivity.createUrlIntent(this, "file:///android_asset/about.html"));
    }

    private void navigateToNewFragment(Fragment fragment) {
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

    @Override
    public void onLastItemDisplayed() {
    }
}
