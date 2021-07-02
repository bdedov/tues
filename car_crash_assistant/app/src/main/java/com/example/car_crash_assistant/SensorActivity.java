package com.example.car_crash_assistant;

public class SensorActivity
{
    float max_acceleration, max_angular_acceleration;

    public SensorActivity(float max_acceleration, float max_angular_acceleration)
    {
        this.max_acceleration = max_acceleration;

        this.max_angular_acceleration = max_angular_acceleration;
    }

    public boolean check_for_issues()
    {
        return true; // TODO!
    }
}