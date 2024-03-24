package com.skazy.vietnameseSolutioner;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Results {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String calculation;
    private int result;
    private int time;

    protected Results() {
    }

    public Results(String calculation, int result, int time) {
        this.calculation = calculation;
        this.result = result;
        this.time = time;
    }

    @Override
    public String toString() {
        return String.format(
                "Results[id=%d, calculation='%s', result='%d', time='%d']",
                id, calculation, result, time);
    }

    public Long getId() {
        return id;
    }

    public String getCalculation() {
        return calculation;
    }

    public int getResult() {
        return result;
    }

    public int getTime() {
        return time;
    }

    // set calculation
    public void setCalculation(String calculation) {
        this.calculation = calculation;
    }

    // set result
    public void setResult(int result) {
        this.result = result;
    }
}