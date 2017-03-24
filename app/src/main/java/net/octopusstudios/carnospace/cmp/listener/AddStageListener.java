package net.octopusstudios.carnospace.cmp.listener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
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

    public static final String SEPARATOR = ",";
    private StagesAdapter stagesAdapter;
    private Mission mission;
    private Context ctx;
    private DaoSession daoSession;
    private ListView parentView;
    private Resources res;

    public AddStageListener(StagesAdapter stagesAdapter, Mission mission, Context ctx, ListView parentView) {
        this.mission = mission;
        this.ctx = ctx;
        this.stagesAdapter = stagesAdapter;
        this.parentView = parentView;
        daoSession = ((SharedState)ctx.getApplicationContext()).getDaoSession();
        res = ctx.getResources();
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
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //checks on difficulty
                                CharSequence diffString = maneuverDifficultyPicker.getText();
                                Integer[] diffInts;
                                String[] diffStringArray;
                                int steps = 0;
                                if(TextUtils.isEmpty(diffString)) {
                                    openSnackbar(ctx.getString(R.string.difficulty_empty));
                                    return;
                                }

                                if(diffString.toString().contains(SEPARATOR)) {
                                    diffStringArray = diffString.toString().split(SEPARATOR);
                                    for(int i = 0; i < diffStringArray.length; i++) {
                                        diffStringArray[i] = diffStringArray[i].trim();
                                    }
                                    steps = diffStringArray.length;
                                }
                                else {
                                    steps = 1;
                                    diffStringArray = new String[1];
                                    diffStringArray[0] = diffString.toString();
                                }

                                diffInts = new Integer[steps];
                                for(int i = 0; i < steps; i++) {
                                    Integer diffInt;
                                    try {
                                        diffInt = Integer.parseInt(diffStringArray[i]);
                                        diffInts[i] = diffInt;
                                    } catch (NumberFormatException e) {
                                        openSnackbar(ctx.getString(R.string.difficulty_not_number));
                                        return;
                                    }
                                    if (diffInt < 1) {
                                        openSnackbar(ctx.getString(R.string.diff_lesser_1));
                                        return;
                                    }
                                    if (diffInt > 9) {
                                        openSnackbar(ctx.getString(R.string.diff_bigger_9));
                                        return;
                                    }
                                }

                                //checks on payload
                                CharSequence payloadString = payloadMassPicker.getText();
                                Integer payloadInt;
                                if(TextUtils.isEmpty(payloadString)) {
                                    openSnackbar(ctx.getString(R.string.payload_empty));
                                    return;
                                }
                                try{
                                    payloadInt = Integer.parseInt(payloadString.toString());
                                }
                                catch (NumberFormatException e) {
                                    openSnackbar(ctx.getString(R.string.payload_not_number));
                                    return;
                                }

                                CharSequence stageNameUser = stageNameEdit.getText();
                                //if everything is alright, build the stage(s)
                                if(steps == 1) {
                                    buildNewStage(stageNameUser.toString(), diffInts[0], payloadInt);
                                }
                                else {
                                    for (int i = 0; i < steps; i++) {
                                        String stageName = res.getString(R.string.intermediate_auto_stage, i);
                                        if(i == 0) {
                                            if(TextUtils.isEmpty(stageNameUser)) {
                                                stageName = ctx.getString(R.string.first_auto_stage);
                                            }
                                            else {
                                                stageName = stageNameUser.toString();
                                            }
                                        }
                                        if(i == steps - 1) {
                                            stageName = ctx.getString(R.string.last_auto_stage);
                                        }

                                        Stage currentStage = buildNewStage(stageName, diffInts[i], payloadInt);
                                        payloadInt += currentStage.getRocketsMass();
                                    }
                                }
                            }
                        })
                .setNegativeButton(R.string.cancel,
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

    private void openSnackbar(String cause) {
        String message = res.getString(R.string.cowardly_refuse_stage, cause);
        Snackbar snackbar = Snackbar.make(parentView, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private Stage buildNewStage(String stageName, Integer difficulty, Integer payload) {
        Stage s = new Stage(stageName, difficulty, payload);
        s.setMissionId(mission.getId());
        mission.addStageCost(s.getTotalCost());
        mission.getMissionStages().add(s);
        daoSession.insertOrReplace(s);
        daoSession.insertOrReplace(mission);
        stagesAdapter.notifyDataSetChanged();

        return s;
    }
}
