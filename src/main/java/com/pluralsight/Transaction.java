package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;

    public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
        String timeString = time.format(format);
        this.time = LocalTime.parse(timeString);
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public void printInfo(){
        System.out.println(this.getDate() + "|" + this.getTime() + "|" + description + "|" +
                vendor + "|" + amount);
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getVendor() {
        return vendor;
    }

    public double getAmount() {
        return amount;
    }
}
