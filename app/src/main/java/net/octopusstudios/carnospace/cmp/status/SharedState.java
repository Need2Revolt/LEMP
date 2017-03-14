package net.octopusstudios.carnospace.cmp.status;

import android.app.Application;

import net.octopusstudios.carnospace.cmp.pojo.Mission;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davide on 12/03/2017.
 */

public class SharedState extends Application {
    private List<Mission> missions = new ArrayList<>(5);
    private int selectedMissionId;

    public void setMissions(List<Mission> missions) {
        this.missions = missions;
    }

    public List<Mission> getMissions() {
        return missions;
    }

    public void setSelectedMissionId(int selectedMissionId) {
        this.selectedMissionId = selectedMissionId;
    }

    public int getSelectedMissionId() {
        return selectedMissionId;
    }

    public Mission getSelectedMission() {
        return missions.get(selectedMissionId);
    }
}
