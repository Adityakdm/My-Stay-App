import java.util.HashMap;
import java.util.Map;

/**
 * MyStayApp
 *
 * Demonstrates read-only room search functionality using centralized inventory.
 * Guests can view available room types and their details without modifying system state.
 *
 * @author Aditya Kadam
 * @version 4.0
 */


/* ================= ROOM DOMAIN MODEL ================= */

// Abstract Room class
abstract class Room {

    protected String type;
    protected int beds;
    protected double price;

    public Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Room Type : " + type);
        System.out.println("Beds      : " + beds);
        System.out.println("Price     : $" + price);
    }
}

// Concrete room types
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 100.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 180.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 350.0);
    }
}


/* ================= INVENTORY ================= */

class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 10);
        inventory.put("Double Room", 5);
        inventory.put("Suite Room", 0);   // Example unavailable
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }
}


/* ================= SEARCH SERVICE ================= */

class SearchService {

    public void searchAvailableRooms(RoomInventory inventory) {

        System.out.println("\nAvailable Rooms:\n");

        Map<String, Integer> data = inventory.getInventory();

        for (String roomType : data.keySet()) {

            int available = data.get(roomType);

            // Defensive programming: filter unavailable rooms
            if (available > 0) {

                Room room;

                switch (roomType) {
                    case "Single Room":
                        room = new SingleRoom();
                        break;

                    case "Double Room":
                        room = new DoubleRoom();
                        break;

                    case "Suite Room":
                        room = new SuiteRoom();
                        break;

                    default:
                        continue;
                }

                room.displayDetails();
                System.out.println("Available : " + available);
                System.out.println("---------------------------");
            }
        }
    }
}


/* ================= MAIN APPLICATION ================= */

public class MyStayApp {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("           BOOK MY STAY APP          ");
        System.out.println("=====================================");
        System.out.println("Version : 4.0");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize search service
        SearchService searchService = new SearchService();

        // Guest searches available rooms
        searchService.searchAvailableRooms(inventory);

        System.out.println("\nSearch completed successfully.");
    }
}