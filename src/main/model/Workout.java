package model;

import exceptions.InWorkoutException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Represents a Workout having exercises that are split into specific containers
public class Workout implements Writable {
    private List<Exercise> chestExercises;
    private List<Exercise> armExercises;
    private List<Exercise> backExercises;
    private List<Exercise> shoulderExercises;
    private List<Exercise> legExercises;
    private List<Exercise> allExercises;

    //EFFECTS: constructs a workout class containing multiple different array lists
    public Workout() {
        chestExercises = new ArrayList<>();
        armExercises = new ArrayList<>();
        backExercises = new ArrayList<>();
        shoulderExercises = new ArrayList<>();
        legExercises = new ArrayList<>();
        allExercises = new ArrayList<>();
    }

    // REQUIRES: ex != null
    // MODIFIES: this
    // EFFECTS: stores the given Exercise (ex) into the appropriate container within this class
    public void storeExercise(Exercise ex) {
        ExerciseType muscle;
        muscle = ex.getType();
        switch (muscle) {
            case ARMS:
                this.armExercises.add(ex);
                break;
            case CHEST:
                this.chestExercises.add(ex);
                break;
            case BACK:
                this.backExercises.add(ex);
                break;
            case LEGS:
                this.legExercises.add(ex);
                break;
            default:
                this.shoulderExercises.add(ex);
        }
    }

    // REQUIRES: ex != null
    // MODIFIES: this
    // EFFECTS: stores the given Exercise (ex) into the appropriate container within this class
    public void addExerciseFromButton(Exercise ex) throws InWorkoutException {
        ExerciseType muscle;
        muscle = ex.getType();
        if (!getAllExercises().contains(ex)) {
            switch (muscle) {
                case ARMS:
                    this.armExercises.add(ex);
                    break;
                case CHEST:
                    this.chestExercises.add(ex);
                    break;
                case BACK:
                    this.backExercises.add(ex);
                    break;
                case LEGS:
                    this.legExercises.add(ex);
                    break;
                default:
                    this.shoulderExercises.add(ex);
            }
        } else {
            throw new InWorkoutException();
        }
    }

    //getters
    public List<Exercise> getArmExercises() {
        return armExercises;
    }

    public List<Exercise> getBackExercises() {
        return backExercises;
    }

    public List<Exercise> getChestExercises() {
        return chestExercises;
    }

    public List<Exercise> getLegExercises() {
        return legExercises;
    }

    public List<Exercise> getShoulderExercises() {
        return shoulderExercises;
    }

    //MODIFIES: this
    //EFFECTS: returns all exercises in the form of a list of Exercises
    public List<Exercise> getAllExercises() {
        this.allExercises.clear();
        this.allExercises.addAll(armExercises);
        this.allExercises.addAll(chestExercises);
        this.allExercises.addAll(backExercises);
        this.allExercises.addAll(legExercises);
        this.allExercises.addAll(shoulderExercises);

        return allExercises;
    }


    //MODIFIES: this
    //REQUIRES: At least 3 exercises in the Chest Exercise array, and at least 2 exercises in the Arm Exercise Array
    //EFFECTS: returns a list of exercises to do on Monday
    public List<Exercise> mondayWorkout() {
        List<Exercise> exerciseList = new ArrayList<>();
        exerciseList.addAll(getRandomExercises(chestExercises, 3));
        exerciseList.addAll(getRandomExercises(armExercises, 2));

        return exerciseList;
    }

    //REQUIRES: At least 4 exercises in the Legs Exercise array
    //EFFECTS: returns a list of exercises to do on Tuesday
    public List<Exercise> tuesdayWorkout() {
        List<Exercise> exerciseList = new ArrayList<>();
        exerciseList.addAll(getRandomExercises(legExercises, 4));

        return exerciseList;

    }

    //REQUIRES: At least 4 exercises in the Shoulders Exercise array, and at least 2 exercises in the Arm Exercise Array
    //EFFECTS: returns a list of exercises to do on Monday
    public List<Exercise> wednesdayWorkout() {
        List<Exercise> exerciseList = new ArrayList<>();
        exerciseList.addAll(getRandomExercises(shoulderExercises, 4));
        exerciseList.addAll(getRandomExercises(armExercises, 2));

        return exerciseList;

    }

    //REQUIRES: At least 4 exercises in the Back Exercise array, and at least 1 exercises in the Chest Exercise Array,
    //EFFECTS: returns a list of exercises to do on Monday
    public List<Exercise> thursdayWorkout() {
        List<Exercise> exerciseList = new ArrayList<>();
        exerciseList.addAll(getRandomExercises(backExercises, 3));
        exerciseList.addAll(getRandomExercises(chestExercises, 2));

        return exerciseList;
    }

    public List<Exercise> getRandomExercises(List<Exercise> exercises, Integer num) {
        Integer rnd;
        List<Integer> numbers = new ArrayList<>();
        List<Exercise> exerciseList = new ArrayList<>();
        do {
            rnd = new Random().nextInt(exercises.size());
            if (!numbers.contains(rnd)) {
                exerciseList.add(exercises.get(rnd));
                numbers.add(rnd);
            }
        } while (numbers.size() < Math.min(exercises.size(), num));

        return exerciseList;
    }

    //EFFECTS: produces the exercise with given name in all workouts, or null if not found
    public Exercise findExercise(String name) {
        name = name.toUpperCase();
        for (Exercise e : allExercises) {
            String upperName =  e.getName().toUpperCase();
            if (upperName.equals(name)) {
                return e;
            }
        }
        return null;
    }

    //MODIFIES: this
    //EFFECTS: removes exercise if it is in the current workout, returning true
    // otherwise returns false
    public boolean removeExercise(String name) {
        Exercise exercise = findExercise(name);
        if (allExercises.contains(exercise)) {
            switch (exercise.getType()) {
                case SHOULDERS:
                    shoulderExercises.remove(exercise);
                    break;
                case LEGS:
                    legExercises.remove(exercise);
                    break;
                case BACK:
                    backExercises.remove(exercise);
                    break;
                case ARMS:
                    armExercises.remove(exercise);
                    break;
                case CHEST:
                    chestExercises.remove(exercise);
                    break;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("all-exercises", exercisesToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray exercisesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Exercise e : this.getAllExercises()) {
            jsonArray.put(e.toJson());
        }

        return jsonArray;
    }


}
