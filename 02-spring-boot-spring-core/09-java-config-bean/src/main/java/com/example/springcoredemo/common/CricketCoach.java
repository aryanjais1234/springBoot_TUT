package com.example.springcoredemo.common;

import org.springframework.stereotype.Component;

@Component
public class CricketCoach implements Coach {
    public CricketCoach(){
        System.out.println("In constructor: " + getClass().getSimpleName());
    }

    // define our init method
   @Override
    public String getDailyWorkout() {
        return "Practice fast bowling for 15 minutes:-";
    }
}
