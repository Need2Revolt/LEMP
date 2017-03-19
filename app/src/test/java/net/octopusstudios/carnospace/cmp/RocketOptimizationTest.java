package net.octopusstudios.carnospace.cmp;

import net.octopusstudios.carnospace.cmp.pojo.Stage;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests what little logic is there in this application.
 * This is the thing you'll trust when playing, to base your strategy upon...
 * Good luck with that... :P
 */
public class RocketOptimizationTest {

    @Test
    public void singleJuno() {
        singleRocketRequired(0, 2, 1);
    }

    @Test
    public void singleAtlas() {
        singleRocketRequired(1, 2, 8);
    }

    @Test
    public void singleSoyuz() {
        singleRocketRequired(2, 2, 29);
    }

    @Test
    public void singleSaturn() {
        singleRocketRequired(3, 2, 70);
    }

    private void singleRocketRequired(int rocketPos, int difficulty, int payloadMass) {
        //expected result
        List<String> expectedResult = new ArrayList<>(1);
        expectedResult.add(Stage.rocketNamesList[rocketPos]);

        //test logic
        Stage stage = new Stage("singleRocket", difficulty, payloadMass);
        assertNotNull(stage);
        assertEquals(expectedResult, stage.getRocktesList());
    }

    @Test
    public void multipleJuno() {
        multipleRocketsRequired(0, 1, 6);
    }

    @Test
    public void multipleAtlas() {
        multipleRocketsRequired(1, 1, 40);
    }

    @Test
    public void multipleSoyuz() {
        multipleRocketsRequired(2, 1, 140);
    }

    @Test
    public void multipleSaturn() {
        multipleRocketsRequired(3, 1, 355);
    }

    private void multipleRocketsRequired(int rocketPos, int difficulty, int payloadMass) {
        //expected result
        List<String> expectedResult = new ArrayList<>(2);
        expectedResult.add(Stage.rocketNamesList[rocketPos]);
        expectedResult.add(Stage.rocketNamesList[rocketPos]);

        //test logic
        Stage stage = new Stage("multiStage", difficulty, payloadMass);
        assertNotNull(stage);
        assertEquals(expectedResult, stage.getRocktesList());
    }

    @Test
    public void cornerCases() {
        //TODO robustness, like 0 payload or other funky stuffs
    }

    //---------------- tests from the rulebook, just to be sure...
    @Test
    public void rulebookExampleLunarDescent() {
        //lunar descent
        singleRocketRequired(0, 2, 1);

        //lunar transfer
        singleRocketRequired(1, 3, 2);

        //to orbit
        singleRocketRequired(2, 5, 6);

        //launch
        singleRocketRequired(2, 3, 15);
    }

    @Test
    public void rulebookExampleLunarDirect() {
        //earth transfer
        singleRocketRequired(1, 3, 2);

        //lunar ascent
        singleRocketRequired(1, 2, 6);

        //lunar descent
        {
            //expected result
            List<String> expectedResult = new ArrayList<>(2);
            expectedResult.add(Stage.rocketNamesList[1]);
            expectedResult.add(Stage.rocketNamesList[0]);

            //test logic
            Stage stage = new Stage("multi", 2, 10);
            assertNotNull(stage);
            assertEquals(expectedResult, stage.getRocktesList());
        }

        //lunar transfer
        singleRocketRequired(2, 3, 15);

        //to orbit
        {
            //expected result
            List<String> expectedResult = new ArrayList<>(2);
            expectedResult.add(Stage.rocketNamesList[3]);
            expectedResult.add(Stage.rocketNamesList[2]);

            //test logic
            Stage stage = new Stage("multi", 5, 24);
            assertNotNull(stage);
            assertEquals(expectedResult, stage.getRocktesList());
        }

        //launch
        {
            //expected result
            List<String> expectedResult = new ArrayList<>(3);
            expectedResult.add(Stage.rocketNamesList[1]);
            expectedResult.add(Stage.rocketNamesList[1]);
            expectedResult.add(Stage.rocketNamesList[3]);

            //test logic
            Stage stage = new Stage("multi", 3, 53);
            assertNotNull(stage);
            assertEquals(expectedResult, stage.getRocktesList());
        }
    }

    @Test
    public void rulebookExampleRendezvous() {
        //earth transfer
        {
            //expected result
            List<String> expectedResult = new ArrayList<>(3);
            expectedResult.add(Stage.rocketNamesList[0]);
            expectedResult.add(Stage.rocketNamesList[0]);
            expectedResult.add(Stage.rocketNamesList[0]);

            //test logic
            Stage stage = new Stage("multiStage", 3, 1);
            assertNotNull(stage);
            assertEquals(expectedResult, stage.getRocktesList());
        }

        //lunar ascent
        singleRocketRequired(1,2,4);

        //lunar descent
        singleRocketRequired(1,2,8);

        //lunar transfer
        singleRocketRequired(2,3,12);

        //to orbit
        {
            //expected result
            List<String> expectedResult = new ArrayList<>(2);
            expectedResult.add(Stage.rocketNamesList[3]);
            expectedResult.add(Stage.rocketNamesList[2]);

            //test logic
            Stage stage = new Stage("multiStage", 5, 23);
            assertNotNull(stage);
            assertEquals(expectedResult, stage.getRocktesList());
        }

        //launch
        {
            //expected result
            List<String> expectedResult = new ArrayList<>(3);
            expectedResult.add(Stage.rocketNamesList[1]);
            expectedResult.add(Stage.rocketNamesList[0]);
            expectedResult.add(Stage.rocketNamesList[3]);

            //test logic
            Stage stage = new Stage("multiStage", 3, 52);
            assertNotNull(stage);
            assertEquals(expectedResult, stage.getRocktesList());
        }
    }

    @Test
    public void problematic1() {
        //expected result
        List<String> expectedResult = new ArrayList<>(2);
        expectedResult.add(Stage.rocketNamesList[1]);
        expectedResult.add(Stage.rocketNamesList[0]);

        //test logic
        Stage stage = new Stage("multi", 2, 10);
        assertNotNull(stage);
        assertEquals(expectedResult, stage.getRocktesList());
    }

    @Test
    public void problematic2() {
        //expected result
        List<String> expectedResult = new ArrayList<>(3);
        expectedResult.add(Stage.rocketNamesList[1]);
        expectedResult.add(Stage.rocketNamesList[1]);
        expectedResult.add(Stage.rocketNamesList[3]);

        //test logic
        Stage stage = new Stage("multi", 3, 53);
        assertNotNull(stage);
        assertEquals(expectedResult, stage.getRocktesList());
    }

    @Test
    public void firstBugOnFieldTesting() {
        //expected result
        List<String> expectedResult = new ArrayList<>(2);
        expectedResult.add(Stage.rocketNamesList[2]);
        expectedResult.add(Stage.rocketNamesList[2]);

        //test logic
        Stage stage = new Stage("firstBug", 5, 10);
        assertNotNull(stage);
        assertEquals(expectedResult, stage.getRocktesList());
    }
}