package persistence;

import model.Exercise;
import model.ExerciseType;
import model.Workout;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

 //Referenced JsonDemo Code Provided By UBC
public class Reader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public Reader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workout from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Workout read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkout(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workout from JSON object and returns it
    private Workout parseWorkout(JSONObject jsonObject) {
        Workout w = new Workout();
        addExercises(w, jsonObject);
        return w;
    }

    // MODIFIES: w
    // EFFECTS: parses list of exercises from JSON object and adds them to workroom
    private void addExercises(Workout w, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("all-exercises");
        for (Object json : jsonArray) {
            JSONObject nextExercise = (JSONObject) json;
            addExercise(w, nextExercise);
        }
    }

    // MODIFIES: w
    // EFFECTS: parses exercise from JSON object and adds it to workout
    private void addExercise(Workout w, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        ExerciseType type = ExerciseType.valueOf(jsonObject.getString("muscle-type"));
        Exercise e1 = new Exercise(name, type);
        w.storeExercise(e1);
    }

}