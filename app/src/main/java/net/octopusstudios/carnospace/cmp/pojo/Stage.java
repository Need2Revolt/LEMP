/**
 * This code is part of "LEMP: Leaving Earth Mission Planner" android application.
 * Released under GPL v3
 * Written by Need2Revolt (francesco.davide.carnovale@gmail.com)
 */
package net.octopusstudios.carnospace.cmp.pojo;

import net.octopusstudios.carnospace.cmp.greendao.converter.StringListConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by Davide on 12/02/2017.
 */
@Entity
public class Stage implements Serializable {

    static final long serialVersionUID = 4639812939L;

    @Transient
    public static final Rocket[] rocketList = {Rocket.JUNO, Rocket.ATLAS, Rocket.SOYUZ, Rocket.SATURN};

    @Id private Long id;
    private Long missionId;
    private String stageName;
    private int difficulty;
    private int payloadMass;
    @Convert(converter = StringListConverter.class, columnType = String.class)
    private List<String> rocketsList;
    private int rocketsMass;
    private int totalCost;
    @ToOne(joinProperty = "missionId")
    private Mission mission;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1008513004)
    private transient StageDao myDao;

    @Generated(hash = 1680779066)
    private transient Long mission__resolvedKey;

    public Stage() {
        difficulty = 0;
        payloadMass = 0;
        rocketsMass = 0;
        rocketsList = new ArrayList<>(2);
    }

    public Stage(String stageName, int difficulty, int payloadMass, List<String> rocketsList, int rocketsMass) {
        this.stageName = stageName;
        this.difficulty = difficulty;
        this.payloadMass = payloadMass;
        this.rocketsList = rocketsList;
        this.rocketsMass = rocketsMass;
    }

    public Stage(String stageName, int difficulty, int payloadMass) {
        this.stageName = stageName;
        this.difficulty = difficulty;
        this.payloadMass = payloadMass;
        calculateRequiredRockets();
    }

    @Generated(hash = 493360797)
    public Stage(Long id, Long missionId, String stageName, int difficulty, int payloadMass, List<String> rocketsList,
            int rocketsMass, int totalCost) {
        this.id = id;
        this.missionId = missionId;
        this.stageName = stageName;
        this.difficulty = difficulty;
        this.payloadMass = payloadMass;
        this.rocketsList = rocketsList;
        this.rocketsMass = rocketsMass;
        this.totalCost = totalCost;
    }

    private void calculateRequiredRockets() {
        //find the relevant table row using the difficulty
        //scan the row until payload > rocket trust
        float mass = 0;
        int stageCost = 0;
        List<String> rockets = new ArrayList<>(2);
        double payloadMassCopy = payloadMass;
        int i = 0;
        float basicMass = 0;
        int basicCost = 0;
        List<String> basicRockets = new ArrayList<>(2);
        while(payloadMassCopy > rocketList[i].getThrustPerDifficulty(difficulty)) {
            if(i < 3) {
                i++;
            }
            else {
                basicMass += rocketList[i].getMass();
                basicCost += rocketList[i].getCost();
                basicRockets.add(rocketList[i].getName());
                payloadMassCopy -= rocketList[i].getThrustPerDifficulty(difficulty);
                i=0;
            }
        }

        //save the rocket as candidate and note it's mass
        mass = basicMass + rocketList[i].getMass();
        stageCost = basicCost + rocketList[i].getCost();
        rockets.addAll(basicRockets);
        rockets.add(rocketList[i].getName());

        //scan lighter rockets
        float newMassCandidate = basicMass;
        int newCostCandidate = basicCost;
        List<String> newRocketsCandidate = new ArrayList<>(4);
        while(--i >= 0 && payloadMassCopy > 0 && rocketList[i].getThrustPerDifficulty(difficulty) > 0) {
            //payload/prior trust * mass) + (payload%prior.trust)/prior.prior.trust * mass) + etc...
            int times = (int)(payloadMassCopy/rocketList[i].getThrustPerDifficulty(difficulty));
            if(payloadMassCopy < rocketList[i].getThrustPerDifficulty(difficulty)) {
                times = 1;
            }
            int stageMass = times * rocketList[i].getMass();
            //check if there will be a very small leftover payload and lighter rockets can't lift it
            //in case, add one of the current rockets
            double provisionalPayloadMassCopy = payloadMassCopy;
            if(stageMass >= rocketList[i+1].getMass()) {
                provisionalPayloadMassCopy -= rocketList[i+1].getThrustPerDifficulty(difficulty);
            }
            else {
                provisionalPayloadMassCopy -= times * rocketList[i].getThrustPerDifficulty(difficulty);
            }

            if(provisionalPayloadMassCopy > 0 && i > 0 && rocketList[i-1].getThrustPerDifficulty(difficulty) <= 0) {
                stageMass += rocketList[i].getMass();
                times++;
            }
            //if the total mass of the smaller rockets is bigger than the next heavier rocket,
            //better use the heavier then...
            if(stageMass >= rocketList[i+1].getMass()) {
                newMassCandidate += rocketList[i+1].getMass();
                newCostCandidate += rocketList[i+1].getCost();
                newRocketsCandidate.add(rocketList[i+1].getName());
                payloadMassCopy -= rocketList[i+1].getThrustPerDifficulty(difficulty);
            }
            //otherwise the smaller ones are the best choice
            else {
                newMassCandidate += times * rocketList[i].getMass();
                newCostCandidate += times * rocketList[i].getCost();
                for (int j = 0; j < times; j++) {
                    newRocketsCandidate.add(rocketList[i].getName());
                }
                payloadMassCopy -= times * rocketList[i].getThrustPerDifficulty(difficulty);
            }
        }

        //if total mass < previous total mass then new minimum candidate.
        if(newMassCandidate != 0 && newMassCandidate < mass && payloadMassCopy < 1) { // < 1 is for decimal rounding
            mass = newMassCandidate;
            rockets = newRocketsCandidate;
            rockets.addAll(basicRockets);
            stageCost = newCostCandidate;
        }

        //set calculated values
        rocketsMass = (int)mass;
        rocketsList = rockets;
        totalCost = stageCost;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getPayloadMass() {
        return payloadMass;
    }

    public void setPayloadMass(int payloadMass) {
        this.payloadMass = payloadMass;
    }

    public List<String> getRocketsList() {
        return rocketsList;
    }

    public void setRocketsList(List<String> rocketsList) {
        this.rocketsList = rocketsList;
    }

    public int getRocketsMass() {
        return rocketsMass;
    }

    public void setRocketsMass(int rocketsMass) {
        this.rocketsMass = rocketsMass;
    }

    public int getTotalMass() {
        return rocketsMass + payloadMass;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMissionId() {
        return this.missionId;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1058945532)
    public Mission getMission() {
        Long __key = this.missionId;
        if (mission__resolvedKey == null || !mission__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MissionDao targetDao = daoSession.getMissionDao();
            Mission missionNew = targetDao.load(__key);
            synchronized (this) {
                mission = missionNew;
                mission__resolvedKey = __key;
            }
        }
        return mission;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1182541489)
    public void setMission(Mission mission) {
        synchronized (this) {
            this.mission = mission;
            missionId = mission == null ? null : mission.getId();
            mission__resolvedKey = missionId;
        }
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
    @Generated(hash = 1015172054)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getStageDao() : null;
    }
}
