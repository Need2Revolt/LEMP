/**
 * This code is part of "LEMP: Leaving Earth Mission Planner" android application.
 * Released under GPL v3
 * Written by Need2Revolt (francesco.davide.carnovale@gmail.com)
 */
package net.octopusstudios.carnospace.cmp.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import net.octopusstudios.carnospace.cmp.R;
import net.octopusstudios.carnospace.cmp.adapter.MissionsAdapter;
import net.octopusstudios.carnospace.cmp.listener.AddMissionListener;
import net.octopusstudios.carnospace.cmp.pojo.DaoSession;
import net.octopusstudios.carnospace.cmp.pojo.Mission;
import net.octopusstudios.carnospace.cmp.pojo.Stage;
import net.octopusstudios.carnospace.cmp.status.SharedState;

import java.util.List;

/**
 * Created by Davide on 12/02/2017.
 */
public class MissionsListerActivity extends AbstractMissionPlannerMenuAwareActivity {

    private MissionsAdapter missionsAdapter;
    private DaoSession daoSession;
    private List<Mission> missions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setup main UI
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missions_lister);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        //setup internal objects
        final SharedState sharedState = (SharedState) getApplicationContext();
        missions = sharedState.getMissions();
        daoSession = sharedState.getDaoSession();

        ListView missionsListView = (ListView) findViewById(R.id.missionsListView);
        missionsAdapter = new MissionsAdapter(this, missions);
        missionsListView.setAdapter(missionsAdapter);

        registerForContextMenu(missionsListView);

        final Context ctx = this;
        fab.setOnClickListener(new AddMissionListener(ctx, missions, missionsAdapter));

        missionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ctx, MissionDetailsActivity.class);
                sharedState.setSelectedMissionId(position);
                startActivity(intent);
            }
        });

        missionsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.missionsListView) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.missions_list_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {

            case R.id.deleteMission:
                Mission toDelete = missions.remove(info.position);
                for(Stage s : toDelete.getMissionStages()) {
                    daoSession.delete(s);
                }
                daoSession.delete(toDelete);
                missionsAdapter.notifyDataSetChanged();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        missionsAdapter.notifyDataSetChanged();
        super.onResume();
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
