import java.io.*;
import java.util.*;

/**
 * UseCase12DataPersistenceRecovery
 *
 * Demonstrates persistence and recovery of system state
 * using serialization and file storage.
 *
 * @author Aditya Kadam
 * @version 12.0
 */


/* ================= SYSTEM STATE MODEL ================= */

class SystemState implements Serializable {

    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<String> bookingHistory;

    public SystemState() {

        inventory = new HashMap<>();
        bookingHistory = new ArrayList<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    public void displayState() {

        System.out.println("\nCurrent Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        System.out.println("\nBooking History:");

        for (String booking : bookingHistory) {
            System.out.println(booking);
        }
    }
}


/* ================= PERSISTENCE SERVICE ================= */

class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    // Save system state
    public void saveState(SystemState state) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(state);

            System.out.println("\nSystem state saved successfully.");

        } catch (IOException e) {

            System.out.println("Error saving system state: " + e.getMessage());
        }
    }

    // Load system state
    public SystemState loadState() {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("\nSystem state restored from file.");

            return (SystemState) in.readObject();

        } catch (Exception e) {

            System.out.println("\nNo previous state found. Initializing new system.");

            return new SystemState();
        }
    }
}


/* ================= MAIN APPLICATION ================= */

public class MyStayApp {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("         BOOK MY STAY APP            ");
        System.out.println("=====================================");
        System.out.println("Version : 12.0");

        PersistenceService persistence = new PersistenceService();

        // Load previous state
        SystemState state = persistence.loadState();

        // Simulate booking activity
        state.bookingHistory.add("RES-301 | Alice | Single Room");
        state.bookingHistory.add("RES-302 | Bob | Double Room");

        // Update inventory
        state.inventory.put("Single Room",
                state.inventory.get("Single Room") - 1);

        state.inventory.put("Double Room",
                state.inventory.get("Double Room") - 1);

        // Display system state
        state.displayState();

        // Save state before shutdown
        persistence.saveState(state);

        System.out.println("\nSystem shutdown complete.");
    }
}