import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        // makes an instance of InventoryManager to manage bookstore operations
        InventoryManager inventoryManager = new InventoryManager();
        Scanner scanner = new Scanner(System.in);

        // start an infinite loop to continuously display the menu and process user input
        while (true) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline character

            // process the user's choice using if else statements
            if (choice == 1) {
                inventoryManager.addBook(scanner);
            } else if (choice == 2) {
                inventoryManager.removeBook(scanner);
            } else if (choice == 3) {
                inventoryManager.updateBookQuantity(scanner);
            } else if (choice == 4) {
                inventoryManager.listAllBooks();
            } else if (choice == 5) {
                inventoryManager.orderBooks(scanner);
            } else if (choice == 6) {
                inventoryManager.processOrders();
            } else if (choice == 7) {
                inventoryManager.searchForBooksByAuthorOrTitle(scanner);
            }  else if (choice == 8) {
                System.out.println("Exiting program. Thank you, see you later!");
                System.exit(0);
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // pritns menu for options
    private static void printMenu() {
        System.out.println("-----------------------------------");
        System.out.println("Bookstore Inventory Management Menu");
        System.out.println("-=================================-");
        System.out.println("1. Add a Book");
        System.out.println("2. Remove a Book");
        System.out.println("3. Update Book Quantity");
        System.out.println("4. List All Books");
        System.out.println("5. Order Book");
        System.out.println("6. Check Processed Order");
        System.out.println("7. Look for books with author name");
        System.out.println("8. Exit");
        System.out.println("-=================================-");
        System.out.println("Enter your choice: ");
    }
}