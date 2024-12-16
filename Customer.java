public class Customer {
    // private member variables to store customer details
    private String name;
    private String phoneNumber;
    private String address;
    private String email;

    public Customer(String name, String phoneNumber, String address, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
    }

    //getter methods below
    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


    public String getAddress(){
        return address;
    }

    public String getEmail(){
        return email;
    }


}
