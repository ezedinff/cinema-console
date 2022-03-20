package cinema;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int row = Integer.parseInt((scanner.next()));
        System.out.println("Enter the number of seats in each row:");
        int seats = Integer.parseInt((scanner.next()));
        HashMap<String,Integer> inventory = new HashMap<>();
        String[][] arr = new String[row + 1][seats + 1];
        initRoom(arr, row, seats);
        int stop = 7;
        while (stop != 0) {
            stop = showMenu(scanner, arr, row, seats, inventory);
        }
    }

    public static int showMenu(Scanner scanner, String[][] arr, int row, int seats, HashMap<String, Integer> store) {
        System.out.println("\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit");
        int choice = scanner.nextInt();
        if (choice == 1) {
            showSeats(arr, row, seats);
        } else if (choice == 2) {
            buyTickets(scanner, arr, row, seats, store);
        } else if (choice == 3) {
            showStatistics(row, seats, store);
        }
        return choice;
    }

    public static void initRoom(String[][] arr, int row, int seats) {
        for (int i = 0; i <= row; i++){
            if (i == 0) {
                arr[i][0] = "  ";
            } else {
                arr[i][0] = i + " ";
            }
            for (int j = 1; j <= seats; j++) {
                if (i == 0) {
                    arr[i][j] = j + " ";
                } else {
                    arr[i][j] = "S ";
                }
            }
        }
    }

    public static void showSeats(String[][] arr, int row, int seats) {
        System.out.println("\nCinema:");
        for (int i = 0; i <= row; i++){
            System.out.print(arr[i][0]);
            for (int j = 1; j <= seats; j++) {
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }
    }

    public static void showStatistics(int row, int seats, HashMap<String, Integer> store) {
        String template = "Number of purchased tickets: %d\nPercentage: %.2f%%\nCurrent income: $%d\nTotal income: $%d";
        int numberOfPurchasedTickets = store.size();
        float percentage =  ((float) numberOfPurchasedTickets / (row * seats)) * 100;
        int currentIncome = 0;
        for (int value : store.values()){
            currentIncome += value;
        }
        int totalIncome = getTotalIncome(row, seats);
        template = String.format(template, numberOfPurchasedTickets, percentage, currentIncome, totalIncome);
        System.out.println(template);
    }

    public static int getTotalIncome(int row, int seats) {
        if (row * seats <= 60) {
            return (row * seats) * 10;
        }
        int half = row / 2;
        int anotherHalf = row - half;
        return ((half * seats) * 10) + ((anotherHalf * seats) * 8);
    }

    public static void buyTickets(Scanner scanner, String[][] arr, int r, int c, HashMap<String, Integer> store) {
        System.out.println();
        System.out.println("Enter a row number:");
        int row = Integer.parseInt((scanner.next()));
        System.out.println("Enter a seat number in that row:");
        int seats = Integer.parseInt((scanner.next()));
        System.out.println();

        int seatPrice = 10;
        int totalSeats = r * c;
        if (totalSeats > 60) {
            int frontRows = r / 2;
            if (row > frontRows) {
                seatPrice = 8;
            }
        }
        String key = String.format("%d_%d", row, seats);
        if (store.get(key) == null) {
            try {
                arr[row][seats] = "B ";
                store.put(key, seatPrice);
                System.out.printf("Ticket price: $%d%n", seatPrice);
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("Wrong input!");
                buyTickets(scanner, arr, r, c, store);
            }
        } else {
            System.out.println("That ticket has already been purchased!");
            buyTickets(scanner, arr, r, c, store);
        }
    }
}