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
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import javax.inject.Inject;


public class MainActivity extends AppCompatActivity
        implements HasSupportFragmentInjector,
        NavigationView.OnNavigationItemSelectedListener,
        NewsfeedFragment.OnNewsfeedFragmentInteractionListener,
        CircuitsFragment.OnCircuitsFragmentInteractionListener,
        DriversFragment.OnDriversFragmentInteractionListener,
        DriverSubFragment.OnDriverSubFragmentInteractionListener
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

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Populate main content area on the first launch
        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.nav_newsfeed);
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            navigateCars();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onNewsfeedFragmentInteraction(Uri uri) {

    }

    @Override
    public void onCircuitSelected(String circuitName, int number) {
        startActivity(CircuitDetailsActivity.createIntent(this, circuitName, number));
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

    private void navigateCars() {

    }

    private void navigateToNewFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_content_frame, fragment)
                .commit();
    }

    private void addNewFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_content_frame, fragment)
                .addToBackStack("dive_navigation_to_new_fragment")
                .commit();
    }
}
