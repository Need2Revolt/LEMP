package net.octopusstudios.carnospace.lemp.status;

import android.app.Application;

import net.octopusstudios.carnospace.lemp.pojo.Mission;

import java.util.List;

/**
 * Created by Davide on 12/03/2017.
 */

public class SharedState extends Application {
    private List<Mission> missions;
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
