import java.util.*;

public class InventoryManager {
    private Stack<Book> inventory;
    private Queue<Book> orders;

    public InventoryManager() {
        this.inventory = new Stack<>();
        this.orders = new LinkedList<>();
    }


    // Option 1: adding book(s)
    public void addBook(Scanner scanner) {
        System.out.print("Enter the title of the book: ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter the author of the book: ");
        String author = scanner.nextLine();
        System.out.print("Enter the quantity: ");
        int quantity = scanner.nextInt();
        System.out.print("Enter the price: ");
        double price = scanner.nextDouble();

        Book book = new Book(title, author, quantity, price);
        inventory.push(book);
        System.out.println("Book added to inventory.");
        scanner.nextLine();
    }


    // Option 2: remove book from inventory

    public void removeBook(Scanner scanner) {
        // check if the inventory is empty
        if (inventory.isEmpty()) {
            // if this is true print indicating there are no books to remove
            System.out.println("Inventory is empty. No books to remove.");
        } else {
            // ask the user to enter the title of the book they want to remove
            System.out.print("Enter the title of the book to remove: ");
            String titleToRemove = scanner.nextLine().trim();

            // create a new stack to hold the updated inventory
            Stack<Book> updatedInventory = new Stack<>();

            // iterate over each book in the inventory
            for (Book book : inventory) {
                // if the current book's title does not match the title to remove
                if (!book.getTitle().equals(titleToRemove)) {
                    //if doesn't match add it to the updated inventory
                    updatedInventory.push(book);
                     }
            }

            inventory = updatedInventory;
            // print a message confirming the removal
            System.out.println("Book removed from inventory.");
        }
    }

    // Option 3: update book quantity
    public void updateBookQuantity(Scanner scanner) {
        // check if the inventory is empty
        if (inventory.isEmpty()) {
            // if true then print a message indicating the inventory is empty
            System.out.println("Inventory is empty. No books to update.");
        } else {
            // prompt the user to enter the title of the book to update
            System.out.print("Enter the title of the book to update: ");
            String title = scanner.nextLine().toLowerCase();

            // initialize a variable to hold the book that needs to be updated
            Book bookToUpdate = null;
            // looks over each book in the inventory
            for (Book book : inventory) {

                if (book.getTitle().toLowerCase().equals(title)) {
                    // if a match is found, assign it to bookToUpdate and break the loop
                    bookToUpdate = book;
                    break;
                }
            }
            // checks if a book to update was found
            if (bookToUpdate != null) {
                // asks the user to enter the new quantity for the book
                System.out.print("Enter the new quantity: ");
                try {
                    // attempts to read the new quantity as an integer
                    int newQuantity = scanner.nextInt();
                    // update the book's quantity with the new value
                    bookToUpdate.setQuantity(newQuantity);
                    // print a confirmation message
                    System.out.println("Quantity updated.");
                } catch (InputMismatchException e) {
                    // this handle incorrect input (non-integer) and notify the user
                    System.out.println("Invalid input for quantity. Please enter a valid integer.");
                    scanner.nextLine();
                }
            } else {
                // if no book was found with the given title, notify the user.
                System.out.println("Book not found in inventory.");
            }
        }
        scanner.nextLine();
    }

    // Option 4: list all books
    public void listAllBooks() {
        // if both the inventory and orders are empty
        if (inventory.isEmpty() && orders.isEmpty()) {

            // if true prints a message indicating the inventory is empty
            System.out.println("Inventory is empty.");
        } else {
            // if there are books in the inventory, prints a message
            System.out.println("Books in Inventory:");

            // print books in the inventory and lets the bookstore owner keep track of customer orders
            for (Book book : inventory) {
                System.out.println(book.getTitle() + " by " + book.getAuthor() + " - Quantity: " + book.getQuantity() + " - Price: $" + book.getPrice());
            }
        }
    }


