package model;

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

    }

    @Test
    public void storeExerciseTest(){
        Exercise ex = new Exercise("Barbell Bench Press", CHEST);
        Exercise ex1 = new Exercise("Shoulder Press", SHOULDERS);
        Exercise ex2 = new Exercise("Back Squat", LEGS);
        Exercise ex3 = new Exercise("Bicep Curls", ARMS);
        Exercise ex4 = new Exercise("Deadlifts", BACK);

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
        Exercise ex = new Exercise("Barbell Bench Press", CHEST);
        Exercise ex1 = new Exercise("Shoulder Press", SHOULDERS);
        testWorkout.storeExercise(ex);
        testWorkout.storeExercise(ex1);
        assertFalse(testWorkout.getAllExercises().isEmpty());
        assertEquals(testWorkout.getAllExercises().size(), 2);
    }

    @Test
    public void mondayWorkoutTest() {
        //Store exercises in their correct container
        Exercise ex = new Exercise("Barbell Bench Press", CHEST);
        testWorkout.storeExercise(ex);
        Exercise ex1 = new Exercise("Cable Flies", CHEST);
        testWorkout.storeExercise(ex1);
        Exercise ex2 = new Exercise("Incline Dumbbell Press", CHEST);
        testWorkout.storeExercise(ex2);
        Exercise ex3 = new Exercise("Triceps Extensions", ARMS);
        testWorkout.storeExercise(ex3);
        Exercise ex4 = new Exercise("Spider Curls", ARMS);
        testWorkout.storeExercise(ex4);

        //Run mondayWorkout, and a list of exercises should be returned
        testWorkout.mondayWorkout();
        assertFalse(testWorkout.mondayWorkout().isEmpty());
        assertEquals(testWorkout.mondayWorkout().size(), 5);

    }

    @Test
    public void tuesdayWorkoutTest() {
        //Store exercises in their correct container
        Exercise ex = new Exercise("Back Squats", LEGS);
        testWorkout.storeExercise(ex);
        Exercise ex1 = new Exercise("Leg Extensions", LEGS);
        testWorkout.storeExercise(ex1);
        Exercise ex2 = new Exercise("Hamstring Curls", LEGS);
        testWorkout.storeExercise(ex2);
        Exercise ex3 = new Exercise("Dumbbell Lunges", LEGS);
        testWorkout.storeExercise(ex3);
        Exercise ex4 = new Exercise("Front Squats", LEGS);
        testWorkout.storeExercise(ex4);

        //Run tuesdayWorkout, and a list of exercises should be returned
        testWorkout.tuesdayWorkout();
        assertFalse(testWorkout.tuesdayWorkout().isEmpty());
        assertEquals(testWorkout.tuesdayWorkout().size(), 4);

    }

    @Test
    public void wednesdayWorkoutTest() {
        //Store exercises in their correct container
        Exercise ex = new Exercise("Overhead Press", SHOULDERS);
        testWorkout.storeExercise(ex);
        Exercise ex1 = new Exercise("Seated Dumbbell Overhead Press", SHOULDERS);
        testWorkout.storeExercise(ex1);
        Exercise ex2 = new Exercise("Lateral Raises", SHOULDERS);
        testWorkout.storeExercise(ex2);
        Exercise ex3 = new Exercise("Face Pulls", SHOULDERS);
        testWorkout.storeExercise(ex3);
        Exercise ex4 = new Exercise("Bicep Curls", ARMS);
        testWorkout.storeExercise(ex4);
        Exercise ex5 = new Exercise("Bicep Curls", ARMS);
        testWorkout.storeExercise(ex5);

        //Run wednesdayWorkout, and a list of exercises should be returned
        testWorkout.wednesdayWorkout();
        assertFalse(testWorkout.wednesdayWorkout().isEmpty());
        assertEquals(testWorkout.wednesdayWorkout().size(), 5);

    }

    @Test
    public void thursdayWorkoutTest() {
        //Store exercises in their correct container
        Exercise ex = new Exercise("Sumo Deadlift", BACK);
        testWorkout.storeExercise(ex);
        Exercise ex1 = new Exercise("Good Morning Extensions", BACK);
        testWorkout.storeExercise(ex1);
        Exercise ex2 = new Exercise("Seated Cable Rows", BACK);
        testWorkout.storeExercise(ex2);
        Exercise ex3 = new Exercise("Pull-ups", BACK);
        testWorkout.storeExercise(ex3);
        Exercise ex4 = new Exercise("Push-ups", CHEST);
        testWorkout.storeExercise(ex4);
        Exercise ex5 = new Exercise("Bicep Curls", ARMS);
        testWorkout.storeExercise(ex5);

        //Run thursdayWorkout, and a list of exercises should be returned
        testWorkout.thursdayWorkout();
        assertFalse(testWorkout.thursdayWorkout().isEmpty());
        assertEquals(testWorkout.thursdayWorkout().size(), 5);

    }




}