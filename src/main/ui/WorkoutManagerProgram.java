package ui;

import exceptions.InWorkoutException;
import model.Exercise;
import model.ExerciseType;
import model.Workout;
import persistence.Reader;
import persistence.Writer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorkoutManagerProgram extends JFrame {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    private JMenu exercises;
    private JMenu workouts;
    private JMenu file;
    private static final String WORKOUT_FILE = "./data/workout.json";
    private Workout myWorkout;
    private List<Exercise> exerciseList;
    private JScrollPane scroller;
    private JList<String> exerciseStringList;
    private JList<String> workoutStringList;
    private boolean isExercisesDisplayed;
    private boolean isWorkoutsDisplayed;
    private JPanel buttonPane;
    private JButton openButton;



    public WorkoutManagerProgram() {
        super("Workout Manager");
        isExercisesDisplayed = false;
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
        initializeData();
        createExerciseList(myWorkout.getAllExercises());
        JLabel label = new JLabel("Label");
        JScrollPane scrollPane = new JScrollPane(label);
        this.add(scrollPane, BorderLayout.EAST);
    }

    //MODIFIES: this
    //EFFECTS: creates the workout and loads the existing one from file if there exists one
    private void initializeData() {
        myWorkout = new Workout();
        loadWorkout();
    }

    //https://docs.oracle.com/javase/tutorial/uiswing/components/menu.html
    //MODIFIES: this
    //EFFECTS: create a menu bar with items
    public void createMenus() {
        JMenuBar menuBar = new JMenuBar();
        exercises = new JMenu("Exercises");
        workouts = new JMenu("Workouts");
        file = new JMenu("File");
        setJMenuBar(menuBar);
        menuBar.add(exercises);
        menuBar.add(workouts);
        menuBar.add(file);
        fileMenu();
        JMenu addExercise = new JMenu("Add Exercise");
        exerciseMenu(addExercise);
        workoutMenu();
    }

    //MODIFIES: this
    //EFFECTS: creates the current exercise list and adds to main frame
    public void createExerciseList(List<Exercise> addToMainFrame) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        exerciseList = new ArrayList<>();
        for (Exercise e : addToMainFrame) {
            listModel.addElement(e.getType() + " - Name: " + e.getName() + ", Weight: " + e.getWeight()
                    + " , Sets x Reps: " + e.getSets() + "x" + e.getReps());
            exerciseList.add(e);
        }
        configureList(listModel);
    }

    //EFFECTS: configures the list in the pane
    private void configureList(ListModel listModel) {
        scroller = new JScrollPane();
        exerciseStringList = new JList<>(listModel);
        exerciseStringList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        exerciseStringList.setFont(new Font("Geneva", Font.PLAIN, 21));
        scroller.setViewportView(exerciseStringList);
        exerciseStringList.setLayoutOrientation(JList.VERTICAL);
        add(scroller);
        addButtonViewExercise();
        setVisible(true);
    }

    //EFFECTS: configures the list in the pane
    private void configureWorkoutList(ListModel listModel) {
        scroller = new JScrollPane();
        exerciseStringList = new JList<>(listModel);
        exerciseStringList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        exerciseStringList.setFont(new Font("System", Font.PLAIN, 24));
        scroller.setViewportView(exerciseStringList);
        exerciseStringList.setLayoutOrientation(JList.VERTICAL);
        add(scroller);
        addButtonViewExercise();
        setVisible(true);
    }

    //https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
    //https://stackoverflow.com/questions/5911565/how-to-add-multiple-actionlisteners-for-multiple-buttons-in-java-swing/5911621 <- for all button actions
    //MODIFIES: this
    //EFFECTS: adds a button to bottom panel that opens the selected exercise item
    private void addButtonViewExercise() {
        openButton = new JButton(new AbstractAction("Open Exercise") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = exerciseStringList.getSelectedIndex();
                Exercise selectedItem = exerciseList.get(index);
                displayPopupExercise(selectedItem);
            }
        });
        openButton.setFont(new Font("System", Font.PLAIN, 20));
        buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(openButton);
        add(buttonPane, BorderLayout.PAGE_END);
        openButton.setVisible(true);
        buttonPane.setVisible(true);
    }

