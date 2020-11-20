package ui;

import exceptions.InWorkoutException;
import model.Exercise;
import model.ExerciseType;
import model.Workout;
import persistence.Reader;
import persistence.Writer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorkoutManagerProgram extends JFrame {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    private static final ImageIcon CHEST = scaleImageIcon("./data/images/chest_image.png");
    private static final ImageIcon ARMS = scaleImageIcon("./data/images/arm_image.png");
    private static final ImageIcon LEGS = scaleImageIcon("./data//images/leg_image.png");
    private static final ImageIcon SHOULDERS = scaleImageIcon("./data/images/shoulder_image.png");
    private static final ImageIcon BACK = scaleImageIcon("./data/images/back_image.png");
    private static final ImageIcon EDIT = scaleImageIcon("./data/images/edit_image.png");
    private static final ImageIcon LOADING = scaleImageIcon("./data/images/load_image.png");
    private static final ImageIcon FIND = scaleImageIcon("./data/images/find_image.png");
    private static final ImageIcon REMOVE = scaleImageIcon("./data/images/eraser_image.png");
    private static final ImageIcon WATER = scaleImageIcon("./data/images/water_image.png");
    private static final String ADD_ARM_EX = "./data/sounds/arm_sound.wav";
    private static final String ADD_BACK_EX = "./data/sounds/back_sound.wav";
    private static final String ADD_CHEST_EX = "./data/sounds/chest_sound.wav";
    private static final String ADD_LEG_EX = "./data/sounds/leg_sound.wav";
    private static final String ADD_SHOULDER_EX = "./data/sounds/shoulder_sound.wav";
    private static final String EDIT_SOUND = "./data/sounds/edit_sound.wav";

    private JMenu exercises;
    private JMenu workouts;
    private JMenu file;
    private static final String WORKOUT_FILE = "./data/workout.json";
    private Workout myWorkout;
    private List<Exercise> exerciseList;
    private List<Exercise> workoutList;
    private Container mainPane;
    private JScrollPane allExerciseScroller;
    private JScrollPane workoutScroller;
    private JList<String> exerciseStringList;
    private JList<String> workoutStringList;
    private boolean isExercisesDisplayed;
    private boolean isWorkoutsDisplayed;
    private JPanel buttonPane;
    private JButton openButton;
    private GridBagConstraints gbcExercises = new GridBagConstraints();
    private GridBagConstraints gbcWorkouts = new GridBagConstraints();
    private GridBagConstraints gbcButton = new GridBagConstraints();
    private GridBagConstraints gbcLabelEast = new GridBagConstraints();
    private GridBagConstraints gbcLabelWest = new GridBagConstraints();
    private JLabel allExercisesLabel;
    private JLabel workoutDayLabel = new JLabel("Monday Workout");
    private JPanel westLabelPanel;
    private JPanel eastLabelPanel = new JPanel();



    public WorkoutManagerProgram() {
        super("Workout Manager");
        isExercisesDisplayed = false;
        initializeGraphics();
    }

    //https://docs.oracle.com/javase/tutorial/uiswing/examples/components/SplitPaneDemo2Project/src/components/SplitPaneDemo2.java
    //https://stackoverflow.com/questions/2361510/how-to-save-application-options-before-exit
    // MODIFIES: this
    // EFFECTS:  draws the JFrame window where this WorkoutManager will operate, and populates the tools to be used
    //           to manipulate this GUI
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        createMenus();
        initializeData();
        mainPane = getContentPane();
        mainPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        mainPane.setLayout(new GridBagLayout());
        createExerciseList(myWorkout.getAllExercises());
        createWorkoutList(myWorkout.dayWorkout("Chest & Arms"));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitGUI();
            }
        });
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
        JMenu editWorkout = new JMenu("Edit Workout");
        workoutMenu(editWorkout);
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

    //MODIFIES: this
    //EFFECTS: creates the current exercise list and adds to main frame
    public void createWorkoutList(List<Exercise> addToSideFrame) {
        DefaultListModel<String> listModelWorkout = new DefaultListModel<>();
        workoutList = new ArrayList<>();
        for (Exercise e : addToSideFrame) {
            listModelWorkout.addElement(e.getType() + " - Name: " + e.getName() + ", Weight: " + e.getWeight()
                    + " , Sets x Reps: " + e.getSets() + "x" + e.getReps());
            workoutList.add(e);
        }
        configureWorkoutList(listModelWorkout);
    }

    //MODIFIES: this
    //EFFECTS: configures the list in the pane
    private void configureList(ListModel listModel) {
        allExerciseScroller = new JScrollPane();
        exerciseStringList = new JList<>(listModel);
        exerciseStringList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        exerciseStringList.setFont(new Font("Geneva", Font.PLAIN, 21));
        allExerciseScroller.setViewportView(exerciseStringList);
        exerciseStringList.setLayoutOrientation(JList.VERTICAL);
        //GridBagLayoutConfiguration Coordinates
        allExercisesGBC();
        mainPane.add(allExerciseScroller, gbcExercises);
        addExerciseHeadingPanel();
        addButtonViewExercise();
        setVisible(true);
    }

    //helper to establish constraints for the all exercises window and button
    private void allExercisesGBC() {
        gbcExercises.gridx = 0;
        gbcExercises.weightx = 0.5;
        gbcExercises.weighty = 0.5;
        gbcExercises.fill = GridBagConstraints.BOTH;
        gbcExercises.gridy = 1;
        gbcExercises.ipady = 40;
        gbcButton.anchor = GridBagConstraints.PAGE_END;
        gbcButton.gridx = 0;
    }

    //https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/
    //  examples/layout/GridBagLayoutDemoProject/src/layout/GridBagLayoutDemo.java
    //EFFECTS: configures the list in a second pane
    private void configureWorkoutList(ListModel listModel) {
        workoutScroller = new JScrollPane();
        workoutStringList = new JList<>(listModel);
        workoutStringList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        workoutStringList.setFont(new Font("Geneva", Font.PLAIN, 21));
        workoutScroller.setViewportView(workoutStringList);
        workoutScroller.revalidate();
        workoutStringList.setLayoutOrientation(JList.VERTICAL);
        //GridBagLayoutConfiguration Coordinates
        workoutListGBC();
        mainPane.add(workoutScroller, gbcWorkouts);
        addHeadingPanel();
        addButtonViewWorkoutExercises();
        setVisible(true);
    }

    //EFFECTS: helper for workoutListGBC and button
    private void workoutListGBC() {
        gbcWorkouts.gridx = 1;
        gbcWorkouts.weightx = 0.4;
        gbcWorkouts.fill = GridBagConstraints.BOTH;
        gbcWorkouts.gridy = 1;
        gbcButton.anchor = GridBagConstraints.PAGE_END;
        gbcButton.gridx = 1;
    }

    //https://stackoverflow.com/questions/8608902/the-correct-way-to-swap-a-component-in-java
    //MODIFIES: this
    //EFFECTS: Heading panel for workout days
    private void addHeadingPanel() {
        workoutDayLabel.setFont(new Font("System", Font.ITALIC, 24));
        eastLabelPanel.setLayout(new BoxLayout(eastLabelPanel, BoxLayout.LINE_AXIS));
        eastLabelPanel.add(workoutDayLabel);
        eastLabelPanel.repaint();
        gbcLabelEast.gridx = 1;
        gbcLabelEast.gridy = 0;
        gbcLabelEast.anchor = GridBagConstraints.NORTH;
        mainPane.add(eastLabelPanel, gbcLabelEast);
        eastLabelPanel.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: Heading panel for workout days
    private void addExerciseHeadingPanel() {
        allExercisesLabel = new JLabel("All Exercises");
        allExercisesLabel.setFont(new Font("System", Font.ITALIC, 24));
        westLabelPanel = new JPanel();
        westLabelPanel.setLayout(new BoxLayout(westLabelPanel, BoxLayout.LINE_AXIS));
        westLabelPanel.add(allExercisesLabel);
        gbcLabelWest.gridx = 0;
        gbcLabelWest.gridy = 0;
        gbcLabelWest.anchor = GridBagConstraints.NORTH;
        mainPane.add(westLabelPanel, gbcLabelWest);
        westLabelPanel.setVisible(true);
    }

    //https://docs.oracle.com/javase/tutorial/displayCode.html?code=
    //https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
    //https://stackoverflow.com/questions/5911565/how-to-add-multiple-actionlisteners-for-multiple-buttons-in-java-swing/5911621
    //MODIFIES: this
    //EFFECTS: adds a button to bottom panel that opens the selected exercise item
    private void addButtonViewExercise() {
        openButton = new JButton(new AbstractAction("View Exercise") {
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
        gbcButton.gridy = 2;
        mainPane.add(buttonPane, gbcButton);
        openButton.setVisible(true);
        buttonPane.setVisible(true);
    }


    //MODIFIES: this
    //EFFECTS: adds a button to bottom panel that opens the selected exercise item for individual workout days
    private void addButtonViewWorkoutExercises() {
        openButton = new JButton(new AbstractAction("View Exercise") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = workoutStringList.getSelectedIndex();
                Exercise selectedItem = workoutList.get(index);
                displayPopupExercise(selectedItem);
            }
        });
        openButton.setFont(new Font("System", Font.PLAIN, 20));
        buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(openButton);
        mainPane.add(buttonPane, gbcButton);
        openButton.setVisible(true);
        buttonPane.setVisible(true);
    }

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
    //EFFECTS: adds items to addExercise submenu
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
    //EFFECTS: adds items to addExercise submenu
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
    //EFFECTS: plays a specific sound for each given exerciseType
    private void addExerciseSounds(ExerciseType exerciseType) {
        switch (exerciseType) {
            case SHOULDERS:
                playSound(ADD_SHOULDER_EX);
                break;
            case ARMS:
                playSound(ADD_ARM_EX);
                break;
            case LEGS:
                playSound(ADD_LEG_EX);
                break;
            case BACK:
                playSound(ADD_BACK_EX);
                break;
            case CHEST:
                playSound(ADD_CHEST_EX);
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: adds a dropdown menu to workouts
    private void workoutMenu(JMenu editWorkout) {
        workouts.add(editWorkout);
        workoutOptionsMenu(editWorkout);
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
        workoutMenuMoreContinued();
    }

    //MODIFIES: this
    //EFFECTS: continues dropdown menu to workouts
    private void workoutMenuMoreContinued() {
        JMenuItem fridayWorkout = new JMenuItem(new AbstractAction("Friday") {
            @Override
            public void actionPerformed(ActionEvent e) {
                workoutDay("Friday");
            }
        });
        workouts.add(fridayWorkout);
        JMenuItem saturdayWorkout = new JMenuItem(new AbstractAction("Saturday") {
            @Override
            public void actionPerformed(ActionEvent e) {
                workoutDay("Saturday");
            }
        });
        workouts.add(saturdayWorkout);
        JMenuItem sundayWorkout = new JMenuItem(new AbstractAction("Sunday") {
            @Override
            public void actionPerformed(ActionEvent e) {
                workoutDay("Sunday");
            }
        });
        workouts.add(sundayWorkout);
    }

    //MODIFIES: this
    //EFFECTS: adds submenu to Edit Workout
    private void workoutOptionsMenu(JMenu editWorkout) {
        JMenuItem editWorkoutType = new JMenuItem(new AbstractAction("Change Workout Muscle Group") {
            @Override
            public void actionPerformed(ActionEvent e) {
                pickDayEdit();
            }
        });
        editWorkout.add(editWorkoutType);
    }

    //MODIFIES: this
    //EFFECTS: creates a pop up menu that allows you to pick which day workout you wish to edit
    private void pickDayEdit() {
        String daySelection = (String) JOptionPane.showInputDialog(WorkoutManagerProgram.this,
                "\n\nWhat workout day would you like to edit?", "Pick Day To Edit",
                JOptionPane.INFORMATION_MESSAGE, EDIT, myWorkout.getWorkoutDays().toArray(), "Monday");
        if (daySelection != null) {
            changeWorkoutType(daySelection);
        } else {
            informationMessage("Edit Window closed","Cancelled");
        }
    }

    //EFFECTS: displays the muscle group for the given day
    private String displayDayMuscleGroup(String daySelection) {
        return myWorkout.getWorkoutPlanner().get(daySelection);
    }

    //MODIFIES:this, Workout
    //EFFECTS: Creates a menu with a dropdown to select what type of workout you want to change this day to
    private void changeWorkoutType(String daySelection) {
        String [] typeOptions = new String[] {"Chest & Arms", "Shoulder & Arms", "Legs", "Chest & Back", "Rest"};
        String editSelection = (String) JOptionPane.showInputDialog(WorkoutManagerProgram.this,
                "This day currently has the following Muscle Group:\n"
                + displayDayMuscleGroup(daySelection) + "\n\nWhat would you like to change it to?", "Details",
                 JOptionPane.INFORMATION_MESSAGE, EDIT, typeOptions,"Chest & Arms");

        if (editSelection != null) {
            myWorkout.getWorkoutPlanner().replace(daySelection, editSelection);
            workoutDay(daySelection);
        } else {
            pickDayEdit();
        }
    }

    //MODIFIES: this
    //EFFECTS: creates a new label and workout list for each workout day
    private void workoutDay(String weekday) {
        eastLabelPanel.remove(workoutDayLabel);
        String selectedDayGroup = myWorkout.getWorkoutPlanner().get(weekday);
        switch (weekday) {
            case "Monday":
                workoutDayLabel = new JLabel("Monday Workout");
                removeWorkoutElements();
                createWorkoutList(myWorkout.dayWorkout(selectedDayGroup));
                break;
            case "Tuesday":
                workoutDayLabel = new JLabel("Tuesday Workout");
                removeWorkoutElements();
                createWorkoutList(myWorkout.dayWorkout(selectedDayGroup));
                break;
            case "Wednesday":
                workoutDayLabel = new JLabel("Wednesday Workout");
                removeWorkoutElements();
                createWorkoutList(myWorkout.dayWorkout(selectedDayGroup));
                break;
        }
        workoutDayContinued(weekday);
    }

    //MODIFIES: this
    //EFFECTS: creates a new label and workout list for each workout day continued
    private void workoutDayContinued(String weekday) {
        String selectedDayGroup = myWorkout.getWorkoutPlanner().get(weekday);
        switch (weekday) {
            case "Thursday":
                workoutDayLabel = new JLabel("Thursday Workout");
                removeWorkoutElements();
                createWorkoutList(myWorkout.dayWorkout(selectedDayGroup));
                break;
            case "Friday":
                workoutDayLabel = new JLabel("Friday Workout");
                removeWorkoutElements();
                createWorkoutList(myWorkout.dayWorkout(selectedDayGroup));
                break;
            case "Saturday":
                workoutDayLabel = new JLabel("Saturday Workout");
                removeWorkoutElements();
                createWorkoutList(myWorkout.dayWorkout(selectedDayGroup));
                break;
            case "Sunday":
                workoutDayLabel = new JLabel("Sunday Workout");
                removeWorkoutElements();
                createWorkoutList(myWorkout.dayWorkout(selectedDayGroup));
        }
    }

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

    //MODIFIES: Exercise, this
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
        playSound(EDIT_SOUND);
    }

    //EFFECTS: prints out the information of the selected exercise
    private String displayExerciseInfo(Exercise exercise) {
        return "Name: " + exercise.getName() + "\nWeight: " + exercise.getWeight()
            + "\nType: " + exercise.getType() + "\nSets: " + exercise.getSets()
            + "\nReps: " + exercise.getReps();
    }

    //MODIFIES: this
    //EFFECTS: adds the inputted exercise to the list of exercises
    private void addInputExercise(ExerciseType type) {
        String name = collectName(type.toString());
        Integer weight = collectIntWeight("weight");
        Integer sets = collectIntSetsReps("sets");
        Integer reps = collectIntSetsReps("reps");

        Exercise addedExercise = new Exercise(name, type, weight,sets,reps);

        try {
            myWorkout.addExerciseFromButton(addedExercise);
            updateExerciseList();
            addExerciseSounds(type);
        } catch (InWorkoutException e) {
            errorMessage("This exercise already exists...");
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


    //EFFECTS: returns the name for a new exercise
    private String collectName(String type) {
        return JOptionPane.showInputDialog(WorkoutManagerProgram.this,
            "What is the name of this " + type + " exercise ?", null).toUpperCase();
    }

    //MODIFIES: this
    //EFFECTS: updates the current exercise list to display any changes made
    private void updateExerciseList() {
        removeElements();
        createExerciseList(myWorkout.getAllExercises());
    }


    //EFFECTS: gets the correct image icon for type
    private ImageIcon getMuscleTypeImage(Exercise exercise) {
        ExerciseType type = exercise.getType();
        switch (type) {
            case CHEST:
                return CHEST;
            case ARMS:
                return ARMS;
            case BACK:
                return BACK;
            case LEGS:
                return LEGS;
        }
        return SHOULDERS;
    }

    //MODIFIES: this
    //EFFECTS: removes the correct list of either exercise or workout
    private void removeElements() {
        remove(allExerciseScroller);
        if (isExercisesDisplayed) {
            isExercisesDisplayed = false;
        }
        buttonPane.remove(openButton);
        remove(buttonPane);
    }

    //MODIFIES: this
    //EFFECTS: removes the correct list of either exercise or workout
    private void removeWorkoutElements() {
        remove(workoutScroller);
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

    //MODIFIES: this
    //EFFECTS: saves the workout to file
    private void saveWorkout() {
        Writer writer;
        try {
            writer = new Writer(WORKOUT_FILE);
            writer.open();
            writer.write(myWorkout);
            writer.close();
            informationMessage("Successfully saved file... " + "\nFile name: "
                    +  WORKOUT_FILE, "File Saved");
        } catch (IOException e) {
            errorMessage("Unable to save workout to " + WORKOUT_FILE);
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
            informationMessage("Successfully loaded a workout... " + "\nFile Name: "
                    + WORKOUT_FILE, "Load File");
        } catch (Exception e) {
            errorMessage("Unable to load a workout from " + WORKOUT_FILE);
        }
    }

    //EFFECTS: message layout that displays information for multiple scenarios using showMessageDialog
    private void informationMessage(String message, String title) {
        JOptionPane.showMessageDialog(WorkoutManagerProgram.this, message, title,
                JOptionPane.INFORMATION_MESSAGE, LOADING);
    }

    //EFFECTS: message layout that displays error message for multiple scenarios using showMessageDialog
    private void errorMessage(String message) {
        JOptionPane.showMessageDialog(WorkoutManagerProgram.this, message, "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    //https://stackoverflow.com/questions/15526255/best-way-to-get-sound-on-button-press-for-a-java-calculator
    //MODIFIES: this
    //EFFECTS: plays the sound with given file path
    private void playSound(String url) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(url));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            errorMessage("This sound cannot be played or it could not be found");
        }
    }

    //https://stackoverflow.com/questions/16343098/resize-a-picture-to-fit-a-jlabel/16345968
    //https://www.codota.com/code/java/methods/java.awt.Image/getScaledInstance
    //https://docs.oracle.com/javase/7/docs/api/java/awt/Image.html
    //MODIFIES: this
    //EFFECTS: scales the image at the file location to be of desired size
    private static ImageIcon scaleImageIcon(String fileLocation) {
        ImageIcon imageIcon = new ImageIcon(fileLocation);
        Image image = imageIcon.getImage();
        Image scaledInstance = image.getScaledInstance(150,150,Image.SCALE_SMOOTH);
        return new ImageIcon(scaledInstance);
    }

    //https://stackoverflow.com/questions/16372241/run-function-on-jframe-close
    //MODIFIES: this
    //EFFECTS: closes the window and saves the workout to file
    private void exitGUI() {
        saveWorkout();
        dispose();
        System.exit(0);
    }

    //https://docs.oracle.com/javase/7/docs/api/java/awt/EventQueue.html#invokeLater(java.lang.Runnable)
    public static void main(String [] args) {
        EventQueue.invokeLater(() -> {
            WorkoutManagerProgram wm = new WorkoutManagerProgram();
            wm.setVisible(true);
        });
    }
}
