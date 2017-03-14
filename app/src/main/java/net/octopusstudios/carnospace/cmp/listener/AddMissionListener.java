/**
 * This code is part of "LEMP: Leaving Earth Mission Planner" android application.
 * Released under GPL v3
 * Written by Need2Revolt (francesco.davide.carnovale@gmail.com)
 */
package net.octopusstudios.carnospace.cmp.listener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import net.octopusstudios.carnospace.cmp.R;
import net.octopusstudios.carnospace.cmp.adapter.MissionsAdapter;
import net.octopusstudios.carnospace.cmp.persistence.MissionsDatabaseHelper;
import net.octopusstudios.carnospace.cmp.pojo.Mission;

import java.util.List;

/**
 * Created by Davide on 16/02/2017.
 */

public class AddMissionListener implements View.OnClickListener {

    private final Context ctx;
    private List<Mission> missions;
    private MissionsAdapter missionsAdapter;

    public AddMissionListener(Context ctx, List<Mission> missions, MissionsAdapter missionsAdapter) {
        this.ctx = ctx;
        this.missions = missions;
        this.missionsAdapter = missionsAdapter;
    }

    @Override
    public void onClick(View view) {

        LayoutInflater li = LayoutInflater.from(ctx);
        View promptsView = li.inflate(R.layout.mission_input_dialog, null);
        final TextView stageNameEdit = (TextView) promptsView.findViewById(R.id.missionNameEdit);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                ctx);

        alertDialogBuilder.setView(promptsView);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String name = stageNameEdit.getText().toString();
                                Mission m = new Mission(name);
                                missions.add(m);
                                MissionsDatabaseHelper dbHelper = new MissionsDatabaseHelper(ctx);
                                dbHelper.saveMission(m);
                                missionsAdapter.notifyDataSetChanged();
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
}
