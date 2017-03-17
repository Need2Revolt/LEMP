package net.octopusstudios.carnospace.cmp.listener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
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

    private void buildNewStage(String stageName, int difficulty, int payload) {
        Stage s = new Stage(stageName, difficulty, payload);
        s.setMissionId(mission.getId());
        mission.addStageCost(s.getTotalCost());
        mission.getMissionStages().add(s);
        daoSession.insertOrReplace(s);
        daoSession.insertOrReplace(mission);
        stagesAdapter.notifyDataSetChanged();
    }
}
