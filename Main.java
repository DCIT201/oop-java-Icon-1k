public class Main {
    public static void main(String[] args) {
    import java.util.ArrayList;
    import java.util.List;
    
    // Abstract class representing a Vehicle
    abstract class Vehicle {
        private String model;
        private String brand;
        private double pricePerDay;
    
        // Constructor
        public Vehicle(String model, String brand, double pricePerDay) {
            this.model = model;
            this.brand = brand;
            this.pricePerDay = pricePerDay;
        }
    
        // Abstract method to be implemented by subclasses
        public abstract String getVehicleType();
    
        // Method to calculate rental cost
        public double calculateRentalCost(int days) {
            return this.pricePerDay * days;
        }
    
        // Getters and setters
        public String getModel() {
            return model;
        }
    
        public String getBrand() {
            return brand;
        }
    
        public double getPricePerDay() {
            return pricePerDay;
        }
    
        @Override
        public String toString() {
            return brand + " " + model + " (" + getVehicleType() + ")";
        }
    }
    
    // Car class inherits from Vehicle
    class Car extends Vehicle {
        private int seatingCapacity;
    
        public Car(String model, String brand, double pricePerDay, int seatingCapacity) {
            super(model, brand, pricePerDay);
            this.seatingCapacity = seatingCapacity;
        }
    
        @Override
        public String getVehicleType() {
            return "Car";
        }
    
        @Override
        public String toString() {
            return super.toString() + " - " + seatingCapacity + " seats";
        }
    }
    
    // Truck class inherits from Vehicle
    class Truck extends Vehicle {
        private double cargoCapacity;  // in kg
    
        public Truck(String model, String brand, double pricePerDay, double cargoCapacity) {
            super(model, brand, pricePerDay);
            this.cargoCapacity = cargoCapacity;
        }
    
        @Override
        public String getVehicleType() {
            return "Truck";
        }
    
        @Override
        public String toString() {
            return super.toString() + " - " + cargoCapacity + " kg cargo capacity";
        }
    }
    
    // Bike class inherits from Vehicle
    class Bike extends Vehicle {
        private String bikeType;
    
        public Bike(String model, String brand, double pricePerDay, String bikeType) {
            super(model, brand, pricePerDay);
            this.bikeType = bikeType;
        }
    
        @Override
        public String getVehicleType() {
            return "Bike";
        }
    
        @Override
        public String toString() {
            return super.toString() + " - " + bikeType + " bike";
        }
    }
    
    // Customer class represents a customer renting a vehicle
    class Customer {
        private int customerId;
        private String name;
        private int age;
        private List<RentalTransaction> rentalHistory;
    
        // Constructor
        public Customer(int customerId, String name, int age) {
            this.customerId = customerId;
            this.name = name;
            this.age = age;
            this.rentalHistory = new ArrayList<>();
        }
    
        // Rent a vehicle
        public RentalTransaction rentVehicle(Vehicle vehicle, int days) {
            RentalTransaction rental = new RentalTransaction(this, vehicle, days);
            rentalHistory.add(rental);
            return rental;
        }
    
        // Getters
        public int getCustomerId() {
            return customerId;
        }
    
        public String getName() {
            return name;
        }
    
        public List<RentalTransaction> getRentalHistory() {
            return rentalHistory;
        }
    
        @Override
        public String toString() {
            return "Customer: " + name + " (ID: " + customerId + ")";
        }
    }
    
    // RentalTransaction class tracks the rental details
    class RentalTransaction {
        private Customer customer;
        private Vehicle vehicle;
        private int rentalDays;
        private double totalCost;
    
        public RentalTransaction(Customer customer, Vehicle vehicle, int rentalDays) {
            this.customer = customer;
            this.vehicle = vehicle;
            this.rentalDays = rentalDays;
            this.totalCost = vehicle.calculateRentalCost(rentalDays);
        }
    
        public double getTotalCost() {
            return totalCost;
        }
    
        @Override
        public String toString() {
            return customer.getName() + " rented " + vehicle + " for " + rentalDays + " days. Total cost: $" + totalCost;
        }
    }
    
    // RentalSystem class manages customers and vehicles
    class RentalSystem {
        private List<Customer> customers;
        private List<Vehicle> vehicles;
    
        // Constructor
        public RentalSystem() {
            this.customers = new ArrayList<>();
            this.vehicles = new ArrayList<>();
        }
    
        // Add customer to the system
        public void addCustomer(Customer customer) {
            customers.add(customer);
        }
    
        // Add vehicle to the system
        public void addVehicle(Vehicle vehicle) {
            vehicles.add(vehicle);
        }
    
        // List available vehicles
        public void listVehicles() {
            for (Vehicle vehicle : vehicles) {
                System.out.println(vehicle);
            }
        }
    
        // Process rental transaction
        public RentalTransaction processRental(int customerId, String vehicleType, int rentalDays) {
            Customer customer = customers.stream().filter(c -> c.getCustomerId() == customerId).findFirst().orElse(null);
            if (customer == null) {
                System.out.println("Customer not found!");
                return null;
            }
    
            // Polymorphism: Handle different types of vehicles uniformly
            Vehicle selectedVehicle = vehicles.stream()
                    .filter(v -> v.getVehicleType().equalsIgnoreCase(vehicleType))
                    .findFirst()
                    .orElse(null);
            
            if (selectedVehicle == null) {
                System.out.println("Vehicle type not available!");
                return null;
            }
    
            return customer.rentVehicle(selectedVehicle, rentalDays);
        }
    }
    