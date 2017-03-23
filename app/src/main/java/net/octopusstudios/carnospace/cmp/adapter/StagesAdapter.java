/**
 * This code is part of "LEMP: Leaving Earth Mission Planner" android application.
 * Released under GPL v3
 * Written by Need2Revolt (francesco.davide.carnovale@gmail.com)
 */
package net.octopusstudios.carnospace.cmp.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.octopusstudios.carnospace.cmp.R;
import net.octopusstudios.carnospace.cmp.pojo.Rocket;
import net.octopusstudios.carnospace.cmp.pojo.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davide on 12/02/2017.
 */

public class StagesAdapter extends BaseAdapter {

    private LayoutInflater inflater;

    private Resources res;

    private List<Stage> stages;

    public StagesAdapter(Context context) {
        super();
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        stages = new ArrayList<Stage>(0);
    }

    public StagesAdapter(Context context, List<Stage> stages) {
        super();
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        res = context.getResources();
        this.stages = stages;
    }

    @Override
    public int getCount() {
        return stages.size();
    }

    @Override
    public Object getItem(int i) {
        try {
            return stages.get(i);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = inflater.inflate(R.layout.stage_listview_item, null);
        }

        fillRowWithValues(i, view);


        return view;
    }

    private void fillRowWithValues(int i, View view) {
        Stage stage = stages.get(i);

        TextView stageNameText = (TextView) view.findViewById(R.id.stageName);
        stageNameText.setText(stage.getStageName());

        TextView difficultyText = (TextView) view.findViewById(R.id.difficulty);
        difficultyText.setText(res.getString(R.string.difficulty_display, stage.getDifficulty()));

        TextView payloadText = (TextView) view.findViewById(R.id.payloadMass);
        payloadText.setText(res.getString(R.string.payload_display, stage.getPayloadMass()));

        TextView rocketsListText = (TextView) view.findViewById(R.id.rocketsList);
        rocketsListText.setText(res.getString(R.string.rockets_list_display, stage.getRocketsList().toString()));

        TextView rocketMassText = (TextView) view.findViewById(R.id.rocketsMass);
        rocketMassText.setText(res.getString(R.string.rockets_mass_display, stage.getRocketsMass()));

        TextView leftoverPayloadText = (TextView) view.findViewById(R.id.leftoverPayload);
        double leftoverPayload = 0;
        double maxPayload = 0;
        int difficulty = stage.getDifficulty();
        for(String rocket : stage.getRocketsList()) {
            Rocket r = Rocket.valueOf(rocket.toUpperCase());
            maxPayload += r.getThrustPerDifficulty(difficulty);
        }
        leftoverPayload = maxPayload - stage.getPayloadMass();
        leftoverPayloadText.setText(res.getString(R.string.rockets_leftover_display, leftoverPayload));
    }
}
