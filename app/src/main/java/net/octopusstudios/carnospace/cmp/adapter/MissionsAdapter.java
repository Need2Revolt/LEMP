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
import net.octopusstudios.carnospace.cmp.pojo.Mission;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davide on 12/02/2017.
 */

public class MissionsAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Resources res;
    private List<Mission> missions;

    public MissionsAdapter(Context context) {
        super();
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        missions = new ArrayList<Mission>(0);
        res = context.getResources();
    }

    public MissionsAdapter(Context context, List<Mission> missions) {
        super();
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.missions = missions;
        res = context.getResources();
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
        totalCostText.setText(res.getString(R.string.mission_cost_display, missions.get(i).getTotalCost()));

        TextView yearsText = (TextView) view.findViewById(R.id.detailYearsRequired);
        int years = missions.get(i).getTotalCost()/25;
        if(missions.get(i).getTotalCost()%25 != 0) {
            years++;
        }
        if(years == 1) {
            yearsText.setText(res.getString(R.string.mission_year_display, years));
        }
        else {
            yearsText.setText(res.getString(R.string.mission_years_display, years));
        }

        TextView dateText = (TextView) view.findViewById(R.id.missionDateText);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - hh:mm:ss z(Z)");
        String date = sdf.format(missions.get(i).getDate().getTime());
        dateText.setText(res.getString(R.string.mission_date_display, date));
    }
}
