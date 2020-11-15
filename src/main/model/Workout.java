package model;

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
    //EFFECTS: generates a random number up to size of the list and
    // extracts that index from chestExercises
    public Exercise getOneChestExercise() {
        int rnd  = new Random().nextInt(chestExercises.size());
        return this.chestExercises.get(rnd);
    }

    //MODIFIES: this
    //EFFECTS: generates a random number up to size of the list
    // and extracts that index from armExercises
    public Exercise getOneArmExercise() {
        int rnd  = new Random().nextInt(armExercises.size());
        return this.armExercises.get(rnd);
    }

    //MODIFIES: this
    //EFFECTS: generates a random number up to size of the list
    // and extracts that index legExercises
    public Exercise getOneLegExercise() {
        int rnd  = new Random().nextInt(legExercises.size());
        return this.legExercises.get(rnd);
    }

    //MODIFIES: this
    //EFFECTS: generates a random number up to size of the list
    // and extracts that index from backExercises
    public Exercise getOneBackExercise() {
        int rnd  = new Random().nextInt(backExercises.size());
        return this.backExercises.get(rnd);
    }

    //MODIFIES: this
    //EFFECTS: generates a random number up to size of the list
    // and extracts that index from shoulderExercises
    public Exercise getOneShoulderExercise() {
        int rnd  = new Random().nextInt(shoulderExercises.size());
        return this.shoulderExercises.get(rnd);
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
        exerciseList.add(this.getOneChestExercise());
        exerciseList.add(this.getOneChestExercise());
        exerciseList.add(this.getOneChestExercise());
        exerciseList.add(this.getOneArmExercise());
        exerciseList.add(this.getOneArmExercise());

        return exerciseList;
    }

    //REQUIRES: At least 4 exercises in the Legs Exercise array
    //EFFECTS: returns a list of exercises to do on Tuesday
    public List<Exercise> tuesdayWorkout() {
        List<Exercise> exerciseList = new ArrayList<>();
        exerciseList.add(this.getOneLegExercise());
        exerciseList.add(this.getOneLegExercise());
        exerciseList.add(this.getOneLegExercise());
        exerciseList.add(this.getOneLegExercise());

        return exerciseList;
    }

    //REQUIRES: At least 4 exercises in the Shoulders Exercise array, and at least 2 exercises in the Arm Exercise Array
    //EFFECTS: returns a list of exercises to do on Monday
    public List<Exercise> wednesdayWorkout() {
        List<Exercise> exerciseList = new ArrayList<>();
        exerciseList.add(this.getOneShoulderExercise());
        exerciseList.add(this.getOneShoulderExercise());
        exerciseList.add(this.getOneShoulderExercise());
        exerciseList.add(this.getOneArmExercise());
        exerciseList.add(this.getOneArmExercise());

        return exerciseList;

    }

    //REQUIRES: At least 4 exercises in the Back Exercise array, and at least 1 exercises in the Chest Exercise Array,
    //EFFECTS: returns a list of exercises to do on Monday
    public List<Exercise> thursdayWorkout() {
        List<Exercise> exerciseList = new ArrayList<>();
        exerciseList.add(this.getOneBackExercise());
        exerciseList.add(this.getOneBackExercise());
        exerciseList.add(this.getOneBackExercise());
        exerciseList.add(this.getOneChestExercise());
        exerciseList.add(this.getOneBackExercise());

        return exerciseList;
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
