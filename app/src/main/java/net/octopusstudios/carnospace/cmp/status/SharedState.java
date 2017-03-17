package net.octopusstudios.carnospace.cmp.status;

import android.app.Application;

import net.octopusstudios.carnospace.cmp.pojo.DaoMaster;
import net.octopusstudios.carnospace.cmp.pojo.DaoSession;
import net.octopusstudios.carnospace.cmp.pojo.Mission;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davide on 12/03/2017.
 */

public class SharedState extends Application {
    private List<Mission> missions = new ArrayList<>(5);
    private int selectedMissionId;
    private DaoSession daoSession;

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

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "carnospace-missions-db");
        Database db =  helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
