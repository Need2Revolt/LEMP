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

    public Mission() {
    }

    public Mission(String name) {
        this.name = name;
        this.date = Calendar.getInstance();
        this.totalCost = 0;
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
}
