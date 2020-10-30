package persistence;

import model.Exercise;
import model.ExerciseType;
import model.Workout;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderTest extends JSONTest {

    @Test
    void testReaderNoFile() {
        Reader reader = new Reader("./data/nonExistentFile.json");
        try {
            Workout w = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            //expected
        }
    }

    @Test
    void testReaderEmptyWorkout() {
        Reader reader = new Reader("./data/testReaderEmptyWorkout.json");
        try {
            Workout w = reader.read();
            assertEquals(0, w.getAllExercises().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderWorkout() {
        Reader reader = new Reader("./data/testReaderWorkout.json");
        try {
            Workout w = reader.read();
            List<Exercise> exercises = w.getAllExercises();
            assertEquals(2, exercises.size());
            checkExercise("BICEP CURLS", ExerciseType.ARMS, exercises.get(0));
            checkExercise("BENCH PRESS", ExerciseType.CHEST, exercises.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
