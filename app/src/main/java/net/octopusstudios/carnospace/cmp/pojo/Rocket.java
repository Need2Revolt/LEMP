package net.octopusstudios.carnospace.cmp.pojo;

/**
 * Created by Davide on 23/03/2017.
 */

public enum Rocket {

    JUNO ("Juno", 1, 1, new double[]{3, 1, 0.33, 0, 0, 0, 0, 0, 0}),
    ATLAS("Atlas", 4, 5, new double[]{23, 9.5, 5, 2.75, 1.4, 0.5, 0, 0, 0}),
    SOYUZ("Soyuz", 9, 8, new double[]{71, 31, 17.66, 11, 7, 4.33, 2.29, 1, 0}),
    SATURN("Saturn", 20, 15, new double[]{180, 80, 46.66, 30, 20, 13.33, 8.57, 5, 2.22});

    private int mass;
    private int cost;
    private String name;
    private double[] thrustPerDifficulty;

    Rocket(String name, int mass, int cost, double[] thrust) {
        this.name = name;
        this.mass = mass;
        this.cost = cost;
        this.thrustPerDifficulty = thrust;
    }

    public int getMass() {
        return mass;
    }

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public double[] getThrustPerDifficultyList() {
        return thrustPerDifficulty;
    }

    public double getThrustPerDifficulty(int difficulty) {
        if(difficulty < 0 || difficulty > 9) {
            return 0;
        }
        return thrustPerDifficulty[difficulty - 1]; //difficulty starts from 1 in human readable form
    }
}
