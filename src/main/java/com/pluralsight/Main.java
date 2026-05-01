package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
                default -> System.out.println("Invalid Input. Please try again.");
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
                case "R" -> reportsScreen(ledger);
                case "H" -> {
                    System.out.println("Going back to Home Screen\n");
                    return;
                }
                default -> System.out.println("Invalid Input. Please try again.");
            }
        }
    }

    public static void allEntries(ArrayList<Transaction> ledger){
        ArrayList<Transaction> newestLedger = new ArrayList<Transaction>();
        newestLedger = new ArrayList<>(ledger);
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

    public static void reportsScreen(ArrayList<Transaction> ledger){
        while (true){

            System.out.print("-----Reports Screen-----\n\n\t" +
                    "1) Month to Date\n\t" +
                    "2) Previous Month\n\t" +
                    "3) Year to Date\n\t" +
                    "4) Previous Year\n\t" +
                    "5) Search by Vendor\n\t" +
                    "6) Custom Search\n\t" +
                    "0) Back\n\n");
            int command = Console.promptForInt("Choose an option: ");
            switch (command) {
                case 1 -> monthToDate(ledger);
                case 2 -> previousMonth(ledger);
                case 3 -> yearToDate(ledger);
                case 4 -> previousYear(ledger);
                case 5 -> searchByVendor(ledger);
                case 6 -> customSearch(ledger);
                case 0 -> {
                    System.out.println("Going back to Ledger Screen\n");
                    return;
                }
                default -> System.out.println("Invalid Input. Please try again.");
            }
        }
    }

    public static void monthToDate(ArrayList<Transaction> ledger){
        ArrayList<Transaction> thisMonth = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (Transaction transaction : ledger){
            if (transaction.getDate().getMonth() == today.getMonth() && transaction.getDate().getYear() == today.getYear()){
                thisMonth.add(transaction);
            }
        }
        Collections.reverse(thisMonth);
        for (Transaction transaction : thisMonth){
            transaction.printInfo();
        }
    }

    public static void previousMonth(ArrayList<Transaction> ledger){
        ArrayList<Transaction> prevMonth = new ArrayList<>();
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        for (Transaction transaction : ledger){
            if (transaction.getDate().getMonth() == lastMonth.getMonth() && transaction.getDate().getYear() == lastMonth.getYear()){
                prevMonth.add(transaction);
            }
        }
        Collections.reverse(prevMonth);
        for (Transaction transaction : prevMonth){
            transaction.printInfo();
        }
    }

    public static void previousYear(ArrayList<Transaction> ledger){
        ArrayList<Transaction> prevYear = new ArrayList<>();
        LocalDate lastYear = LocalDate.now().minusYears(1);
        for (Transaction transaction : ledger){
            if (transaction.getDate().getYear() == lastYear.getYear()){
                prevYear.add(transaction);
            }
        }
        Collections.reverse(prevYear);
        for (Transaction transaction : prevYear){
            transaction.printInfo();
        }
    }

    public static void yearToDate(ArrayList<Transaction> ledger){
        ArrayList<Transaction> thisYear = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (Transaction transaction : ledger){
            if (transaction.getDate().getYear() == today.getYear()){
                thisYear.add(transaction);
            }
        }
        Collections.reverse(thisYear);
        for (Transaction transaction : thisYear){
            transaction.printInfo();
        }
    }

    public static void searchByVendor(ArrayList<Transaction> ledger){
        String vendor = Console.promptForString("What is the name of the vendor? ");
        ArrayList<Transaction> vendorList = new ArrayList<>();
        for (Transaction transaction : ledger){
            if (transaction.getVendor().equalsIgnoreCase(vendor)){
                vendorList.add(transaction);
            }
        }
        Collections.reverse(vendorList);
        for (Transaction transaction : vendorList){
            transaction.printInfo();
        }
        if (vendorList.isEmpty()){
            System.out.println("Looks like there is no transaction with that vendor.");
        }
    }

    public static void customSearch(ArrayList<Transaction> ledger){
        String startDate = Console.promptForString("What is the start date? (yyyy-mm-dd) ");
        startDate = dateCheck(startDate);
        String endDate = Console.promptForString("What is the end date? (yyyy-mm-dd) ");
        endDate = dateCheck(endDate);
        if (!startDate.isBlank() && !endDate.isBlank()){
            while (true){
                if (LocalDate.parse(endDate).isBefore(LocalDate.parse(startDate))) {
                    endDate = Console.promptForString("End date can't be before the start date. Please try again. (yyyy-mm-dd) ");
                    endDate = dateCheck(endDate);
                } else {
                    break;
                }
            }
        }
        String description = Console.promptForString("What is the description? ");
        String vendor = Console.promptForString("What is the vendor? ");
        String amount = Console.promptForString("What is the amount? ");
        //todo: search google on how to copy an arraylist without a reference.
        ArrayList<Transaction> custom = new ArrayList<>(ledger);
        if (!startDate.isBlank()) {
            for (int i = 0; i < custom.size(); i++) {
                if (!custom.get(i).getDate().isAfter(LocalDate.parse(startDate))) {
                    custom.remove(custom.get(i));
                    i--;
                }
            }
        }
        // 0, 1, 2, 3, 4
        // 0 apples
        // 1 oranges
        //
        // 2 banana
        //
        // 3 raspberry
        // 4 grapes

        if (!endDate.isBlank()){
            for (int i = 0; i < custom.size(); i++){
                if (!custom.get(i).getDate().isBefore(LocalDate.parse(endDate))){
                    custom.remove(custom.get(i));
                    i--;
                }
            }
        }

        if (!description.isBlank()){
            for (int i = 0; i < custom.size(); i++){
                if (!custom.get(i).getDescription().equalsIgnoreCase(description)){
                    custom.remove(custom.get(i));
                    i--;
                }
            }
        }

        if (!vendor.isBlank()){
            for (int i = 0; i < custom.size(); i++){
                if (!custom.get(i).getVendor().equalsIgnoreCase(vendor)){
                    custom.remove(custom.get(i));
                    i--;
                }
            }
        }

        if (!(amount.isBlank())){
            for (int i = 0; i < custom.size(); i++){
                if (!(custom.get(i).getAmount() == Double.parseDouble(amount))){
                    custom.remove(custom.get(i));
                    i--;
                }
            }
        }
        Collections.reverse(custom);
        for ( Transaction transaction : custom){
            transaction.printInfo();
        }
        if (custom.isEmpty()){
            System.out.println("Looks like there isn't anything that matches your filters. Try broadening your search.");
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

    public static String dateCheck(String stringDate){
        Scanner scanner = new Scanner(System.in);
        if (stringDate.isBlank()){
            return stringDate;
        }
        while (true){
            try {
                LocalDate date = LocalDate.parse(stringDate);
                return stringDate;

            } catch (DateTimeParseException e){
                System.out.println("Date not in correct format, please try again. (yyyy-mm-dd) ");
                stringDate = scanner.nextLine();
            }
        }
    }
}
