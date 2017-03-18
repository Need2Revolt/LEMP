/**
 * This code is part of "LEMP: Leaving Earth Mission Planner" android application.
 * Released under GPL v3
 * Written by Need2Revolt (francesco.davide.carnovale@gmail.com)
 */
package net.octopusstudios.carnospace.cmp.activity;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import net.octopusstudios.carnospace.cmp.R;
import net.octopusstudios.carnospace.cmp.adapter.StagesAdapter;
import net.octopusstudios.carnospace.cmp.listener.AddStageListener;
import net.octopusstudios.carnospace.cmp.pojo.Mission;
import net.octopusstudios.carnospace.cmp.status.SharedState;

/**
 * Created by Davide on 12/02/2017.
 */
public class MissionDetailsActivity extends AbstractMissionPlannerMenuAwareActivity {

    private StagesAdapter stagesAdapter;
    private Mission mission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedState sharedState = (SharedState) getApplicationContext();
        mission = sharedState.getSelectedMission();
        //mission.setMissionStages(sharedState.getDaoSession().getStageDao().loadAll());

        final Context ctx = this;

        ListView stagesList = (ListView) findViewById(R.id.missionsListView);
        stagesAdapter = new StagesAdapter(this, mission.getMissionStages());

        stagesList.setAdapter(stagesAdapter);
        stagesAdapter.notifyDataSetChanged();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        AddStageListener addStageListener = new AddStageListener(stagesAdapter, mission, ctx);
        fab.setOnClickListener(addStageListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_missions_lister, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //TODO same as other class i suppose
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
