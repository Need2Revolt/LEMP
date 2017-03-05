/**
 * This code is part of "LEMP: Leaving Earth Mission Planner" android application.
 * Released under GPL v3
 * Written by Need2Revolt (francesco.davide.carnovale@gmail.com)
 */
package net.octopusstudios.carnospace.lemp.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Davide on 12/02/2017.
 */

public class Stage {

    public static final int[] massList = {1, 4, 9, 20};
    public static final String[] rocketNamesList = {"Juno", "Atlas", "Soyuz", "Saturn"};
    public static final Map<Integer, List<Double>> difficultiesMap = new HashMap<>();

    static {
        List<Double> diff1 = new ArrayList<>(4);
        diff1.add(3d);
        diff1.add(23d);
        diff1.add(71d);
        diff1.add(180d);
        difficultiesMap.put(1, diff1);

        List<Double> diff2 = new ArrayList<>(4);
        diff2.add(1d);
        diff2.add(9.5d);
        diff2.add(31d);
        diff2.add(80d);
        difficultiesMap.put(2, diff2);

        List<Double> diff3 = new ArrayList<>(4);
        diff3.add(0.33d);
        diff3.add(5d);
        diff3.add(17.66d);
        diff3.add(46.66d);
        difficultiesMap.put(3, diff3);

        List<Double> diff4 = new ArrayList<>(4);
        diff4.add(0d);
        diff4.add(2.75d);
        diff4.add(11d);
        diff4.add(30d);
        difficultiesMap.put(4, diff4);

        List<Double> diff5 = new ArrayList<>(4);
        diff5.add(0d);
        diff5.add(1.4d);
        diff5.add(7d);
        diff5.add(20d);
        difficultiesMap.put(5, diff5);

        List<Double> diff6 = new ArrayList<>(4);
        diff6.add(0d);
        diff6.add(0.5d);
        diff6.add(4.33d);
        diff6.add(13.33d);
        difficultiesMap.put(6, diff6);

        List<Double> diff7 = new ArrayList<>(4);
        diff7.add(0d);
        diff7.add(0d);
        diff7.add(2.29d);
        diff7.add(8.57d);
        difficultiesMap.put(7, diff7);

        List<Double> diff8 = new ArrayList<>(4);
        diff8.add(0d);
        diff8.add(0d);
        diff8.add(1d);
        diff8.add(5d);
        difficultiesMap.put(8, diff8);

        List<Double> diff9 = new ArrayList<>(4);
        diff9.add(0d);
        diff9.add(0d);
        diff9.add(1d);
        diff9.add(5d);
        difficultiesMap.put(9, diff9);
    }

    private String stageName;
    private int difficulty;
    private int payloadMass;
    private List<String> rocktesList;
    private int rocketsMass;

    public Stage() {
        difficulty = 0;
        payloadMass = 0;
        rocketsMass = 0;
        rocktesList = new ArrayList<>(2);
    }

    public Stage(String stageName, int difficulty, int payloadMass, List<String> rocktesList, int rocketsMass) {
        this.stageName = stageName;
        this.difficulty = difficulty;
        this.payloadMass = payloadMass;
        this.rocktesList = rocktesList;
        this.rocketsMass = rocketsMass;
    }

    public Stage(String stageName, int difficulty, int payloadMass) {
        this.stageName = stageName;
        this.difficulty = difficulty;
        this.payloadMass = payloadMass;
        calculateRequiredRockets();
    }

    private void calculateRequiredRockets() {
        //find the relevant table row using the difficulty
        List<Double> difficultyList = difficultiesMap.get(difficulty);

        //scan the row until payload > rocket trust
        int mass;
        List<String> rockets = new ArrayList<>(2);
        int i = 0;
        while(payloadMass > difficultyList.get(i)) { //TODO what if i goes bigger than 3?
            i++;
        }

        //save the rocket as candidate and note it's mass
        mass = massList[i];
        rockets.add(rocketNamesList[i]);

        //scan lighter rockets
        double payloadMassCopy = payloadMass;
        int newMassCandidate = Integer.MAX_VALUE;
        List<String> newRocketsCandidate = new ArrayList<>(4);
        while(--i > 0 && payloadMassCopy > 0) {
            //payload/prior trust * mass) + (payload%prior.trust)/prior.prior.trust * mass) + etc...
            int times = (int)(payloadMassCopy/difficultyList.get(i));
            newMassCandidate += times * massList[i];
            for(int j = 0; j < times; j++) {
                newRocketsCandidate.add(rocketNamesList[i]);
            }
            payloadMassCopy = payloadMassCopy%difficultyList.get(i);
        }

        //if total mass < previous total mass then new minimum candidate.
        if(newMassCandidate < mass) {
            mass = newMassCandidate;
            rockets = newRocketsCandidate;
        }

        //set calculated values
        rocketsMass = mass;
        rocktesList = rockets;
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
