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
import net.octopusstudios.carnospace.lemp.pojo.Mission;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davide on 12/02/2017.
 */

public class MissionsAdapter extends BaseAdapter {

    private LayoutInflater inflater;

    private List<Mission> missions;

    public MissionsAdapter(Context context) {
        super();
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        missions = new ArrayList<Mission>(0);
    }

    public MissionsAdapter(Context context, List<Mission> missions) {
        super();
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.missions = missions;
    }

    @Override
    public int getCount() {
        return missions.size();
    }

    @Override
    public Object getItem(int i) {
        try {
            return missions.get(i);
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
            view = inflater.inflate(R.layout.mission_listview_item, null);
        }

        fillRowWithValues(i, view);

        return view;
    }

    private void fillRowWithValues(int i, View view) {
        TextView missionNameText = (TextView) view.findViewById(R.id.missionNameText);
        missionNameText.setText(missions.get(i).getName());

        TextView totalCostText = (TextView) view.findViewById(R.id.detailTotalCostText);
        totalCostText.setText("Total Cost " + missions.get(i).getTotalCost());

        TextView dateText = (TextView) view.findViewById(R.id.missionDateText);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - hh:mm:ss z(Z)");
        //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - hh:mm:ss");
        String date = sdf.format(missions.get(i).getDate().getTime());
        dateText.setText(date);
    }
}
