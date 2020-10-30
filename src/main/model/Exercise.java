package model;

import org.json.JSONObject;
import persistence.Writable;

public class Exercise implements Writable {

    private ExerciseType type;
    private int weight;
    private String name;
    private int sets;
    private int reps;
    private String description;

    //MODIFIES: this
    //EFFECTS: creates an Exercise object with the name of the exercise and type(ENUM)
    public Exercise(String name, ExerciseType type) {
        this.name = name;
        this.weight = 0;
        this.type = type;
        this.sets = 3;
        this.reps = 5;
    }

    //getters
    public String getName() {
        return name;
    }

    public ExerciseType getType() {
        return type;
    }

    public int getWeight() {
        return weight;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }

    //MODIFIES: this
    //EFFECTS: updates weight lifted to the given num
    public void setWeight(int num) {
        weight = num;
    }

    //MODIFIES: this
    //EFFECTS: updates reps lifted to the given num
    public void setReps(int num) {
        reps = num;
    }

    //MODIFIES: this
    //EFFECTS: updates sets lifted to the given num
    public void setSets(int num) {
        sets = num;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("muscle-type", type);
        return json;
    }
}
