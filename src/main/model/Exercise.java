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
    public Exercise(String name, ExerciseType type, Integer weight, Integer sets, Integer reps) {
        this.name = name;
        this.weight = weight;
        this.type = type;
        this.sets = sets;
        this.reps = reps;
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
    //EFFECTS: updates name to the given string
    public void setName(String name) {
        this.name = name;
    }

    //MODIFIES: this
    //EFFECTS: updates type to the given type
    public void setType(ExerciseType type) {
        this.type = type;
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
        json.put("type", type);
        json.put("weight", weight);
        json.put("sets", sets);
        json.put("reps", reps);
        return json;
    }
}
