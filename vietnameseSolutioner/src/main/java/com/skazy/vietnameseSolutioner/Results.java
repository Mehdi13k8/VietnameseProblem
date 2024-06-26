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
    private boolean isCorrect;

    protected Results() {
    }

    public Results(String calculation, int result, int time, boolean isCorrect) {
        this.calculation = calculation;
        this.result = result;
        this.time = time;
        this.isCorrect = isCorrect;
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

    // set isCorrect
    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    // get isCorrect
    public boolean getIsCorrect() {
        return isCorrect;
    }
}