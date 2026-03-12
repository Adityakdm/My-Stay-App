/**
 * MyStayApp
 *
 * Demonstrates room initialization for the MyStay Hotel Booking System.
 * Introduces abstraction, inheritance, polymorphism, and static room availability.
 *
 * @author Aditya Kadam
 * @version 2.0
 */

// Abstract Room class
abstract class Room {

    protected String roomType;
    protected int beds;
    protected double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Price     : $" + price);
    }
}

// Single Room class
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 100.0);
    }
}

// Double Room class
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 180.0);
    }
}

// Suite Room class
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 350.0);
    }
}

// Main Application Class
public class MyStayApp {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("         BOOK MY STAY APP             ");
        System.out.println("======================================");
        System.out.println("Version : 2.0");
        System.out.println("--------------------------------------");

        // Create room objects (Polymorphism)
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Static availability variables
        int singleAvailable = 10;
        int doubleAvailable = 6;
        int suiteAvailable = 3;

        System.out.println("\nSingle Room Details:");
        singleRoom.displayRoomDetails();
        System.out.println("Available : " + singleAvailable);

        System.out.println("\nDouble Room Details:");
        doubleRoom.displayRoomDetails();
        System.out.println("Available : " + doubleAvailable);

        System.out.println("\nSuite Room Details:");
        suiteRoom.displayRoomDetails();
        System.out.println("Available : " + suiteAvailable);

        System.out.println("\nApplication Execution Completed.");
    }
}