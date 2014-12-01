package com.example.john.debtmania;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
/**
 * Created by mollyshrestha on 11/18/14.
 */
@DatabaseTable(tableName = "dbamount")
public class dbAmount {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(canBeNull = false)
    private String Name;
    @DatabaseField(format = "yyyyMMdd_HHmmss" )
    private String Date;
    @DatabaseField(defaultValue = "Unknown")
    private String Description;
    @DatabaseField (defaultValue = "0.0")
    private float amount;

    public dbAmount(){}



    public String toSting()
    {
        return (Name + ", " + Description + ", " + amount);
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString()
    {
        return ("Date: " +  Date + ", Name: " + Name + ", Description: " + Description +
                ", Amount: $" + amount);
    }
}
