/**/package com.github.fo2rist.mclaren;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        NewsfeedFragment.OnNewsfeedFragmentInteractionListener,
        CircuitsFragment.OnCircuitsFragmentInteractionListener,
        DriversFragment.OnDriversFragmentInteractionListener,
        DriverSubFragment.OnDriverSubFragmentInteractionListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Populate main content area on the first launch
        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.nav_newsfeed);
            navigateNewsfeed();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
    public void onCircuitsFragmentInteraction(String circuitName, int number) {
        Snackbar.make(getWindow().getCurrentFocus(), circuitName, Snackbar.LENGTH_SHORT).show();
        //TODO start activity here 28.12 fo2rist
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
                new NewsfeedFragment());
    }

    private void navigateCircuits() {
        navigateToNewFragment(
                CircuitsFragment.newInstanceForColumns(2));
    }

    private void navigateDrivers() {
        navigateToNewFragment(
                new DriversFragment() );
    }

    private void navigateCars() {

    }

    private void navigateToNewFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_content_frame, fragment)
                .commit();
    }

}