package persistence;

import model.Exercise;
import model.ExerciseType;
import model.Workout;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class WriterTest extends JSONTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Workout w = new Workout();
            Writer writer = new Writer("./data/\0invalid:fileName.json");
            writer.open();
            fail("IOException was expected but not thrown");
        } catch (IOException e) {
            //expected
        }
    }

    @Test
    void testWriterEmptyWorkout() {
        try {
            Workout w = new Workout();
            Writer writer = new Writer("./data/testWriterEmptyWorkout.json");
            writer.open();
            writer.write(w);
            writer.close();

            Reader reader = new Reader("./data/testWriterEmptyWorkout.json");
            w = reader.read();
            assertEquals(0, w.getAllExercises().size());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkout() {
        try {
            Workout w = new Workout();
            w.storeExercise(new Exercise("Bicep Curls", ExerciseType.ARMS, 30, 4, 12));
            w.storeExercise(new Exercise("Bench Press", ExerciseType.CHEST, 225, 5,5));
            Writer writer = new Writer("./data/testWriterGeneralWorkout.json");
            writer.open();
            writer.write(w);
            writer.close();

            Reader reader = new Reader("./data/testWriterGeneralWorkout.json");
            w = reader.read();
            List<Exercise> exercises = w.getAllExercises();
            assertEquals(2, exercises.size());
            checkExercise("Bicep Curls", ExerciseType.ARMS, exercises.get(0));
            checkExercise("Bench Press", ExerciseType.CHEST, exercises.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


}
