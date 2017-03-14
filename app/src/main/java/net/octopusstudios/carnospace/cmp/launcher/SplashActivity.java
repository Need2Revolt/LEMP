/**
 * This code is part of "LEMP: Leaving Earth Mission Planner" android application.
 * Released under GPL v3
 * Written by Need2Revolt (francesco.davide.carnovale@gmail.com)
 */
package net.octopusstudios.carnospace.cmp.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import net.octopusstudios.carnospace.cmp.activity.MissionsListerActivity;
import net.octopusstudios.carnospace.cmp.persistence.MissionsDatabaseHelper;
import net.octopusstudios.carnospace.cmp.status.SharedState;

/**
 * Created by Davide on 13/02/2017.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MissionsDatabaseHelper dbHelper = new MissionsDatabaseHelper(this);
        SharedState sharedState = (SharedState) getApplicationContext();
        sharedState.setMissions(dbHelper.getMissionsList());
        /**
        try {
            Thread.sleep(2000); //TODO this is temporary, remove it
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
         */
        Intent intent = new Intent(this, MissionsListerActivity.class);
        startActivity(intent);
        finish();
    }
}
