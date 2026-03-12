import java.util.*;

/**
 * UseCase6RoomAllocationService
 *
 * Demonstrates safe room allocation from a booking queue.
 * Ensures unique room IDs and prevents double booking.
 *
 * @author Aditya Kadam
 * @version 6.0
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

    private Map<String, Integer> inventory;

    public InventoryService() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    public void decrement(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {

        System.out.println("\nCurrent Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}


/* ================= BOOKING SERVICE ================= */

class BookingService {

    private Set<String> allocatedRoomIds;
    private Map<String, Set<String>> allocatedRoomsByType;
    private InventoryService inventory;

    public BookingService(InventoryService inventory) {

        this.inventory = inventory;

        allocatedRoomIds = new HashSet<>();
        allocatedRoomsByType = new HashMap<>();
    }

    public void processBookings(Queue<Reservation> queue) {

        while (!queue.isEmpty()) {

            Reservation reservation = queue.poll();

            String roomType = reservation.getRoomType();

            if (inventory.isAvailable(roomType)) {

                String roomId = generateRoomId(roomType);

                allocatedRoomIds.add(roomId);

                allocatedRoomsByType
                        .computeIfAbsent(roomType, k -> new HashSet<>())
                        .add(roomId);

                inventory.decrement(roomType);

                System.out.println("Reservation Confirmed:");
                System.out.println("Guest: " + reservation.getGuestName());
                System.out.println("Room Type: " + roomType);
                System.out.println("Assigned Room ID: " + roomId);
                System.out.println("-----------------------------------");

            } else {

                System.out.println("Reservation Failed (No Availability): "
                        + reservation.getGuestName() + " requested " + roomType);
            }
        }
    }

    private String generateRoomId(String roomType) {

        String prefix = roomType.replace(" ", "").substring(0, 3).toUpperCase();

        String roomId;

        do {
            roomId = prefix + "-" + (100 + new Random().nextInt(900));
        } while (allocatedRoomIds.contains(roomId));

        return roomId;
    }
}


/* ================= MAIN APPLICATION ================= */

public class MyStayApp {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("         BOOK MY STAY APP            ");
        System.out.println("=====================================");
        System.out.println("Version : 6.0\n");

        Queue<Reservation> bookingQueue = new LinkedList<>();

        bookingQueue.add(new Reservation("Alice", "Single Room"));
        bookingQueue.add(new Reservation("Bob", "Double Room"));
        bookingQueue.add(new Reservation("Charlie", "Single Room"));
        bookingQueue.add(new Reservation("David", "Suite Room"));

        InventoryService inventory = new InventoryService();

        BookingService bookingService = new BookingService(inventory);

        bookingService.processBookings(bookingQueue);

        inventory.displayInventory();
    }
}