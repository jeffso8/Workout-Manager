package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExerciseTest {
    private Exercise testExercise;

    @BeforeEach
    public void runBefore() {
        testExercise = new Exercise("Bench Press", ExerciseType.CHEST, 225, 5, 5);
    }

    @Test
    public void testConstructor(){
        assertEquals(testExercise.getName(), "Bench Press");
        assertEquals(testExercise.getType(), ExerciseType.CHEST);
        assertEquals(testExercise.getWeight(), 225);
        assertEquals(testExercise.getSets(), 5);
        assertEquals(testExercise.getReps(),5);
    }

    @Test
    public void setWeightTest(){
        testExercise.setWeight(230);
        assertEquals(testExercise.getWeight(), 230);
    }

    @Test
    public void setSetsTest(){
        testExercise.setSets(4);
        assertEquals(testExercise.getSets(), 4);
    }

    @Test
    public void setRepsTest(){
        testExercise.setReps(12);
        assertEquals(testExercise.getReps(), 12);
    }

    @Test
    public void equalsTest() {
        Exercise ex2 = new Exercise("Bench Press", ExerciseType.CHEST, 225, 5, 5);
        Exercise ex3 = new Exercise("Incline Bench Press", ExerciseType.CHEST, 225, 5, 5);
        assertTrue(testExercise.equals(ex2));
        assertFalse(testExercise.equals(ex3));
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
