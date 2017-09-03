package com.github.fo2rist.mclaren.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class CircuitDetailsActivity extends AppCompatActivity{

    public static Intent createIntent(Context context, String circuitName, int circuitNumber) {
        Intent intent = new Intent(context, CircuitDetailsActivity.class);
        Bundle args = CircuitDetailsFragment.createLaunchBundle(circuitName, circuitNumber);
        intent.putExtras(args);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CircuitDetailsFragment contentFragment = CircuitDetailsFragment.newInstance(getIntent().getExtras());
        setFragment(contentFragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            finish();
            return true;
        }

        return false;
    }

    protected void setFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, fragment)
                .commit();
    }
}
