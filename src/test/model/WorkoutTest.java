package model;

import exceptions.InWorkoutException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.ExerciseType.*;
import static org.junit.jupiter.api.Assertions.*;


public class WorkoutTest {
    private Workout testWorkout;

    @BeforeEach
    public void runBefore() {
        testWorkout = new Workout();
    }

    @Test
    public void testConstructor(){
        assertTrue(testWorkout.getChestExercises().isEmpty());
        assertTrue(testWorkout.getArmExercises().isEmpty());
        assertTrue(testWorkout.getBackExercises().isEmpty());
        assertTrue(testWorkout.getLegExercises().isEmpty());
        assertTrue(testWorkout.getShoulderExercises().isEmpty());
        assertEquals(7, testWorkout.getWorkoutDays().size());
        assertFalse(testWorkout.getWorkoutPlanner().isEmpty());
    }

    @Test
    public void storeExerciseTest(){
        Exercise ex = new Exercise("Barbell Bench Press", CHEST,50,3,5);
        Exercise ex1 = new Exercise("Shoulder Press", SHOULDERS,50,3,5);
        Exercise ex2 = new Exercise("Back Squat", LEGS,50,3,5);
        Exercise ex3 = new Exercise("Bicep Curls", ARMS,50,3,5);
        Exercise ex4 = new Exercise("Deadlifts", BACK,50,3,5);

        assertTrue(testWorkout.getChestExercises().isEmpty());
        testWorkout.storeExercise(ex);
        assertFalse(testWorkout.getChestExercises().isEmpty());

        assertTrue(testWorkout.getShoulderExercises().isEmpty());
        testWorkout.storeExercise(ex1);
        assertFalse(testWorkout.getShoulderExercises().isEmpty());

        assertTrue(testWorkout.getLegExercises().isEmpty());
        testWorkout.storeExercise(ex2);
        assertFalse(testWorkout.getLegExercises().isEmpty());

        assertTrue(testWorkout.getArmExercises().isEmpty());
        testWorkout.storeExercise(ex3);
        assertFalse(testWorkout.getArmExercises().isEmpty());

        assertTrue(testWorkout.getBackExercises().isEmpty());
        testWorkout.storeExercise(ex4);
        assertFalse(testWorkout.getBackExercises().isEmpty());

    }

    @Test
    public void getAllExercisesTest() {
        assertTrue(testWorkout.getAllExercises().isEmpty());
        Exercise ex = new Exercise("Barbell Bench Press", CHEST, 120, 4,8);
        Exercise ex1 = new Exercise("Shoulder Press", SHOULDERS, 120, 4,8);
        testWorkout.storeExercise(ex);
        testWorkout.storeExercise(ex1);
        assertFalse(testWorkout.getAllExercises().isEmpty());
        assertEquals(testWorkout.getAllExercises().size(), 2);
    }

    @Test
    public void dayWorkoutChestAndArmsTest() {
        //Store exercises in their correct container
        Exercise ex = new Exercise("Barbell Bench Press", CHEST, 225, 5, 5);
        testWorkout.storeExercise(ex);
        Exercise ex1 = new Exercise("Cable Flies", CHEST,25,3,4);
        testWorkout.storeExercise(ex1);
        Exercise ex2 = new Exercise("Incline Dumbbell Press", CHEST,100,3,4);
        testWorkout.storeExercise(ex2);
        Exercise ex3 = new Exercise("Triceps Extensions", ARMS,20,4,12);
        testWorkout.storeExercise(ex3);
        Exercise ex4 = new Exercise("Bicep Curls", ARMS,30,5,4);
        testWorkout.storeExercise(ex4);

        //Run mondayWorkout, and a list of exercises should be returned
        testWorkout.dayWorkout("Chest & Arms");
        assertFalse(testWorkout.dayWorkout("Chest & Arms").isEmpty());
        assertEquals(testWorkout.dayWorkout("Chest & Arms").size(), 5);
    }

