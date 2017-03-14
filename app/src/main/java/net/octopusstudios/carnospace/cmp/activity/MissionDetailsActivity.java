/**
 * This code is part of "LEMP: Leaving Earth Mission Planner" android application.
 * Released under GPL v3
 * Written by Need2Revolt (francesco.davide.carnovale@gmail.com)
 */
package net.octopusstudios.carnospace.cmp.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import net.octopusstudios.carnospace.cmp.R;
import net.octopusstudios.carnospace.cmp.adapter.StagesAdapter;
import net.octopusstudios.carnospace.cmp.pojo.Mission;
import net.octopusstudios.carnospace.cmp.pojo.Stage;
import net.octopusstudios.carnospace.cmp.status.SharedState;

/**
 * Created by Davide on 12/02/2017.
 */
public class MissionDetailsActivity extends AppCompatActivity {

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

        final Context ctx = this;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater li = LayoutInflater.from(ctx);
                View stageInputView = li.inflate(R.layout.payload_input_dialog, null);
                final TextView stageNameEdit = (TextView) stageInputView.findViewById(R.id.payloadNameText);
                final NumberPicker maneuverDifficultyPicker = (NumberPicker)stageInputView.findViewById(R.id.maneuverDifficultyPicker);
                maneuverDifficultyPicker.setMinValue(0);
                maneuverDifficultyPicker.setMaxValue(9); //TODO put this in a constant or something
                final NumberPicker payloadMassPicker = (NumberPicker)stageInputView.findViewById(R.id.payloadMassPicker);
                payloadMassPicker.setMinValue(0);
                payloadMassPicker.setMaxValue(20); //TODO put this in a constant or something
                //auto fill payload with previous stage total mass
                if(mission.getMissionStages().size() > 0)
                {
                    Stage previousStage = mission.getMissionStages().get(mission.getMissionStages().size() - 1);
                    payloadMassPicker.setValue(previousStage.getTotalMass());
                }

                AlertDialog.Builder inputDialogBuilder = new AlertDialog.Builder(ctx);

                // set payload_input_dialog.xml to input dialog builder
                inputDialogBuilder.setView(stageInputView);

                // set dialog message
                inputDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        buildNewStage(stageNameEdit.getText().toString(),
                                                maneuverDifficultyPicker.getValue(),
                                                payloadMassPicker.getValue());
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create input dialog
                AlertDialog inputDialog = inputDialogBuilder.create();

                // show it
                inputDialog.show();
            }
        });

        ListView stagesList = (ListView) findViewById(R.id.missionsListView);
        stagesAdapter = new StagesAdapter(this, mission.getMissionStages());

        stagesList.setAdapter(stagesAdapter);
        stagesAdapter.notifyDataSetChanged();
    }

    private void buildNewStage(String stageName, int difficulty, int payload) {
        Stage s = new Stage(stageName, difficulty, payload);
        mission.addStageCost(s.getTotalCost());
        mission.getMissionStages().add(s);
        stagesAdapter.notifyDataSetChanged();
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
