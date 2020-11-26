# My Personal Project

## Workout Program/ Progress Manager

## Questions
- **What will the application do?**

    This application will keep track of different muscle groups and workouts for those muscle groups. 
    For example, if you find a workout program online that you like, you can easily
    input the workouts into this application, and categorize which muscle group it is. When you decide to 
    workout, you will have the option to click which day of the week
    it is, and it will display which workout day it is. Most workouts are split into muscle groups, 
    so if today is Chest and Back day, it will display three of four workouts for each muscle group,
     as well as your current progress in that workout.  

- **Who will use it?**

    Anyone who workouts will greatly appreciate this application. Most of the time, I have to log my workouts
    on excel, but having a GUI to interact with and have it display a lot of 
    information in an easily digestable format would be greatly beneficial. The additional 
    functionality would be the randomization of workouts for each muscle group. 
    
- **Why is this project of interest to you?**
    
    This project is of interest to me because I am a Gym Rat, and being able to create
    an application that I would use would highly interest me. I also do not like doing the same 
    workouts every time I go to the gym, so having different workouts displayed to me would 
    save me the time of looking up workouts to do. 

## User Stories

- As a user, I want to be able to add workouts to my Workout Log
- As a user, I want to be able to view workouts by muscle groups in my Workout Log
- As a user, I want to be able to create different workouts for different days
- As a user, I want to be able to select a certain muscle group and add a workout
- As a user, I want to be able to select a specific exercise and add statistics to it
- As a user, I want to be able to save my exercise lists to file
- As a user, I want to be able to load my exercise lists from file
- As a user, when I start the application I want to be given the option to load from file

#### GUI Specific User Stories: 
- As a user, when I start the interface, I want to have the previous file loaded
- As a user, I want to be able to scroll through and view all the exercises on the main panel
- As a user, I want to be able to add, update, remove and find individual exercises to and within the Workout class
- As a user, I want to be able to view workouts for every day of the week specified as a workout day
- As a user, I want to be able to view multiple different windows with information displayed on button press
- As a user, I want to be able to configure workout days to specific muscle groups
- As a user, I want my data to be saved everytime we exit the GUI

###Phase 4: Task 2
- I have chosen to:
 Test and design a class in your model package that is robust. 
 You must have at least one method that throws a checked exception.  
 You must have one test for the case where the exception is expected 
 and another where the exception is not expected.
 -Class: Workout, Exception: InWorkoutException, Method: addExerciseFromButton

###Phase 4: Task 3
- I would most likely refactor the Workout class to allow for the creation of individual workout days, such as adding an Ab day workout, 
or some other muscle group workout other than the weekdays.
Currently, the workout class has weaker cohesion than I would like, as I am storing exercises into lists, as well as creating
different workouts, so it does not strictly follow the single responsibility principle. 
- I am pretty pleased with the coupling between and within classes. There is minimal duplication, and I refactored my workout class
after Phase 3 to remove a lot of the duplication through helper methods to improve readability. However, the workoutManagerProgram 
and the Workout class both have association with Exercise class. I believe that could be refactored to remove that additional
coupling, perhaps access the list of exercises through the Workout class.
- In my WorkoutManagerProgram, there is some duplication, and if I had more time, I would refactor the code to 
allow for easier addition and updates of new functionalities into the GUI.