    @Test
    public void dayWorkoutLegsTest() {
        //Store exercises in their correct container
        Exercise ex = new Exercise("Back Squats", LEGS, 225, 5,5);
        testWorkout.storeExercise(ex);
        Exercise ex1 = new Exercise("Leg Extensions", LEGS, 80,4,10);
        testWorkout.storeExercise(ex1);
        Exercise ex2 = new Exercise("Hamstring Curls", LEGS,75,4,10);
        testWorkout.storeExercise(ex2);
        Exercise ex3 = new Exercise("Dumbbell Lunges", LEGS,35,4,12);
        testWorkout.storeExercise(ex3);
        Exercise ex4 = new Exercise("Front Squats", LEGS, 235,5,5);
        testWorkout.storeExercise(ex4);

        //Run tuesdayWorkout, and a list of exercises should be returned

        System.out.println(testWorkout.dayWorkout("Legs"));
        assertFalse(testWorkout.dayWorkout("Legs").isEmpty());
        assertEquals(testWorkout.dayWorkout("Legs").size(), 4);

    }

    @Test
    public void dayWorkoutShouldersAndArmsTest() {
        //Store exercises in their correct container
        Exercise ex = new Exercise("Overhead Press", SHOULDERS, 125,4,8);
        testWorkout.storeExercise(ex);
        Exercise ex1 = new Exercise("Seated Dumbbell Overhead Press", SHOULDERS,70,4,5);
        testWorkout.storeExercise(ex1);
        Exercise ex2 = new Exercise("Lateral Raises", SHOULDERS,15,4,12);
        testWorkout.storeExercise(ex2);
        Exercise ex3 = new Exercise("Face Pulls", SHOULDERS,30,5,8);
        testWorkout.storeExercise(ex3);
        Exercise ex4 = new Exercise("Bicep Curls", ARMS,30,4,12);
        testWorkout.storeExercise(ex4);
        Exercise ex5 = new Exercise("Tricep Cable Pulls", ARMS, 20,4,12);
        testWorkout.storeExercise(ex5);

        //Run wednesdayWorkout, and a list of exercises should be returned
        testWorkout.dayWorkout("Shoulder & Arms");
        assertFalse(testWorkout.dayWorkout("Shoulder & Arms").isEmpty());
        assertEquals(testWorkout.dayWorkout("Shoulder & Arms").size(), 6);
    }

    @Test
    public void dayWorkoutChestAndBackTest() {
        //Store exercises in their correct container
        Exercise ex = new Exercise("Sumo Deadlift", BACK, 225,5,5);
        testWorkout.storeExercise(ex);
        Exercise ex1 = new Exercise("Good Morning Extensions", BACK,35,3,4);
        testWorkout.storeExercise(ex1);
        Exercise ex2 = new Exercise("Seated Cable Rows", BACK,50,4,3);
        testWorkout.storeExercise(ex2);
        Exercise ex3 = new Exercise("Pull-ups", BACK,0,6,6);
        testWorkout.storeExercise(ex3);
        Exercise ex4 = new Exercise("Push-ups", CHEST,0,6,15);
        testWorkout.storeExercise(ex4);
        Exercise ex5 = new Exercise("Bicep Curls", ARMS,30,4,12);
        testWorkout.storeExercise(ex5);

        //Run thursdayWorkout, and a list of exercises should be returned
        testWorkout.dayWorkout("Chest & Back");
        assertFalse(testWorkout.dayWorkout("Chest & Back").isEmpty());
        assertEquals(testWorkout.dayWorkout("Chest & Back").size(), 3);
    }

    @Test
    public void dayWorkoutRestTest() {
        //Store exercises in their correct container
        Exercise ex = new Exercise("Sumo Deadlift", BACK, 225,5,5);
        testWorkout.storeExercise(ex);
        Exercise ex1 = new Exercise("Good Morning Extensions", BACK,35,3,4);
        testWorkout.storeExercise(ex1);
        Exercise ex2 = new Exercise("Seated Cable Rows", BACK,50,4,3);
        testWorkout.storeExercise(ex2);
        Exercise ex3 = new Exercise("Pull-ups", BACK,0,6,6);
        testWorkout.storeExercise(ex3);
        Exercise ex4 = new Exercise("Push-ups", CHEST,0,6,15);
        testWorkout.storeExercise(ex4);
        Exercise ex5 = new Exercise("Rest", REST,0,0,0);
        testWorkout.storeExercise(ex5);

        //Run thursdayWorkout, and a list of exercises should be returned
        testWorkout.dayWorkout("Rest");
        assertFalse(testWorkout.dayWorkout("Rest").isEmpty());
        assertEquals(testWorkout.dayWorkout("Rest").size(), 1);
    }

