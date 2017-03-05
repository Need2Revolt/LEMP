/**
 * This code is part of "LEMP: Leaving Earth Mission Planner" android application.
 * Released under GPL v3
 * Written by Need2Revolt (francesco.davide.carnovale@gmail.com)
 */
package net.octopusstudios.carnospace.lemp.activity;

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
import android.widget.TextView;

import net.octopusstudios.carnospace.lemp.R;
import net.octopusstudios.carnospace.lemp.adapter.StagesAdapter;
import net.octopusstudios.carnospace.lemp.pojo.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davide on 12/02/2017.
 */
public class MissionDetailsActivity extends AppCompatActivity {

    private StagesAdapter stagesAdapter;

    private List<Stage> stages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missions_lister);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        stages = new ArrayList<Stage>(0);


        final Context ctx = this;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater li = LayoutInflater.from(ctx);
                View promptsView = li.inflate(R.layout.mission_input_dialog, null);
                final TextView stageNameEdit = (TextView) promptsView.findViewById(R.id.missionNameEdit);
                //final NumberPicker difficultyPicker = (NumberPicker)promptsView.findViewById(R.id.difficultyPicker);
                //final NumberPicker payloadPicker = (NumberPicker)promptsView.findViewById(R.id.payloadPicker);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        ctx);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Stage s = new Stage();
                                        s.setStageName(stageNameEdit.getText().toString());
                                        //s.setDifficulty(difficultyPicker.getValue());
                                        //s.setPayloadMass(payloadPicker.getValue());
                                        stages.add(s);
                                        stagesAdapter.notifyDataSetChanged();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });

        //my code starts!!!! yeah!!!
        ListView stagesList = (ListView) findViewById(R.id.missionsListView);
        stagesAdapter = new StagesAdapter(this, stages);

        stagesList.setAdapter(stagesAdapter);

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
