package com.example.john.debtmania;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
/**
 * Created by mollyshrestha on 11/18/14.
 */
@DatabaseTable(tableName = "amountown")
public class dbAmount {
    @DatabaseField(generatedId = true)
    private String Name;
    @DatabaseField(format = "##/##/##" )
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
}
