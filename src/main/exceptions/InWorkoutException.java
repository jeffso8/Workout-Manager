package exceptions;

public class InWorkoutException extends Exception {

    public InWorkoutException() {
        super("This exercise already exists in the workout");
    }
}