//    //MODIFIES: this
//    //EFFECTS: adds a button to bottom panel that opens the selected exercise item for individual workout days
//    private void addButtonMakeWorkout() {
//        openButton = new JButton(new AbstractAction("Add to Workout") {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int index = workoutStringList.getSelectedIndex();
//                Exercise selectedItem = workoutList.get(index);
//                displayPopupExercise(selectedItem);
//            }
//        });
//        openButton.setFont(new Font("System", Font.PLAIN, 20));
//        buttonPane = new JPanel();
//        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
//        buttonPane.add(openButton);
//        mainPane.add(buttonPane, gbcButton);
//        openButton.setVisible(true);
//        buttonPane.setVisible(true);
//    }

    //MODIFIES: this
    //EFFECTS: adds sub menu items to exercises menu tab
    private void exerciseMenu(JMenu addExercise) {
        exercises.add(addExercise);
        addExerciseSubmenu(addExercise);
        JMenuItem editExercise = new JMenuItem(new AbstractAction("Edit Exercises") {
            @Override
            public void actionPerformed(ActionEvent e) {
                editExercise();
            }
        });
        exercises.add(editExercise);
        JMenuItem removeExercise = new JMenuItem(new AbstractAction("Remove Exercise") {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeExercise();
            }
        });
        exercises.add(removeExercise);
        JMenuItem findExercises = new JMenuItem(new AbstractAction("Find Exercise") {
            @Override
            public void actionPerformed(ActionEvent e) {
                findExercise();
            }
        });
        exercises.add(findExercises);
    }


    //MODIFIES: this
    //EFFECTS: adds items to addClothing submenu
    private void addExerciseSubmenu(JMenu addExercise) {
        JMenuItem addArmExercise = new JMenuItem(new AbstractAction("Arms") {
            @Override
            public void actionPerformed(ActionEvent e) {
                addInputExercise(ExerciseType.ARMS);
            }
        });
        addExercise.add(addArmExercise);
        JMenuItem addChestExercise = new JMenuItem(new AbstractAction("Chest") {
            @Override
            public void actionPerformed(ActionEvent e) {
                addInputExercise(ExerciseType.CHEST);
            }
        });
        addExercise.add(addChestExercise);
        JMenuItem addLegExercise = new JMenuItem(new AbstractAction("Legs") {
            @Override
            public void actionPerformed(ActionEvent e) {
                addInputExercise(ExerciseType.LEGS);
            }
        });
        addExercise.add(addLegExercise);
        addExerciseSubmenuContinued(addExercise);
    }

    //MODIFIES: this
    //EFFECTS: adds items to addClothing submenu
    private void addExerciseSubmenuContinued(JMenu addExercise) {
        JMenuItem addShoulderExercise = new JMenuItem(new AbstractAction("Shoulders") {
            @Override
            public void actionPerformed(ActionEvent e) {
                addInputExercise(ExerciseType.SHOULDERS);
            }
        });
        addExercise.add(addShoulderExercise);
        JMenuItem addBackExercise = new JMenuItem(new AbstractAction("Back") {
            @Override
            public void actionPerformed(ActionEvent e) {
                addInputExercise(ExerciseType.BACK);
            }
        });
        addExercise.add(addBackExercise);
    }

    //MODIFIES: this
    //EFFECTS: adds a dropdown menu to workouts
    private void workoutMenu() {
//        JMenu addWorkout = new JMenu(new AbstractAction("Add Workout") {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                addWorkout();
//            }
//        });
//        workouts.add(addWorkout);
        JMenuItem mondayWorkout = new JMenuItem(new AbstractAction("Monday") {
            @Override
            public void actionPerformed(ActionEvent e) {
                workoutDay("Monday");
            }
        });
        workouts.add(mondayWorkout);
        JMenuItem tuesdayWorkout = new JMenuItem(new AbstractAction("Tuesday") {
            @Override
            public void actionPerformed(ActionEvent e) {
                workoutDay("Tuesday");
            }
        });
        workouts.add(tuesdayWorkout);
        workoutMenuContinued();
    }

    //MODIFIES: this
    //EFFECTS: adds dropdown menu to workouts
    private void workoutMenuContinued() {
        JMenuItem wednesdayWorkout = new JMenuItem(new AbstractAction("Wednesday") {
            @Override
            public void actionPerformed(ActionEvent e) {
                workoutDay("Wednesday");
            }
        });
        workouts.add(wednesdayWorkout);
        JMenuItem thursdayWorkout = new JMenuItem(new AbstractAction("Thursday") {
            @Override
            public void actionPerformed(ActionEvent e) {
                workoutDay("Thursday");
            }
        });
        workouts.add(thursdayWorkout);
    }

    //MODIFIES: this
    //EFFECTS: creates a new label and workout list for each workout day
    private void workoutDay(String weekday) {
        eastLabelPanel.remove(workoutDayLabel);
        switch (weekday) {
            case "Monday":
                createExerciseList(myWorkout.mondayWorkout());
                break;
            case "Tuesday":
                createExerciseList(myWorkout.tuesdayWorkout());
                break;
            case "Wednesday":
                createExerciseList(myWorkout.wednesdayWorkout());
                break;
            case "Thursday":
                createExerciseList(myWorkout.thursdayWorkout());
                break;
        }
    }

