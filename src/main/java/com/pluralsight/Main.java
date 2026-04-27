import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    static void main() throws IOException {
        //scanner to read user input
        Scanner scanner = new Scanner(System.in);
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
                case "D" -> addDeposit();
                case "p","P" -> makePayment();
//                case "l","L" -> ledgerScreen();
                case "x","X" -> {
                    System.out.println("Have a nice day!");
                    return;
                }
            }
        }
    }

    public static void addDeposit() throws IOException {
        Scanner scanner = new Scanner(System.in);
        FileWriter fileWriter = new FileWriter("transactions.csv",true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        System.out.println("What is the reason for this deposit? ");
        String description = scanner.nextLine();
        System.out.println("Who is paying this deposit? ");
        String vendor = scanner.nextLine();
        String formattedAmount;
        while (true) {
            System.out.println("How much is the deposit? ");
            if (scanner.hasNextDouble()){
                double amount = scanner.nextDouble();
                formattedAmount = String.format("%.2f",amount);
                break;
            } else {
                System.out.println("Invalid Input. Try Again");
                scanner.next();
            }
        }
        scanner.nextLine();
        LocalDateTime date = LocalDateTime.now();
        LocalDateTime time = LocalDateTime.now();
        String formattedDate = date.format(dateFormat);
        String formattedTime = time.format(timeFormat);
        bufferedWriter.write("\n" + formattedDate + "|" + formattedTime
        + "|" + description + "|" + vendor + "|" + formattedAmount);
        bufferedWriter.close();
    }

    public static void makePayment() throws IOException {
        Scanner scanner = new Scanner(System.in);
        FileWriter fileWriter = new FileWriter("transactions.csv",true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        System.out.println("What is the reason for this payment? ");
        String description = scanner.nextLine();
        System.out.println("Who is the vendor? ");
        String vendor = scanner.nextLine();
        String formattedAmount;
        while (true) {
            System.out.println("How much is the payment? ");
            if (scanner.hasNextDouble()){
                double amount = scanner.nextDouble();
                amount *= -1;
                formattedAmount = String.format("%.2f",amount);
                break;
            } else {
                System.out.println("Invalid Input. Try Again");
                scanner.next();
            }
        }
        scanner.nextLine();
        LocalDateTime date = LocalDateTime.now();
        LocalDateTime time = LocalDateTime.now();
        String formattedDate = date.format(dateFormat);
        String formattedTime = time.format(timeFormat);
        bufferedWriter.write("\n" + formattedDate + "|" + formattedTime
                + "|" + description + "|" + vendor + "|" + formattedAmount);
        bufferedWriter.close();
    }
}
