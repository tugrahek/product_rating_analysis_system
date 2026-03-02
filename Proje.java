package proje;

import java.io.*;
import java.util.*;
        
public class Proje {

    public static void main(String[] args) {
        // Initialize variables
        String productCount = null;
        String[] productNames = null;
        int row = 0;
        int numCustomers = 0;
        int customerNumber = 0;
        String[] items;
        TwoDimensionalArray array = null;
        CustomerLinkedList customerList = new CustomerLinkedList();
        Scanner scanner = new Scanner(System.in);
        int showMenu = 0;

        // Display menu options
        System.out.println("""
                           1)Reading the data from the file and creating the relevant data structures.
                           2)Entering new customer information via keyboard.
                           3)Calculating and printing the average rating score for each product.
                           4)Calculating and printing the average rating score obtained from only customers whose country is "Turkey" for each product.
                           5)Calculating and printing the average rating score obtained from only customers whose country is not "Turkey" for each product.
                           6)Calculating and printing the average rating score obtained from only customers whose profession is "Doctor" for each product.
                           7)Printing the linked list of customer information from the beginning to the end.
                           8)Printing the two-dimensional array to the screen.
                           9)Exit.""");

        // Try to read the product names from the file
        try {
            Scanner scan = new Scanner(new FileInputStream("File.txt"));
            if (scan.hasNextLine()) {
                String firstLine = scan.nextLine();
                String[] parts = firstLine.split(",");
                productNames = new String[parts.length - 1];

                // Extract product names from the first line of the file
                for (int i = 1; i < parts.length; i++) {
                    productNames[i - 1] = parts[i].trim();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File could not be read: " + e.getMessage());
        }

        // Main menu loop
        while (showMenu > 0 || showMenu <= 9) {
            System.out.println("Select a process 1 to 9.");
            showMenu = scanner.nextInt();

            if (showMenu == 1) {
                // Reading data from file and creating data structures
                Scanner scanner1 = null;

                try {
                    scanner1 = new Scanner(new FileInputStream("File.txt"));
                    if (scanner1.hasNextLine()) {
                        String line = scanner1.nextLine();
                        items = line.split(",");
                        productCount = items[0];
                        array = new TwoDimensionalArray(200, Integer.parseInt(productCount) + 1);
                    }
                    int lineCount = 1;

                    // Read customer and rating data from the file
                    while (scanner1.hasNextLine()) {
                        lineCount++;
                        String line = scanner1.nextLine();
                        items = line.split(",");

                        if (lineCount % 2 == 0) { 
                            // Processing customer data
                            customerNumber = Integer.parseInt(items[0]);
                            String name = items[1];
                            String surname = items[2];
                            String country = items[3];
                            String city = items[4];
                            String occupation = items[5];

                            // Create and add customer data to the list
                            CustomerData customerData = new CustomerData(name, surname, country, city, occupation);
                            customerList.addCustomer(customerNumber, customerData);
                            numCustomers++;
                        } else {
                            // Processing ratings data
                            row = lineCount / 2 - 1;
                            int[] ratingsRow = new int[Integer.parseInt(productCount)];
                            for (int i = 0; i < items.length; i++) {
                                ratingsRow[i] = Integer.parseInt(items[i]);
                            }
                            array.setRatingsRow(row, ratingsRow, customerNumber);
                        }
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("File could not be read: " + e.getMessage());
                }
            }

            if (showMenu == 2) {
                // Enter new customer information
                if (numCustomers + 1 > 200) {
                    System.out.println("Number of customers cannot be more than 200.");
                    return;
                } else {
                    numCustomers++;
                    enterNewCustomer(scanner, customerList, productCount, array, numCustomers);
                }
            }

            if (showMenu == 3) {
                // Calculate and print the average rating for each product
                calculateAverage(array.getRatings(), productNames);
            }

            if (showMenu == 4) {
                // Calculate and print the average rating for each product by customers from Turkey
                calculateAverageByCountry(array.getRatings(), customerList, productNames, "Turkey");
            }

            if (showMenu == 5) {
                // Calculate and print the average rating for each product by customers not from Turkey
                calculateAverageByCountry(array.getRatings(), customerList, productNames, "not Turkey");
            }

            if (showMenu == 6) {
                // Calculate and print the average rating for each product by customers who are doctors
                calculateAverageByOccupation(array.getRatings(), customerList, productNames, "Doctor");
            }

            if (showMenu == 7) {
                // Print the linked list of customer information
                customerList.displayList();
            }

            if (showMenu == 8) {
                // Print the two-dimensional array
                int[][] ratings = array.getRatings();
                array.printRatings(ratings, numCustomers);
            }

            if (showMenu == 9) {
                // Exit the program
                System.out.println("Exiting...");
                System.exit(0);
            }
        }
    }

    // Method to enter new customer information
    private static void enterNewCustomer(Scanner scanner, CustomerLinkedList customerList, String productCount, TwoDimensionalArray array, int numCustomers) {
        System.out.println("Enter customer information:");
        System.out.print("Enter the customer number: ");
        int customerNumber = scanner.nextInt();
        System.out.print("Enter the customer's name: ");
        String name = scanner.next();
        System.out.print("Enter the customer's surname: ");
        String surname = scanner.next();
        System.out.print("Enter the country where customer lives: ");
        String country = scanner.next();
        System.out.print("Enter the city where customer lives: ");
        String city = scanner.next();
        System.out.print("Enter the customer's occupation: ");
        String occupation = scanner.next();

        // Create a new customer data object and add it to the list
        CustomerData newCustomer = new CustomerData(name, surname, country, city, occupation);
        customerList.addCustomer(customerNumber, newCustomer);
        int numProducts = Integer.parseInt(productCount);
        int[] ratings = new int[numProducts - 1];

        // Get ratings for each product
        for (int i = 0; i < numProducts - 1; i++) {
            System.out.print("Points for product  " + (char) ('A' + i) + "(1-5): ");
            ratings[i] = scanner.nextInt();
            while (ratings[i] < 1 || ratings[i] > 5) {
                System.out.print("Wrong input! Input should be (1-5), points for product  " + (char) ('A' + i) + ": ");
                ratings[i] = scanner.nextInt();
            }
        }

        // Predict the rating for the last product
        int predictedRating = predictRating(array.getRatings(), ratings, numProducts - 1);
        System.out.println("Predicted rating: " + predictedRating);
        int[] completeRatings = new int[numProducts];
        System.arraycopy(ratings, 0, completeRatings, 0, ratings.length);
        completeRatings[numProducts - 1] = predictedRating;
        array.setRatingsRow(numCustomers - 1, completeRatings, customerNumber);
        System.out.println("New customer information added successfully.");
    }

    // Method to predict rating for the last product based on existing ratings
    private static int predictRating(int[][] ratings, int[] newRatings, int lastProductIndex) {
        if (ratings == null || ratings.length == 0 || newRatings == null || newRatings.length == 0) {
            throw new IllegalArgumentException("Ratings data or new ratings data is invalid.");
        }
        int minSimilarity = Integer.MAX_VALUE;
        int predictedRating = 0;
        int similarCustomerCount = 0;
        int similarCustomerTotalRating = 0;

        // Calculate similarity between new ratings and existing ratings
        for (int i = 0; i < ratings.length; i++) {
            if (ratings[i].length <= lastProductIndex + 1) {
                continue;
            }
            int similarity = 0;
            for (int j = 1; j <= lastProductIndex; j++) {
                similarity += Math.abs(newRatings[j - 1] - ratings[i][j]);
            }

            // Update predicted rating based on most similar customer
            if (similarity < minSimilarity) {
                minSimilarity = similarity;
                predictedRating = ratings[i][lastProductIndex + 1];
                similarCustomerCount = 1;
                similarCustomerTotalRating = ratings[i][lastProductIndex + 1];
            } else if (similarity == minSimilarity) {
                similarCustomerTotalRating += ratings[i][lastProductIndex + 1];
                similarCustomerCount++;
            }
        }

        // If there are multiple similar customers, average their ratings
        if (similarCustomerCount > 1) {
            predictedRating = similarCustomerTotalRating / similarCustomerCount;
        }

        return predictedRating;
    }

    // Method to calculate and print the average rating for each product
    private static void calculateAverage(int[][] ratings, String[] productNames) {
        int numCustomers = ratings.length;
        int numProducts = ratings[0].length - 1;

        for (int j = 1; j <= numProducts; j++) {
            int totalRating = 0;
            int ratingCount = 0;

            // Sum ratings for each product
            for (int i = 0; i < numCustomers; i++) {
                if (ratings[i][j] != 0) {
                    totalRating += ratings[i][j];
                    ratingCount++;
                }
            }

            // Calculate and print average rating
            double averageRating = (double) totalRating / ratingCount;
            System.out.println("Average rating for product " + productNames[j - 1] + ": " + averageRating);
        }
    }

    // Method to calculate and print the average rating for each product by country
    private static void calculateAverageByCountry(int[][] ratings, CustomerLinkedList customerList, String[] productNames, String country) {
        int numCustomers = ratings.length;
        int numProducts = ratings[0].length - 1;

        for (int j = 1; j <= numProducts; j++) {
            int totalRating = 0;
            int ratingCount = 0;

            // Sum ratings for each product by country
            for (int i = 0; i < numCustomers; i++) {
                if (ratings[i][j] != 0 && (country.equals("not Turkey") ? !customerList.getCountry(ratings[i][0]).equals("Turkey") : customerList.getCountry(ratings[i][0]).equals("Turkey"))) {
                    totalRating += ratings[i][j];
                    ratingCount++;
                }
            }

            // Calculate and print average rating by country
            double averageRating = (double) totalRating / ratingCount;
            System.out.println("Average rating for product " + productNames[j - 1] + " (" + (country.equals("not Turkey") ? "Not Turkey" : "Turkey") + "): " + averageRating);
        }
    }

    // Method to calculate and print the average rating for each product by occupation
    private static void calculateAverageByOccupation(int[][] ratings, CustomerLinkedList customerList, String[] productNames, String occupation) {
        int numCustomers = ratings.length;
        int numProducts = ratings[0].length - 1;

        for (int j = 1; j <= numProducts; j++) {
            int totalRating = 0;
            int ratingCount = 0;

            // Sum ratings for each product by occupation
            for (int i = 0; i < numCustomers; i++) {
                if (ratings[i][j] != 0 && customerList.getOccupation(ratings[i][0]).equals(occupation)) {
                    totalRating += ratings[i][j];
                    ratingCount++;
                }
            }

            // Calculate and print average rating by occupation
            double averageRating = (double) totalRating / ratingCount;
            System.out.println("Average rating for product " + productNames[j - 1] + " (Occupation: " + occupation + "): " + averageRating);
        }
    }
}
