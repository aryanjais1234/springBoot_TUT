package com.example.springcoredemo.common;

public class SwimCoach implements  Coach{

    public SwimCoach() {
        System.out.println("In constructor: "+getClass().getSimpleName());
    }
    public String getDailyWorkout() {
        return "Swim 1000 meters as a warm up";
    }
}