    @Test
    public void addExerciseFromButtonTestNoException() {
        Exercise ex = new Exercise("Sumo Deadlift", BACK, 225,5,5);
        testWorkout.storeExercise(ex);
        Exercise ex1 = new Exercise("Good Morning Extensions", BACK,35,3,4);
        testWorkout.storeExercise(ex1);
        Exercise ex2 = new Exercise("Push-ups", CHEST,0,6,15);
        try {
            testWorkout.addExerciseFromButton(ex2);
            assertEquals(1, testWorkout.getChestExercises().size());
        } catch (InWorkoutException e) {
            fail();
        }
    }

    @Test
    public void addExerciseFromButtonTestException() {
        Exercise ex = new Exercise("Sumo Deadlift", BACK, 225,5,5);
        testWorkout.storeExercise(ex);
        Exercise ex1 = new Exercise("Good Morning Extensions", BACK,35,3,4);
        testWorkout.storeExercise(ex1);
        Exercise ex2 = new Exercise("Push-ups", CHEST,0,6,15);
        testWorkout.storeExercise(ex2);
        try {
            testWorkout.addExerciseFromButton(ex2);
            fail();
        } catch (InWorkoutException e) {
            assertEquals("This exercise already exists in the workout", e.getMessage());
        }
    }

    @Test
    public void findExerciseTestFound() {
        Exercise ex1 = new Exercise("Good Morning Extensions", BACK,35,3,4);
        testWorkout.storeExercise(ex1);
        Exercise ex2 = new Exercise("Push-ups", CHEST,0,6,15);
        testWorkout.storeExercise(ex2);
        assertEquals(ex2, testWorkout.findExercise("Push-ups"));
    }

    @Test
    public void findExerciseTestNotFound() {
        Exercise ex1 = new Exercise("Good Morning Extensions", BACK,35,3,4);
        testWorkout.storeExercise(ex1);
        Exercise ex2 = new Exercise("Push-ups", CHEST,0,6,15);
        testWorkout.storeExercise(ex2);
        assertEquals(null, testWorkout.findExercise("Bench Press"));
    }

    @Test
    public void removeExerciseTestContains() {
        Exercise ex1 = new Exercise("Good Morning Extensions", BACK,35,3,4);
        testWorkout.storeExercise(ex1);
        Exercise ex2 = new Exercise("Push-ups", CHEST,0,6,15);
        testWorkout.storeExercise(ex2);
        assertTrue(testWorkout.removeExercise("Push-ups"));
        assertEquals(1, testWorkout.getAllExercises().size());
    }

    @Test
    public void removeExerciseTestNotContained() {
        Exercise ex1 = new Exercise("Good Morning Extensions", BACK,35,3,4);
        testWorkout.storeExercise(ex1);
        Exercise ex2 = new Exercise("Push-ups", CHEST,0,6,15);
        testWorkout.storeExercise(ex2);
        assertFalse(testWorkout.removeExercise("Bench Press"));
    }

    @Test
    public void toJsonTest() {
        testWorkout.storeExercise(new Exercise("Bench Press", CHEST, 225, 5,5));
        JSONObject json = testWorkout.toJson();
        JSONArray jsonArray = json.getJSONArray("all-exercises");
        JSONObject jsonExercises = jsonArray.getJSONObject(0);
        System.out.println(jsonExercises);
        String exerciseName = jsonExercises.getString("name");
        Object muscleType = jsonExercises.get("type");

        assertEquals(exerciseName, "Bench Press");
        assertEquals(muscleType, CHEST);
    }




}