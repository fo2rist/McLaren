package com.github.fo2rist.mclaren.ui.splashscreen;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.github.fo2rist.mclaren.ui.MainActivity;

/**
 * Activity that shows the image_splash image while app is initialized.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //there is no layout by intention so the image is loaded before any layout can be instantiated
        //it works because the image_splash image is a part of window background
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
