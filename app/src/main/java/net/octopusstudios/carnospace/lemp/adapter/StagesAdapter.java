/**
 * This code is part of "LEMP: Leaving Earth Mission Planner" android application.
 * Released under GPL v3
 * Written by Need2Revolt (francesco.davide.carnovale@gmail.com)
 */
package net.octopusstudios.carnospace.lemp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.octopusstudios.carnospace.lemp.R;
import net.octopusstudios.carnospace.lemp.pojo.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davide on 12/02/2017.
 */

public class StagesAdapter extends BaseAdapter {

    private LayoutInflater inflater;

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
        TextView stageNameText = (TextView) view.findViewById(R.id.stageName);
        stageNameText.setText(stages.get(i).getStageName());

        TextView difficultyText = (TextView) view.findViewById(R.id.difficulty);
        difficultyText.setText(String.valueOf(stages.get(i).getDifficulty()));

        TextView payloadText = (TextView) view.findViewById(R.id.payloadMass);
        payloadText.setText(String.valueOf(stages.get(i).getPayloadMass()));

        TextView rocketsListText = (TextView) view.findViewById(R.id.rocketsList);
        rocketsListText.setText(stages.get(i).getRocktesList().toString());

        TextView rocketMassText = (TextView) view.findViewById(R.id.rocketsMass);
        rocketMassText.setText(String.valueOf(stages.get(i).getRocketsMass()));
    }
}
