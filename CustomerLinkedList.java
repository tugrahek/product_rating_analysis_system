package proje;

public class CustomerLinkedList {
    static class Node {
        // Represents a customer record in the linked list

        private int customerNumber; // Unique customer number
        private CustomerData customerData; // Customer information
        private Node next; // Reference to the next node

        // Constructor to initialize Node with customer number and data
        public Node(int customerNumber, CustomerData customerData) {
            this.customerNumber = customerNumber;
            this.customerData = customerData;
            this.next = null; // Initially, next is null
        }
    }
    
    private Node head; // Reference to the first node

    // Constructor to initialize an empty list
    public CustomerLinkedList() {
        this.head = null; // Initially empty
    }

    // Add a new customer to the list
    public void addCustomer(int customerNumber, CustomerData customerData) {
        Node newNode = new Node(customerNumber, customerData); // Create new node

        // Insert at the beginning if list is empty or new customer comes before head
        if (head == null || customerNumber < head.customerNumber) {
            newNode.next = head;
            head = newNode;
        } else {
            Node current = head;
            // Find appropriate position to insert new node
            while (current.next != null && current.next.customerNumber < customerNumber) {
                current = current.next;
            }
            // Insert the new node
            newNode.next = current.next;
            current.next = newNode;
        }
    }

    // Display the contents of the list
    public void displayList() {
        Node current = head;
        while (current != null) {
            System.out.println("Customer Number: " + current.customerNumber);
            System.out.println("Customer Data: " + current.customerData.toString());
            System.out.println("");
            current = current.next;
        }
    }

    // Retrieve the country of a customer based on their customer number
    public String getCountry(int customerNumber) {
        Node current = head;
        while (current != null) {
            if (current.customerNumber == customerNumber) {
                return current.customerData.getCountry();
            }
            current = current.next;
        }
        return null; 
    }

    // Retrieve the occupation of a customer based on their customer number
    public String getOccupation(int customerNumber) {
        Node current = head;
        while (current != null) {
            if (current.customerNumber == customerNumber) {
                return current.customerData.getOccupation();
            }
            current = current.next;
        }
        return null; 
    }
}




