import java.util.*;

/**
 * UseCase7AddOnServiceSelection
 *
 * Demonstrates how optional add-on services can be attached to
 * an existing reservation without modifying booking or inventory logic.
 *
 * @author Aditya Kadam
 * @version 7.0
 */


/* ================= SERVICE MODEL ================= */

class AddOnService {

    private String serviceName;
    private double price;

    public AddOnService(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }

    public void displayService() {
        System.out.println(serviceName + " - $" + price);
    }
}


/* ================= ADD-ON SERVICE MANAGER ================= */

class AddOnServiceManager {

    // Map reservationId -> list of services
    private Map<String, List<AddOnService>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {

        reservationServices
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println("Added service: " + service.getServiceName()
                + " to reservation " + reservationId);
    }

    // Display services for reservation
    public void displayServices(String reservationId) {

        List<AddOnService> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        System.out.println("\nServices for Reservation " + reservationId + ":");

        for (AddOnService service : services) {
            service.displayService();
        }
    }

    // Calculate total cost
    public double calculateTotalCost(String reservationId) {

        List<AddOnService> services = reservationServices.get(reservationId);

        double total = 0;

        if (services != null) {
            for (AddOnService service : services) {
                total += service.getPrice();
            }
        }

        return total;
    }
}


/* ================= MAIN APPLICATION ================= */

public class MyStayApp {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("         BOOK MY STAY APP            ");
        System.out.println("=====================================");
        System.out.println("Version : 7.0\n");

        // Example reservation
        String reservationId = "RES-101";

        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Guest selects services
        serviceManager.addService(reservationId,
                new AddOnService("Breakfast", 20));

        serviceManager.addService(reservationId,
                new AddOnService("Airport Pickup", 40));

        serviceManager.addService(reservationId,
                new AddOnService("Spa Access", 60));

        // Display selected services
        serviceManager.displayServices(reservationId);

        // Calculate additional cost
        double total = serviceManager.calculateTotalCost(reservationId);

        System.out.println("\nTotal Add-On Cost: $" + total);
    }
}