public class Book {
    // declaring private member variables for the Book class
    private String title;
    private String author;
    private int quantity;
    private double price;
    private Customer customer;

    // constructor to initialize a new Book object
    public Book(String title, String author, int quantity, double price) {
        this.title = title;
        this.author = author;
        this.quantity = quantity;
        this.price = price;
        this.customer = null;
    }

    // below are getter methods
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public Customer getCustomer() {
        return customer;
    }

    // setter method to update the book quantity
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // another setter method to assign a customer to the book
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String toString() {
        return title + " by " + author + " - Price: $" + price;
    }

}
