package net.octopusstudios.carnospace.cmp.listener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import net.octopusstudios.carnospace.cmp.R;
import net.octopusstudios.carnospace.cmp.adapter.StagesAdapter;
import net.octopusstudios.carnospace.cmp.pojo.DaoSession;
import net.octopusstudios.carnospace.cmp.pojo.Mission;
import net.octopusstudios.carnospace.cmp.pojo.Stage;
import net.octopusstudios.carnospace.cmp.status.SharedState;

/**
 * Created by Davide on 17/03/2017.
 */

public class AddStageListener  implements View.OnClickListener {

    private StagesAdapter stagesAdapter;
    private Mission mission;
    private Context ctx;
    private DaoSession daoSession;

    public AddStageListener(StagesAdapter stagesAdapter, Mission mission, Context ctx) {
        this.mission = mission;
        this.ctx = ctx;
        this.stagesAdapter = stagesAdapter;
        daoSession = ((SharedState)ctx.getApplicationContext()).getDaoSession();
    }

    @Override
    public void onClick(View view) {

        LayoutInflater li = LayoutInflater.from(ctx);
        View stageInputView = li.inflate(R.layout.payload_input_dialog, null);
        final TextView stageNameEdit = (TextView) stageInputView.findViewById(R.id.payloadNameText);
        final TextView maneuverDifficultyPicker = (TextView)stageInputView.findViewById(R.id.difficultyNumber);
        final TextView payloadMassPicker = (TextView)stageInputView.findViewById(R.id.payloadNumber);

        //auto fill payload with previous stage total mass
        if(mission.getMissionStages().size() > 0)
        {
            Stage previousStage = mission.getMissionStages().get(mission.getMissionStages().size() - 1);
            payloadMassPicker.setText(String.valueOf(previousStage.getTotalMass()));
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
                                        maneuverDifficultyPicker.getText().toString(),
                                        payloadMassPicker.getText().toString());
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

    private void buildNewStage(String stageName, String difficulty, String payload) {
        int difficultyInt = Integer.parseInt(difficulty);
        int payloadInt = Integer.parseInt(payload);
        Stage s = new Stage(stageName, difficultyInt, payloadInt);
        s.setMissionId(mission.getId());
        mission.addStageCost(s.getTotalCost());
        mission.getMissionStages().add(s);
        daoSession.insertOrReplace(s);
        daoSession.insertOrReplace(mission);
        stagesAdapter.notifyDataSetChanged();
    }
}
