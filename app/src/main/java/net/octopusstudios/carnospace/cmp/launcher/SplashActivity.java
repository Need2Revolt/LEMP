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
import net.octopusstudios.carnospace.cmp.pojo.DaoSession;
import net.octopusstudios.carnospace.cmp.status.SharedState;

/**
 * Created by Davide on 13/02/2017.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedState sharedState = (SharedState)getApplicationContext();
        DaoSession daoSession = sharedState.getDaoSession();
        sharedState.setMissions(daoSession.getMissionDao().loadAll());

        Intent intent = new Intent(this, MissionsListerActivity.class);
        startActivity(intent);
        finish();
    }
}
