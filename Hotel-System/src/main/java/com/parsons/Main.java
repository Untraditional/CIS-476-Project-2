package com.parsons;

import com.parsons.handler.*;
import com.parsons.model.Bids;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Setting up the chain of responsibility
        abstractRoomHandler suiteHandler = new SuiteHandler();
        abstractRoomHandler deluxeHandler = new DeluxeHandler();
        abstractRoomHandler standardHandler = new StandardHandler();

        suiteHandler.setNextHandler(deluxeHandler);
        deluxeHandler.setNextHandler(standardHandler);

        // testing the chain
        Scanner scanner = new Scanner(System.in);
        System.out.println("Testing hotel bidding system");

        while(true) {
            System.out.println("Enter the bid amount or type exit to quit: ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Thanks for trying");
                break;
            }

            try {
                double bidAmount = Double.parseDouble(input.trim());
                Bids request = new Bids(bidAmount);

                // processing the bid starting with suite handler
                String result = suiteHandler.handleRequest(request);
                System.out.println(result);
            } catch (NumberFormatException e) {
                System.out.println("Invalid bid amount");
            }
            System.out.println();
        }
    }
}