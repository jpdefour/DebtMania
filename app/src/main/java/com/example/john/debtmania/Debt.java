package com.example.john.debtmania;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
/**
 * Created by mollyshrestha on 11/18/14.
 */
@DatabaseTable(tableName = "Debt")
public class Debt {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(canBeNull = false)
    private String name;
    @DatabaseField(format = "yyyyMMdd_HHmmss" )
    private String date;
    @DatabaseField(defaultValue = "Unknown")
    private String description;
    @DatabaseField (defaultValue = "0.0")
    private float amount;
    @DatabaseField(canBeNull = false)
    private String number;

    public Debt(){}



    public String toSting()
    {
        return (name + ", " + description + ", " + amount);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setNumber(String number) { this.number = number;}
    public String getNumber() { return this.number; }

    public String toString()
    {
        return ("date: " + date + ", name: " + name + ", description: " + description +
                ", Amount: $" + amount);
    }

}
