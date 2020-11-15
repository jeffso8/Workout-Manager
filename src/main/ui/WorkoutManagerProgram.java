package ui;

import model.Workout;
import persistence.Reader;
import persistence.Writer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class WorkoutManagerProgram extends JFrame {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    private JMenu exercises;
    private JMenu mondayWorkout;
    private JMenu file;
    private static final String WORKOUT_FILE = "./data/workout.json";
    private Workout myWorkout;

    public WorkoutManagerProgram() {
        super("Workout Manager");
        initializeGraphics();
    }

    // MODIFIES: this
    // EFFECTS:  draws the JFrame window where this WorkoutManager will operate, and populates the tools to be used
    //           to manipulate this GUI
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        createMenus();
    }

    public void createMenus() {
        JMenuBar menuBar = new JMenuBar();
        exercises = new JMenu("Exercises");
        mondayWorkout = new JMenu("Monday Workout");
        file = new JMenu("File");
        setJMenuBar(menuBar);
        menuBar.add(exercises);
        menuBar.add(mondayWorkout);
        menuBar.add(file);
        fileMenu();
    }

    //MODIFIES: this
    //EFFECTS: adds items to clothing menu
    private void workoutMenu(JMenu addExercises) {
        exercises.add(addExercises);
//        addClothingSubmenu(addExercises);
        JMenuItem editExercise = new JMenuItem(new AbstractAction("Edit Exercises") {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: add edit
                //doEdit();
            }
        });
        exercises.add(editExercise);
        JMenuItem removeExercise = new JMenuItem(new AbstractAction("Remove Exercise") {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: add remove
                //doRemove();
            }
        });
        exercises.add(removeExercise);
        JMenuItem findExercises = new JMenuItem(new AbstractAction("Find Exercise") {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: add find
                //doFind();
            }
        });
        exercises.add(findExercises);
    }

    //MODIFIES: this
    //EFFECTS: adds items to file menu
    private void fileMenu() {
        JMenuItem save = new JMenuItem(new AbstractAction("Save Workout") {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveWorkout();
            }
        });
        file.add(save);
        JMenuItem load = new JMenuItem(new AbstractAction("Load Workout") {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadWardrobe();
            }
        });
        file.add(load);
    }


    private void saveWorkout() {
        Writer writer;
        try {
            writer = new Writer(WORKOUT_FILE);
            writer.write(myWorkout);
            writer.close();
            informationMessage("Workout saved to file " + WORKOUT_FILE, "File Saved");
        } catch (IOException e) {
            errorMessage("Unable to save wardrobe to " + WORKOUT_FILE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workout from WORKOUT_FILE, if that file exists;
    // otherwise initializes workout with default values
    private void loadWardrobe() {
        try {
            myWorkout = Reader.read();
            informationMessage("Successfully loaded a wardrobe from " + WORKOUT_FILE, "File Loaded");
        } catch (IOException e) {
            errorMessage("Unable to load a wardrobe from " + WORKOUT_FILE);
        }
    }

    private void informationMessage(String message, String title) {
        JOptionPane.showMessageDialog(WorkoutManagerProgram.this, message, title,
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void errorMessage(String message) {
        JOptionPane.showMessageDialog(WorkoutManagerProgram.this, message, "Error",
                JOptionPane.ERROR_MESSAGE);
    }


    public static void main(String [] args) {
        new WorkoutManagerProgram();
    }
}
