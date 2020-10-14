package ui;

import model.Exercise;
import model.ExerciseType;
import model.Workout;

import java.util.List;
import java.util.Scanner;

// Workout Manager Application
public class WorkoutManager {
    Workout workout;

    Scanner scanner = new Scanner(System.in);

    // EFFECTS: runs the Workout Manager Application
    public WorkoutManager() {
        workout = new Workout();
        run();
    }

    //MODIFIES: this
    //EFFECTS: processes user input
    void run() {
        System.out.println("Welcome to your Workout Manager");
        String response;
        do {
            showMenu("Please Select an Option", "1. View Workout Today",
                    "2. Update Exercise", "3. Add Exercise",
                    "4. View All Exercises", "5. Exit");
            response = scanner.nextLine();

            switch (response) {
                case "1":
                    viewWorkoutToday();
                    break;
                case "2":
                    updateExercise();
                    break;
                case "3":
                    addExercise();
                    break;
                case "4":
                    viewExercises();
                    break;
            }
        } while (!response.equals("5"));
    }

    //EFFECTS: allows inputted strings to be printed as a menu
    private void showMenu(String s2, String s3, String s4, String s5, String s6, String s7) {
        System.out.println(s2);
        System.out.println(s3);
        System.out.println(s4);
        System.out.println(s5);
        System.out.println(s6);
        System.out.println(s7);

    }

    //MODIFIES: this, Exercise, Workout
    //EFFECTS: adds an exercise to the Workout class
    private void addExercise() {
        System.out.println("Please enter exercise name");
        String name = scanner.nextLine().toUpperCase();
        System.out.println("Please enter exercise type (CHEST, ARMS, LEGS, SHOULDERS, BACK)");
        ExerciseType e1 = ExerciseType.valueOf(scanner.nextLine().toUpperCase());
        workout.storeExercise(new Exercise(name, e1));

    }

    //MODIFIES: this
    //EFFECTS: prints exercises in a list of exercises
    private void printWorkoutStats(List<Exercise> ex) {
        for (Exercise e : ex) {
            System.out.println("(" + e.getName() + ',' + e.getWeight() + ','
                    + e.getSets() + 'x' + e.getReps() + ")");
        }
    }

    //MODIFIES: this
    //EFFECTS: prints the exercises for the given day
    private void viewWorkoutToday() {
        System.out.println("Please enter the current day (MONDAY, TUESDAY, WEDNESDAY, ETC.)");
        String day = scanner.nextLine().toUpperCase();
        switch (day) {
            case "MONDAY":
                printWorkoutStats(workout.mondayWorkout());
                break;
            case "TUESDAY":
                printWorkoutStats(workout.tuesdayWorkout());
                break;
            case "WEDNESDAY":
                printWorkoutStats(workout.wednesdayWorkout());
                break;
            case "THURSDAY":
                printWorkoutStats(workout.thursdayWorkout());
                break;
            default: System.out.println("Today is your rest day!");
        }
    }

    //MODIFIES: this
    //EFFECTS: updates the statistics of an exercise
    private void updateExercise() {
        System.out.println("Please enter the name of the exercise you want to update");
        String name = scanner.nextLine().toUpperCase();
        for (Exercise e : workout.getAllExercises()) {
            if (name.equals(e.getName().toUpperCase())) {
                System.out.println("Please enter the weight you lifted");
                int weight = scanner.nextInt();
                System.out.println("Please enter the sets you did");
                int sets = scanner.nextInt();
                System.out.println("Please enter the reps you did");
                int reps = scanner.nextInt();
                e.setWeight(weight);
                e.setSets(sets);
                e.setReps(reps);
            }
        }
    }

    //EFFECTS: displays all exercises logged in the Workout
    private void viewExercises() {
        for (Exercise e : workout.getAllExercises()) {
            System.out.println("(" + e.getName() + ", " + e.getType());
            System.out.println("Weight: " + e.getWeight() + " lbs" + " )");
        }
    }

}
