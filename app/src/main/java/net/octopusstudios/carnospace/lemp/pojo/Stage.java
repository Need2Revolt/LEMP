package net.octopusstudios.carnospace.lemp.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davide on 12/02/2017.
 */

public class Stage {
    private String stageName;
    private int difficulty;
    private int payloadMass;
    private List<String> rocktesList;
    private int rocketsMass;

    public Stage() {
        difficulty = 0;
        payloadMass = 0;
        rocketsMass = 0;
        rocktesList = new ArrayList<String>(2);
    }

    public Stage(String stageName, int difficulty, int payloadMass, List<String> rocktesList, int rocketsMass) {
        this.stageName = stageName;
        this.difficulty = difficulty;
        this.payloadMass = payloadMass;
        this.rocktesList = rocktesList;
        this.rocketsMass = rocketsMass;
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

    public List<String> getRocktesList() {
        return rocktesList;
    }

    public void setRocktesList(List<String> rocktesList) {
        this.rocktesList = rocktesList;
    }

    public int getRocketsMass() {
        return rocketsMass;
    }

    public void setRocketsMass(int rocketsMass) {
        this.rocketsMass = rocketsMass;
    }
}
