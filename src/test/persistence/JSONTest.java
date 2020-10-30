package persistence;

import model.Exercise;
import model.ExerciseType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JSONTest {
    protected void checkExercise(String name, ExerciseType type, Exercise exercise) {
        assertEquals(name, exercise.getName());
        assertEquals(type, exercise.getType());
    }
}
