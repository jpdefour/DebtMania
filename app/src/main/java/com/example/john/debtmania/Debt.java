package com.example.john.debtmania;

/**
 * Created by John on 11/19/2014.
 */
public class Debt {
    private String person;
    private String description;
    private double moneyOwed;

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getMoneyOwed() {
        return moneyOwed;
    }

    public void setMoneyOwed(float moneyOwed) {
        this.moneyOwed = moneyOwed;
    }
}
