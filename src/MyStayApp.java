import java.util.HashMap;
import java.util.Map;

/**
 * MyStayApp
 *
 * Demonstrates centralized room inventory management using HashMap.
 * Availability is managed through a dedicated inventory component.
 *
 * @author Aditya Kadam
 * @version 3.0
 */

// Inventory Management Class
class RoomInventory {

    private Map<String, Integer> inventory;

    // Constructor initializes inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        // Register room types with availability
        inventory.put("Single Room", 10);
        inventory.put("Double Room", 6);
        inventory.put("Suite Room", 3);
    }

    // Get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability
    public void updateAvailability(String roomType, int newCount) {
        inventory.put(roomType, newCount);
    }

    // Display current inventory
    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " available");
        }
    }
}


// Main Application Class
public class MyStayApp {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("          BOOK MY STAY APP            ");
        System.out.println("======================================");
        System.out.println("Version : 3.0");
        System.out.println("--------------------------------------");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display inventory
        inventory.displayInventory();

        // Example lookup
        System.out.println("\nChecking availability for Double Room:");
        System.out.println("Available: " + inventory.getAvailability("Double Room"));

        // Example update
        System.out.println("\nUpdating availability after booking...");
        inventory.updateAvailability("Double Room", 5);

        // Display updated inventory
        inventory.displayInventory();
    }
}