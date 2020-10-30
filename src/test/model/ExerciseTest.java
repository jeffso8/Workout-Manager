package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExerciseTest {
    private Exercise testExercise;

    @BeforeEach
    public void runBefore() {
        testExercise = new Exercise("Bench Press", ExerciseType.CHEST);
    }

    @Test
    public void testConstructor(){
        assertEquals(testExercise.getName(), "Bench Press");
        assertEquals(testExercise.getType(), ExerciseType.CHEST);
        assertEquals(testExercise.getWeight(), 0);
        assertEquals(testExercise.getSets(), 3);
        assertEquals(testExercise.getReps(),5);
    }

    @Test
    public void setWeightTest(){
        assertEquals(testExercise.getWeight(), 0);
        testExercise.setWeight(225);
        assertEquals(testExercise.getWeight(), 225);
    }

    @Test
    public void setSetsTest(){
        assertEquals(testExercise.getSets(), 3);
        testExercise.setSets(4);
        assertEquals(testExercise.getSets(), 4);
    }

    @Test
    public void setRepsTest(){
        assertEquals(testExercise.getReps(), 5);
        testExercise.setReps(12);
        assertEquals(testExercise.getReps(), 12);
    }

    @Test
    public void toJsonTest() {
        JSONObject json = testExercise.toJson();
        String exerciseName = json.getString("name");
        Object muscleType = json.get("type");
        assertEquals(exerciseName, "Bench Press");
        assertEquals(muscleType, ExerciseType.CHEST);
    }
}
