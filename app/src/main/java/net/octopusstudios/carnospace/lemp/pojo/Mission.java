/**
 * This code is part of "LEMP: Leaving Earth Mission Planner" android application.
 * Released under GPL v3
 * Written by Need2Revolt (francesco.davide.carnovale@gmail.com)
 */
package net.octopusstudios.carnospace.lemp.pojo;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by Davide on 12/02/2017.
 */

public class Mission {
    private String name;
    private int totalCost;
    private Calendar date;
    private List<Stage> missionStages;

    public Mission() {
    }

    public Mission(String name) {
        this.name = name;
        this.date = Calendar.getInstance();
        this.totalCost = 0;
        missionStages = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public List<Stage> getMissionStages() {
        return missionStages;
    }

    public void setMissionStages(List<Stage> missionStages) {
        this.missionStages = missionStages;
    }
}
