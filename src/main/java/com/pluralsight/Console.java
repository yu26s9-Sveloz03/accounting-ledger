package com.pluralsight;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Console {

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Prompts the user for a double.
     * @param prompt to display the user
     * @return the double the user selected.
     */
    public static double promptForDouble(String prompt) {

        System.out.print(prompt);

        double result;

        while(true){
            try{
                result =  scanner.nextDouble();
                scanner.nextLine();
                return result;
            }
            catch (InputMismatchException e){
                System.out.print("Invalid selection, please enter a number: ");
            }
            scanner.nextLine();
        }

    }

    /**
     * Prompts the user for a string.
     * @param prompt to display the user
     * @return the string the user selected.
     */
    public static String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    /**
     * Prompts the user for an integer.
     * @param prompt to display the user
     * @return the int the user selected.
     */
    public static int promptForInt(String prompt) {

        System.out.print(prompt);

        int result;

        while(true){
            try{
                result =  scanner.nextInt();
                return result;
            }
            catch (InputMismatchException e){
                System.out.print("Invalid selection, please enter a number: ");
            }
            scanner.nextLine();
        }
    }



    /**
     * Prompts the user for an integer.
     * @param prompt to display the user
     * @return the int the user selected.
     */
    public static int promptForInt(String prompt, int min, int max) {

        System.out.print(prompt);

        int result;

        while(true){
            try{
                result =  scanner.nextInt();

                if(result >= min && result <= max){
                    scanner.nextLine();
                    return result;
                }
                else{
                    scanner.nextLine();
                    System.out.printf("Invalid selection, please enter a number between %d and %d: ", min, max);
                }
            }
            catch (InputMismatchException e){
                scanner.nextLine();
                System.out.print("Invalid selection, please enter a number: ");
            }
        }
    }



    /**
     * Prompts the user for a Yes or No which is returned as a boolean.
     * @param prompt to display the user
     * @return the boolean
     */
    public static boolean promptForYesNo(String prompt) {
        System.out.print(prompt);
        String userInput = scanner.nextLine();
        return userInput.equalsIgnoreCase("YES");
        //opportunity to enhance this with some error protection.
    }

}
