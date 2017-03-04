package net.octopusstudios.carnospace.lemp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import net.octopusstudios.carnospace.lemp.adapters.MissionsAdapter;
import net.octopusstudios.carnospace.lemp.pojo.Mission;

import java.util.ArrayList;
import java.util.List;

public class MissionsListerActivity extends AppCompatActivity {

    private MissionsAdapter missionsAdapter;

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
        missions = new ArrayList<Mission>(0);

        ListView missionsList = (ListView) findViewById(R.id.missionsListView);
        missionsAdapter = new MissionsAdapter(this, missions);
        missionsList.setAdapter(missionsAdapter);

        final Context ctx = this;
        //TODO restore this
        //fab.setOnClickListener(new AddMissionListener(ctx, missions, missionsAdapter));

        //experimental
        missionsList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //TODO restore this
                //Intent intent = new Intent(ctx, MissionDetails.class);
                //startActivity(intent);
            }
        });
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

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
