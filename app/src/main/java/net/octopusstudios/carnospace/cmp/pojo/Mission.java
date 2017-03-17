/**
 * This code is part of "LEMP: Leaving Earth Mission Planner" android application.
 * Released under GPL v3
 * Written by Need2Revolt (francesco.davide.carnovale@gmail.com)
 */
package net.octopusstudios.carnospace.cmp.pojo;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.greenrobot.greendao.DaoException;


/**
 * Created by Davide on 12/02/2017.
 */

@Entity
public class Mission implements Serializable {
    static final long serialVersionUID = 4639812938L;

    @Id private Long id;
    private String name;
    private int totalCost;
    private Date date;
    @ToMany(referencedJoinProperty = "missionId")
    private List<Stage> missionStages;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1935729854)
    private transient MissionDao myDao;

    public Mission() {
        this.date = Calendar.getInstance().getTime();
        this.totalCost = 0;
        missionStages = new ArrayList<>();
    }

    public Mission(String name) {
        this();
        this.name = name;
    }

    @Generated(hash = 1977290769)
    public Mission(Long id, String name, int totalCost, Date date) {
        this.id = id;
        this.name = name;
        this.totalCost = totalCost;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 411734941)
    public List<Stage> getMissionStages() {
        if (missionStages == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            StageDao targetDao = daoSession.getStageDao();
            List<Stage> missionStagesNew = targetDao
                    ._queryMission_MissionStages(id);
            synchronized (this) {
                if (missionStages == null) {
                    missionStages = missionStagesNew;
                }
            }
        }
        return missionStages;
    }

    public void setMissionStages(List<Stage> missionStages) {
        this.missionStages = missionStages;
    }

    public void addStageCost(int stageCose) {
        totalCost += stageCose;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 302311782)
    public synchronized void resetMissionStages() {
        missionStages = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 829174646)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMissionDao() : null;
    }
}
