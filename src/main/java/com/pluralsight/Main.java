package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    static void main() throws IOException {
        //scanner to read user input
        Scanner scanner = new Scanner(System.in);
        ArrayList<Transaction> ledger = new ArrayList<Transaction>();

        loadTransactions(ledger);
//        //code to read and write files
//        FileReader fileReader = new FileReader("transactions.csv");
//        BufferedReader bufferedReader = new BufferedReader(fileReader);
//        FileWriter fileWriter = new FileWriter("transactions.csv");
//        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//        // code to output and format date AND time
//        LocalDateTime date = LocalDateTime.now();
//        LocalDateTime time = LocalDateTime.now();
//        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

        while (true){

            System.out.print("-----Home Screen-----\n\n\t" +
                    "D) Add Deposit\n\t" +
                    "P) Make Payment (Debit)\n\t" +
                    "L) Ledger\n\t" +
                    "X) Exit\n\n" +
                    "Choose an option: ");
            String command = scanner.nextLine();
            switch (command.toUpperCase()) {
                case "D" -> addDeposit(ledger);
                case "P" -> makePayment(ledger);
                case "L" -> ledgerScreen(ledger);
                case "X" -> {
                    System.out.println("Have a nice day!");
                    return;
                }
            }
        }
    }

    public static void addDeposit(ArrayList<Transaction> ledger) {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        String description = Console.promptForString("What is the reason for this deposit? ");
        String vendor = Console.promptForString("Who is making this deposit? ");
        double amount = Console.promptForDouble("What is the amount? ");
        Transaction transaction = new Transaction(date,time,description,vendor,amount);
        ledger.add(transaction);
        try {
            FileWriter fileWriter = new FileWriter("transactions.csv",true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("\n" + transaction.getDate() + "|" + transaction.getTime() + "|"
            + transaction.getDescription() + "|" + transaction.getVendor() + "|"
            + transaction.getAmount());
            bufferedWriter.close();

        } catch (IOException e) {
            System.out.println("ERROR: Can't find the file");
        }
    }

    public static void makePayment(ArrayList<Transaction> ledger) {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        String description = Console.promptForString("What is the reason for this payment? ");
        String vendor = Console.promptForString("Who are you paying? ");
        double amount = Console.promptForDouble("What is the amount? ");
        amount *= -1;
        Transaction transaction = new Transaction(date,time,description,vendor,amount);
        ledger.add(transaction);
        try {
            FileWriter fileWriter = new FileWriter("transactions.csv",true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("\n" + transaction.getDate() + "|" + transaction.getTime() + "|"
                    + transaction.getDescription() + "|" + transaction.getVendor() + "|"
                    + transaction.getAmount());
            bufferedWriter.close();

        } catch (IOException e) {
            System.out.println("ERROR: Can't find the file");
        }
    }

    public static void ledgerScreen(ArrayList<Transaction> ledger){
        Scanner scanner = new Scanner(System.in);
        while (true){

            System.out.print("-----Ledger Screen-----\n\n\t" +
                    "A) All\n\t" +
                    "D) Deposits\n\t" +
                    "P) Payments\n\t" +
                    "R) Reports\n\t" +
                    "H) Home\n\n" +
                    "Choose an option: ");
            String command = scanner.nextLine();
            switch (command.toUpperCase()) {
                case "A" -> allEntries(ledger);
                case "D" -> deposits(ledger);
                case "P" -> payments(ledger);
//                case "R" -> reports(ledger);
                case "H" -> {
                    System.out.println("Going back to Home Screen\n");
                    return;
                }
            }
        }
    }

    public static void allEntries(ArrayList<Transaction> ledger){
        ArrayList<Transaction> newestLedger = new ArrayList<Transaction>();
        newestLedger = ledger;
        Collections.reverse(newestLedger);
        for (Transaction transaction : newestLedger ){
            transaction.printInfo();
        }
    }

    public static void deposits(ArrayList<Transaction> ledger){
        ArrayList<Transaction> depositLedger = new ArrayList<Transaction>();
        for (Transaction transaction : ledger){
            if (transaction.getAmount() > 0){
                depositLedger.add(transaction);
            }
        }
        Collections.reverse(depositLedger);
        for (Transaction transaction : depositLedger){
            transaction.printInfo();
        }
    }

    public static void payments(ArrayList<Transaction> ledger){
        ArrayList<Transaction> paymentLedger = new ArrayList<Transaction>();
        for (Transaction transaction : ledger){
            if (transaction.getAmount() < 0){
                paymentLedger.add(transaction);
            }
        }
        Collections.reverse(paymentLedger);
        for (Transaction transaction : paymentLedger){
            transaction.printInfo();
        }
    }

    public static void loadTransactions(ArrayList<Transaction> ledger){
        try {
            FileReader fileReader = new FileReader("transactions.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null){
                String[] transactionInfo = line.split("\\|");
                Transaction transaction = new Transaction(LocalDate.parse(transactionInfo[0]),LocalTime.parse(transactionInfo[1]),transactionInfo[2],transactionInfo[3],Double.parseDouble(transactionInfo[4]));
                ledger.add(transaction);
            }

        } catch (Exception e) {
            System.out.println("ERROR: Can't find the file");
        }
    }



}
