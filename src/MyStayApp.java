import java.util.*;

/**
 * UseCase10BookingCancellation
 *
 * Demonstrates safe booking cancellation and system rollback
 * using Stack to track released room IDs.
 *
 * @author Aditya Kadam
 * @version 10.0
 */


/* ================= RESERVATION MODEL ================= */

class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;
    private boolean active;

    public Reservation(String reservationId, String guestName,
                       String roomType, String roomId) {

        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.active = true;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isActive() {
        return active;
    }

    public void cancel() {
        active = false;
    }
}


/* ================= INVENTORY SERVICE ================= */

class InventoryService {

    private Map<String, Integer> inventory;

    public InventoryService() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public void increment(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void displayInventory() {

        System.out.println("\nCurrent Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}


/* ================= CANCELLATION SERVICE ================= */

class CancellationService {

    private Map<String, Reservation> reservations;
    private Stack<String> rollbackStack;
    private InventoryService inventory;

    public CancellationService(Map<String, Reservation> reservations,
                               InventoryService inventory) {

        this.reservations = reservations;
        this.inventory = inventory;
        rollbackStack = new Stack<>();
    }

    public void cancelReservation(String reservationId) {

        if (!reservations.containsKey(reservationId)) {
            System.out.println("Cancellation Failed: Reservation not found.");
            return;
        }

        Reservation reservation = reservations.get(reservationId);

        if (!reservation.isActive()) {
            System.out.println("Cancellation Failed: Already cancelled.");
            return;
        }

        // Record room ID for rollback
        rollbackStack.push(reservation.getRoomId());

        // Restore inventory
        inventory.increment(reservation.getRoomType());

        reservation.cancel();

        System.out.println("\nReservation Cancelled Successfully");
        System.out.println("Released Room ID: " + reservation.getRoomId());
    }

    public void displayRollbackStack() {

        System.out.println("\nRollback Stack (Released Rooms):");

        for (String roomId : rollbackStack) {
            System.out.println(roomId);
        }
    }
}


/* ================= MAIN APPLICATION ================= */

public class MyStayApp {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("         BOOK MY STAY APP            ");
        System.out.println("=====================================");
        System.out.println("Version : 10.0");

        InventoryService inventory = new InventoryService();

        Map<String, Reservation> reservations = new HashMap<>();

        // Simulated confirmed reservation
        Reservation r1 = new Reservation(
                "RES-201", "Alice", "Single Room", "SIN-301");

        reservations.put(r1.getReservationId(), r1);

        CancellationService cancellationService =
                new CancellationService(reservations, inventory);

        // Guest cancels reservation
        cancellationService.cancelReservation("RES-201");

        cancellationService.displayRollbackStack();

        inventory.displayInventory();
    }
}