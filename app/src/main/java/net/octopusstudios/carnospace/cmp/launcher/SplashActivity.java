/**
 * This code is part of "LEMP: Leaving Earth Mission Planner" android application.
 * Released under GPL v3
 * Written by Need2Revolt (francesco.davide.carnovale@gmail.com)
 */
package net.octopusstudios.carnospace.cmp.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.AppCompatActivity;

import net.octopusstudios.carnospace.cmp.activity.MissionsListerActivity;
import net.octopusstudios.carnospace.cmp.pojo.DaoSession;
import net.octopusstudios.carnospace.cmp.pojo.Mission;
import net.octopusstudios.carnospace.cmp.status.SharedState;

import java.util.Collections;
import java.util.List;

/**
 * Created by Davide on 13/02/2017.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedState sharedState = (SharedState)getApplicationContext();
        DaoSession daoSession = sharedState.getDaoSession();
        List<Mission> missions = daoSession.getMissionDao().loadAll();
        //reverse missions so most recent is on top
        Collections.reverse(missions);
        sharedState.setMissions(missions);

        Intent intent = new Intent(this, MissionsListerActivity.class);
        startActivity(intent);
        finish();
    }
}
