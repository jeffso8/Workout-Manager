package model;

import exceptions.InWorkoutException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

//Represents a Workout having exercises that are split into specific containers
public class Workout implements Writable {
    private List<Exercise> chestExercises;
    private List<Exercise> armExercises;
    private List<Exercise> backExercises;
    private List<Exercise> shoulderExercises;
    private List<Exercise> legExercises;
    private List<Exercise> allExercises;
    private List<String> workoutDays;
    private Map<String, String> workoutPlanner;

    //EFFECTS: constructs a workout class containing multiple different array lists
    public Workout() {
        chestExercises = new ArrayList<>();
        armExercises = new ArrayList<>();
        backExercises = new ArrayList<>();
        shoulderExercises = new ArrayList<>();
        legExercises = new ArrayList<>();
        allExercises = new ArrayList<>();
        workoutDays = new ArrayList<>();
        workoutDays.addAll(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));
        workoutPlanner = new HashMap<>();
        initWorkoutPlanner();
    }

    public void initWorkoutPlanner() {
        workoutPlanner.put(workoutDays.get(0), "Chest & Arms");
        workoutPlanner.put(workoutDays.get(1), "Legs");
        workoutPlanner.put(workoutDays.get(2), "Shoulder & Arms");
        workoutPlanner.put(workoutDays.get(3), "Chest & Back");
        workoutPlanner.put(workoutDays.get(4), "Chest & Arms");
        workoutPlanner.put(workoutDays.get(5), "Rest");
        workoutPlanner.put(workoutDays.get(6), "Rest");
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
    public List<String> getWorkoutDays() {
        return workoutDays;
    }

    public Map<String, String> getWorkoutPlanner() {
        return workoutPlanner;
    }

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

    //REQUIRES: given string must be one of "Chest & Arms", "Legs", "Shoulder & Arms", "Chest & Back", "Rest"
    //MODIFIES: this
    //EFFECTS: returns a list of exercises for a given muscle group
    public List<Exercise> getWorkoutFromTypes(String muscleGroup) {
        if (muscleGroup.equals("Chest & Arms")) {
            List<Exercise> exerciseList = new ArrayList<>();
            exerciseList.addAll(getRandomExercises(chestExercises, 3));
            exerciseList.addAll(getRandomExercises(armExercises, 2));
            return exerciseList;
        } else if (muscleGroup.equals("Legs")) {
            List<Exercise> exerciseList = new ArrayList<>();
            exerciseList.addAll(getRandomExercises(legExercises, 4));
            return exerciseList;
        } else if (muscleGroup.equals("Shoulder & Arms")) {
            List<Exercise> exerciseList = new ArrayList<>();
            exerciseList.addAll(getRandomExercises(shoulderExercises, 4));
            exerciseList.addAll(getRandomExercises(armExercises, 2));
            return exerciseList;
        } else {
            return getWorkoutFromTypesContinued(muscleGroup);
        }
    }

    //MODIFIES: this
    //EFFECTS: returns a list of exercises for a given muscle group
    public List<Exercise> getWorkoutFromTypesContinued(String muscleGroup) {
        if (muscleGroup.equals("Chest & Back")) {
            List<Exercise> exerciseList = new ArrayList<>();
            exerciseList.addAll(getRandomExercises(chestExercises, 3));
            exerciseList.addAll(getRandomExercises(backExercises, 2));
            return exerciseList;
        } else if (muscleGroup.equals("Rest")) {
            List<Exercise> exerciseList = new ArrayList<>();
            exerciseList.add(new Exercise("Rest",ExerciseType.REST,0,0,0));
            return exerciseList;
        } else {
            return null;
        }
    }

    //MODIFIES: this
    //EFFECTS: returns a list of exercises according to the given muscle group
    public List<Exercise> dayWorkout(String muscleGroup) {
        return getWorkoutFromTypes(muscleGroup);
    }

    //EFFECTS: returns a random exercise within specified range
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
        for (Exercise e : getAllExercises()) {
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
        if (getAllExercises().contains(exercise)) {
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
