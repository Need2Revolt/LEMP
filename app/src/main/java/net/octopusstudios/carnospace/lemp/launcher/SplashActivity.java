/**
 * This code is part of "LEMP: Leaving Earth Mission Planner" android application.
 * Release under GPL v3
 * Written by Need2Revolt (francesco.davide.carnovale@gmail.com)
 */
package net.octopusstudios.carnospace.lemp.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import net.octopusstudios.carnospace.lemp.MissionsListerActivity;

/**
 * Created by Davide on 13/02/2017.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(5000); //TODO this is temporary, remove it
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, MissionsListerActivity.class);
        startActivity(intent);
        finish();
    }
}