    // Option 5: order books for customer
    public void orderBooks(Scanner scanner) {
        System.out.print("Enter the title of the book you want to order: ");
        String title = scanner.nextLine().trim().toLowerCase();

        Book bookInInventory = getBookInInventory(title);

        if (bookInInventory != null) {
            System.out.print("Enter the quantity: ");
            int quantity;
            try {
                quantity = scanner.nextInt();
                if (quantity <= 0 || quantity > bookInInventory.getQuantity()) {
                    System.out.println("Invalid quantity. Please enter a positive number that does not exceed inventory.");
                    scanner.nextLine(); // this consume the newline character after the number
                    return;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input for quantity. Please enter a valid integer.");
                scanner.nextLine(); // this will consume the invalid input
                return;
            }
            scanner.nextLine(); // the newline character after the number

            System.out.print("Enter customer's first and last name: ");
            String customerName = scanner.nextLine();
            System.out.print("Enter customer's phone number: ");
            String phoneNumber = scanner.nextLine();
            System.out.print("Enter customer's address: ");
            String address = scanner.nextLine();
            System.out.print("Enter customer's email address: ");
            String email = scanner.nextLine();

            // create a new Customer object for the order
            Customer customer = new Customer(customerName, phoneNumber, address, email);

            // CCreate a new book object for the order
            Book order = new Book(bookInInventory.getTitle(), bookInInventory.getAuthor(), quantity, bookInInventory.getPrice());
            order.setCustomer(customer);

            // Add the order to the queue
            orders.offer(order);

            double totalOrderPrice = quantity * bookInInventory.getPrice();
            System.out.println("Order successfully placed for " + customerName + " - Total Price: $" + String.format("%.2f", totalOrderPrice));
        } else {
            System.out.println("Book not found in inventory.");
        }
    }

    // Option 6: process customer's order
    public void processOrders() {
        if (orders.isEmpty()) {
            System.out.println("No customer orders to process.");
            return;
        }

        List<Book> updatedInventory = new ArrayList<>(inventory);

        while (!orders.isEmpty()) {
            Book order = orders.poll();
            boolean orderFulfilled = false;

            // iterate over the updated inventory
            for (int i = 0; i < updatedInventory.size(); i++) {
                // get a book from the inventory.
                Book bookInInventory = updatedInventory.get(i);
                // check if the book in inventory and matches the ordered book and has enough quantity
                if (bookInInventory.getTitle().equals(order.getTitle()) && bookInInventory.getQuantity() >= order.getQuantity()) {
                    // this will isplay order details
                    displayOrderDetails(bookInInventory, order);
                    // update the book's quantity in the inventory.
                    bookInInventory.setQuantity(bookInInventory.getQuantity() - order.getQuantity());
                    // mark the order as fulfilled.
                    orderFulfilled = true;
                    // exit the loop as the order is processed.
                    break;
                }
            }

            // if the order could not be fulfilled then print a message indicating insufficient quantity
            if (!orderFulfilled) {
                System.out.println("Insufficient quantity to fulfill the order for " +
                        (order.getCustomer() != null ? order.getCustomer().getName() : "N/A") + ".");
            }
        }

        // update the inventory with the updated quantities and update it with the changes made to the updatedInventory.
        inventory.clear();
        inventory.addAll(updatedInventory);
    }


    private void displayOrderDetails(Book bookInInventory, Book order) {
        // get the customer details from the order.
        Customer customer = order.getCustomer();
        // this calculates the total price of the order, quantity multiplied by the price of a single book.
        double totalOrderPrice = order.getQuantity() * bookInInventory.getPrice();
        // header for processed order(s)
        System.out.println("Processed Orders");
        System.out.println("-----------------------------------");

        // Print customer details. If any detail is not available or null, it prints "N/A"
        System.out.println("Customer Name: " + (customer != null ? customer.getName() : "N/A"));
        System.out.println(" - Phone #: " + (customer != null ? customer.getPhoneNumber() : "N/A"));
        System.out.println(" - Email: " + (customer != null ? customer.getEmail() : "N/A"));
        System.out.println(" - Address: " + (customer != null ? customer.getAddress() : "N/A"));

        // Print details of the ordered book
        System.out.println(" - Book Title: " + order.getTitle() + " by " + order.getAuthor());
        System.out.println(" - Quantity: " + order.getQuantity());

        //prints price details
        System.out.println(" - Price of one book: $" + bookInInventory.getPrice());
        System.out.println(" - Total Price: $" + String.format("%.2f", totalOrderPrice));
    }


    // helper method to get the book in the current state of the inventory
    private Book getBookInInventory(String title) {
        for (Book book : inventory) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }

        for (Book order : orders) {
            if (order.getTitle().equalsIgnoreCase(title)) {
                return order;
            }
        }
        return null;
    }


    // Option 7: Look for books with author name or title
    public void searchForBooksByAuthorOrTitle(Scanner scanner) {
        // this prompts the user to enter the author's name or book title for search
        System.out.print("Enter the author's name or book title: ");
        String searchInput = scanner.nextLine().trim(); // Removed toLowerCase for exact matching

        //creates a list to store books that match the search criteria
        List<Book> matchingBooks = new ArrayList<>();
        for (Book book : inventory) {
            //check if the book's title or author matches the search input exactly, ignoring the case
            if (book.getAuthor().equals(searchInput) || book.getTitle().equals(searchInput)) {
                matchingBooks.add(book);
            }
        }
        //checks if there are any matching books
        if (!matchingBooks.isEmpty()) {
            System.out.println("Books matching the search:");
            for (Book matchingBook : matchingBooks) {
                // prints each matching book
                System.out.println(matchingBook);
            }
        } else {
            // inform the user if no books match the search criteria
            System.out.println("No books found matching the search criteria.");
        }
    }
}