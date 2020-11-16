package ui;

import model.Exercise;
import model.ExerciseType;
import model.Workout;
import persistence.Reader;
import persistence.Writer;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// Workout Manager Application
public class WorkoutManager {
    private static final String JSON_STORE = "./data/workout.json";

    Workout workout;
    Scanner scanner = new Scanner(System.in);
    private Writer jsonWriter;
    private Reader jsonReader;

    // EFFECTS: runs the Workout Manager Application
    public WorkoutManager() {
        workout = new Workout();
        jsonWriter = new Writer(JSON_STORE);
        jsonReader = new Reader(JSON_STORE);
        run();
    }

    //MODIFIES: this
    //EFFECTS: processes user input
    void run() {
        System.out.println("Welcome to your Workout Manager");
        String response;
        do {
            showMenu("Please Select an Option", "1. View Workout Today", "2. Update Exercise",
                    "3. Add Exercise", "4. View All Exercises", "5. Save Workout to File",
                    "6. Load Workout from File","7. Exit");
            response = scanner.nextLine();
            if (response.equals("1")) {
                viewWorkoutToday();
            } else if (response.equals("2")) {
                updateExercise();
            } else if (response.equals("3")) {
                addExercise();
            } else if (response.equals("4")) {
                viewExercises();
            } else if (response.equals("5")) {
                saveWorkout();
            } else if (response.equals("6")) {
                loadWorkout();
            }
        } while (!response.equals("7"));
    }

    //EFFECTS: allows inputted strings to be printed as a menu
    private void showMenu(String s2, String s3, String s4, String s5, String s6, String s7, String s8, String s9) {
        System.out.println(s2);
        System.out.println(s3);
        System.out.println(s4);
        System.out.println(s5);
        System.out.println(s6);
        System.out.println(s7);
        System.out.println(s8);
        System.out.println(s9);

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

    //References JsonSerializationDemo
    // EFFECTS: saves the workroom to file
    private void saveWorkout() {
        try {
            jsonWriter.open();
            jsonWriter.write(workout);
            jsonWriter.close();
            System.out.println("Saved workout to " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //References JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: loads workout from file
    private void loadWorkout() {
        try {
            workout = jsonReader.read();
            System.out.println("Loaded workout from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