//    private void addWorkout() {
//
//    }


    //https://stackoverflow.com/questions/11494222/how-to-handle-cancel-button-in-joptionpane/11494262
    //MODIFIES: this
    //EFFECTS: Edits the exercise if found by providing an edit menu for the inputted exercise
    private void editExercise() {
        String exerciseName = (String) JOptionPane.showInputDialog(WorkoutManagerProgram.this,
                "What is the name of the exercise you want to edit?", "Edit Exercise",
                JOptionPane.PLAIN_MESSAGE, EDIT, null, "");
        if (exerciseName != null) {
            exerciseName = exerciseName.toUpperCase();
            if (null != myWorkout.findExercise(exerciseName)) {
                Exercise exercise = myWorkout.findExercise(exerciseName);
                editMenu(exercise);
            } else {
                errorMessage("Exercise of name " + exerciseName + " was not found.");
            }
        } else {
            createExerciseList(myWorkout.getAllExercises());
        }
    }

    //https://stackoverflow.com/questions/33961793/custom-icon-joptionpane-showinputdialog
    //MODIFIES: this
    //EFFECTS: removes the exercise item selected
    private void removeExercise() {
        String exerciseName = (String) JOptionPane.showInputDialog(WorkoutManagerProgram.this,
                "What is the name of the Exercise you want to remove?", "Remove Exercise",
                JOptionPane.PLAIN_MESSAGE, REMOVE, null, "");
        if (exerciseName != null) {
            exerciseName = exerciseName.toUpperCase();
            if (myWorkout.removeExercise(exerciseName)) {
                informationMessage(exerciseName + " was successfully removed from your workouts.",
                        "Removed");
            } else {
                errorMessage("The exercise with inputted name " + exerciseName + " was not found in your workouts");
            }
            updateExerciseList();
        } else {
            createExerciseList(myWorkout.getAllExercises());
        }
    }

    //https://stackoverflow.com/questions/33961793/custom-icon-joptionpane-showinputdialog
    //EFFECTS: finds and opens exercise if it exists
    private void findExercise() {
        String exerciseName = (String) JOptionPane.showInputDialog(WorkoutManagerProgram.this,
                "What is the name of the exercise you want to find?", "Find Exercise",
                JOptionPane.PLAIN_MESSAGE, FIND, null, "");
        if (exerciseName != null) {
            exerciseName = exerciseName.toUpperCase();
            if (null != myWorkout.findExercise(exerciseName)) {
                Exercise exercise = myWorkout.findExercise(exerciseName);
                displayPopupExercise(exercise);
            } else {
                errorMessage("The exercise with inputted name " + exerciseName + " was not found in Workouts.");
            }
        } else {
            createExerciseList(myWorkout.getAllExercises());
        }
    }

    //https://stackoverflow.com/questions/6975736/java-joptionpane-showmessagedialog-custom-icon-problem
    //EFFECTS: Displays an exercise in a pop up with details and a picture
    private void displayPopupExercise(Exercise exercise) {
        ImageIcon icon = getMuscleTypeImage(exercise);
        JOptionPane.showMessageDialog(WorkoutManagerProgram.this, displayExerciseInfo(exercise),
                exercise.getName(), JOptionPane.INFORMATION_MESSAGE, icon);
    }

    //https://stackoverflow.com/questions/11494222/how-to-handle-cancel-button-in-joptionpane
    //https://www.roseindia.net/tutorial/java/swing/comboinjoptionpane.html
    //MODIFIES: this
    //EFFECTS: completes the editing and selects field & new name for field
    private void editMenu(Exercise exercise) {
        String [] editOptions = new String[] {"Name", "Weight","Type","Sets","Reps"};
        String editSelection = (String) JOptionPane.showInputDialog(WorkoutManagerProgram.this,
                "This Exercise currently has the following statistics and details:\n"
                    + displayExerciseInfo(exercise) + "\n\nWhat would you like to edit?", "Details & Stats",
                    JOptionPane.INFORMATION_MESSAGE, EDIT, editOptions,"Name");

        String changedValue = (String) JOptionPane.showInputDialog(WorkoutManagerProgram.this,
                "Input the updated value of the exercise " + editSelection + "!", "Details & Stats",
                     JOptionPane.INFORMATION_MESSAGE, EDIT, null,null);

        if (changedValue != null) {
            editMenuSelections(editSelection, exercise, changedValue);
        } else {
            editExercise();
        }
    }

    //EFFECTS: helper for edit menu to decrease method length, and ease of understanding, if the selection is equal
    private void editMenuSelections(String selection, Exercise ex, String changedValue) {
        switch (selection) {
            case "Name":
                ex.setName(changedValue);
                break;
            case "Weight":
                ex.setWeight(Integer.parseInt(changedValue));
                break;
            case "Type":
                ex.setType(ExerciseType.valueOf(changedValue));
                break;
            case "Sets":
                ex.setSets(Integer.parseInt(changedValue));
                break;
            case "Reps":
                ex.setReps(Integer.parseInt(changedValue));
        }
        informationMessage("The " + selection + " is now " + changedValue + "!", "Item Edited");
        updateExerciseList();
    }

    //EFFECTS: prints out the information of the selected exercise
    private String displayExerciseInfo(Exercise exercise) {
        return "Name: " + exercise.getName() + "\nWeight: " + exercise.getWeight()
            + "\nType: " + exercise.getType() + "\nSets: " + exercise.getSets()
            + "\nReps: " + exercise.getReps();
    }

    //EFFECTS: adds the inputted exercise to the list of exercises
    private void addInputExercise(ExerciseType type) {
        String name = collectName(type.toString());
        Integer weight = collectIntWeight("weight");
        Integer sets = collectIntSetsReps("sets");
        Integer reps = collectIntSetsReps("reps");

        Exercise addedExercise = new Exercise(name, type);
        addedExercise.setWeight(weight);
        addedExercise.setSets(sets);
        addedExercise.setReps(reps);

        try {
            myWorkout.addExerciseFromButton(addedExercise);
            updateExerciseList();
        } catch (InWorkoutException e) {
            System.out.println("This exercise already exists...");
        }
    }

    //EFFECTS: returns the int for the reps and sets attribute given in a string
    private int collectIntSetsReps(String attribute) {
        return Integer.parseInt(JOptionPane.showInputDialog(WorkoutManagerProgram.this,
            "How many " + attribute + " are you going to do for this exercise?", "Sets",
            JOptionPane.PLAIN_MESSAGE, WATER, null, "").toString());
    }

    //EFFECTS: returns the int for the weight attribute given
    private int collectIntWeight(String attribute) {
        return Integer.parseInt(JOptionPane.showInputDialog(WorkoutManagerProgram.this,
            "What is the " + attribute + " of this exercise?", "Weight",
            JOptionPane.PLAIN_MESSAGE, WATER, null, "").toString());
    }


    //EFFECTS: returns the name for a new clothing item
    private String collectName(String type) {
        return JOptionPane.showInputDialog(WorkoutManagerProgram.this,
            "What is the name of this " + type + " exercise ?", null).toUpperCase();
    }

    //MODIFIES: this
    //EFFECTS: updates the current clothing list to display any changes made
    private void updateExerciseList() {
        removeElements();
        createExerciseList(myWorkout.getAllExercises());
    }


    //MODIFIES: this
    //EFFECTS: removes the correct list of either clothing or outfit
    private void whatToRemove() {
        remove(scroller);
        if (isExercisesDisplayed) {
            isExercisesDisplayed = false;
        }
        buttonPane.remove(openButton);
        remove(buttonPane);
    }

    //MODIFIES: this
    //EFFECTS: removes the correct list of either exercise or workout
    private void removeWorkoutElements() {
        remove(scroller2);
        if (isWorkoutsDisplayed) {
            isWorkoutsDisplayed = false;
        }
        buttonPane.remove(openButton);
        remove(buttonPane);
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
                loadWorkout();
            }
        });
        file.add(load);
    }

    //EFFECTS: saves the workout to file
    private void saveWorkout() {
        Writer writer;
        try {
            writer = new Writer(WORKOUT_FILE);
            writer.write(myWorkout);
            writer.close();
            informationMessage("Successfully saved file... " + "\nFile name: "
                    +  WORKOUT_FILE, "File Saved");
        } catch (IOException e) {
            errorMessage("Unable to save wardrobe to " + WORKOUT_FILE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workout from WORKOUT_FILE, if that file exists;
    // otherwise initializes workout with default values
    private void loadWorkout() {
        Reader reader;
        try {
            reader = new Reader(WORKOUT_FILE);
            myWorkout = reader.read();
            informationMessage("Successfully loaded a wardrobe from " + WORKOUT_FILE, "File Loaded");
        } catch (Exception e) {
            errorMessage("Unable to load a wardrobe from " + WORKOUT_FILE);
        }
    }

    //EFFECTS: message layout that displays information for multiple scenarios using showMessageDialog
    private void informationMessage(String message, String title) {
        JOptionPane.showMessageDialog(WorkoutManagerProgram.this, message, title,
                JOptionPane.INFORMATION_MESSAGE);
    }

    //EFFECTS: message layout that displays error message for multiple scenarios using showMessageDialog
    private void errorMessage(String message) {
        JOptionPane.showMessageDialog(WorkoutManagerProgram.this, message, "Error",
                JOptionPane.ERROR_MESSAGE);
    }


    public static void main(String [] args) {
        EventQueue.invokeLater(() -> {
            WorkoutManagerProgram wm = new WorkoutManagerProgram();
            wm.setVisible(true);
        });
    }
}
