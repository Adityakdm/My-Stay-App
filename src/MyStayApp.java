import java.util.*;

/**
 * UseCase11ConcurrentBookingSimulation
 *
 * Demonstrates thread-safe room booking under concurrent access.
 * Multiple guests attempt to book rooms simultaneously while
 * synchronization prevents race conditions.
 *
 * @author Aditya Kadam
 * @version 11.0
 */


/* ================= RESERVATION ================= */

class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}


/* ================= INVENTORY SERVICE ================= */

class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    // synchronized ensures thread safety
    public synchronized boolean allocateRoom(String roomType, String guest) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {

            inventory.put(roomType, available - 1);

            System.out.println("Reservation confirmed for "
                    + guest + " | Room Type: " + roomType);

            return true;
        }

        System.out.println("Reservation failed for "
                + guest + " | No rooms available for " + roomType);

        return false;
    }

    public void displayInventory() {

        System.out.println("\nFinal Inventory State:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}


/* ================= BOOKING PROCESSOR ================= */

class BookingProcessor extends Thread {

    private Reservation reservation;
    private InventoryService inventory;

    public BookingProcessor(Reservation reservation,
                            InventoryService inventory) {

        this.reservation = reservation;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        inventory.allocateRoom(
                reservation.getRoomType(),
                reservation.getGuestName());
    }
}


/* ================= MAIN APPLICATION ================= */

public class MyStayApp {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("         BOOK MY STAY APP            ");
        System.out.println("=====================================");
        System.out.println("Version : 11.0\n");

        InventoryService inventory = new InventoryService();

        // Simulated concurrent booking requests
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Single Room");
        Reservation r3 = new Reservation("Charlie", "Single Room");
        Reservation r4 = new Reservation("David", "Suite Room");

        BookingProcessor t1 = new BookingProcessor(r1, inventory);
        BookingProcessor t2 = new BookingProcessor(r2, inventory);
        BookingProcessor t3 = new BookingProcessor(r3, inventory);
        BookingProcessor t4 = new BookingProcessor(r4, inventory);

        // Start threads simultaneously
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inventory.displayInventory();

        System.out.println("\nConcurrent booking simulation completed.");
    }
}