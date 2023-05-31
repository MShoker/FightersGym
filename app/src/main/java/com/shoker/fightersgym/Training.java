package com.shoker.fightersgym;

public class Training {
    private int id;
    private String name;
    private String date;
    private String time;
    private int numberOfExercises;
    private String exercisesId;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setNumberOfExercises(int numberOfExercises) {
        this.numberOfExercises = numberOfExercises;
    }

    public void setExercisesId(String exercisesId) {
        this.exercisesId = exercisesId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getNumberOfExercises() {
        return numberOfExercises;
    }

    public String getExercisesId() {
        return exercisesId;
    }
}
