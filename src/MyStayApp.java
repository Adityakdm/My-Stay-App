import java.util.HashMap;
import java.util.Map;

/**
 * UseCase9ErrorHandlingValidation
 *
 * Demonstrates structured validation and custom exception handling
 * for invalid booking inputs and inconsistent inventory states.
 *
 * @author Aditya Kadam
 * @version 9.0
 */


/* ================= CUSTOM EXCEPTION ================= */

class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}


/* ================= INVENTORY SERVICE ================= */

class InventoryService {

    private Map<String, Integer> inventory;

    public InventoryService() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public void validateRoomType(String roomType) throws InvalidBookingException {

        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException(
                    "Invalid room type: " + roomType);
        }
    }

    public void checkAvailability(String roomType) throws InvalidBookingException {

        int available = inventory.get(roomType);

        if (available <= 0) {
            throw new InvalidBookingException(
                    "No rooms available for: " + roomType);
        }
    }

    public void allocateRoom(String roomType) {

        int available = inventory.get(roomType);

        inventory.put(roomType, available - 1);
    }

    public void displayInventory() {

        System.out.println("\nCurrent Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}


/* ================= BOOKING VALIDATOR ================= */

class BookingValidator {

    private InventoryService inventory;

    public BookingValidator(InventoryService inventory) {
        this.inventory = inventory;
    }

    public void processBooking(String guestName, String roomType)
            throws InvalidBookingException {

        // Validate input
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        inventory.validateRoomType(roomType);
        inventory.checkAvailability(roomType);

        // Safe allocation
        inventory.allocateRoom(roomType);

        System.out.println("\nReservation Confirmed!");
        System.out.println("Guest: " + guestName);
        System.out.println("Room Type: " + roomType);
    }
}


/* ================= MAIN APPLICATION ================= */

public class MyStayApp {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("         BOOK MY STAY APP            ");
        System.out.println("=====================================");
        System.out.println("Version : 9.0");

        InventoryService inventory = new InventoryService();
        BookingValidator validator = new BookingValidator(inventory);

        try {

            validator.processBooking("Alice", "Single Room");

            // Invalid room type example
            validator.processBooking("Bob", "Luxury Room");

        } catch (InvalidBookingException e) {

            System.out.println("\nBooking Failed:");
            System.out.println(e.getMessage());
        }

        inventory.displayInventory();

        System.out.println("\nSystem continues running safely.");
    }
}