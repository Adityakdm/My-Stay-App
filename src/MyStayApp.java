import java.util.LinkedList;
import java.util.Queue;

/**
 * UseCase5BookingRequestQueue
 *
 * Demonstrates how booking requests are collected using a Queue
 * to preserve First-Come-First-Served (FIFO) ordering.
 *
 * No room allocation or inventory modification occurs in this stage.
 *
 * @author Aditya Kadam
 * @version 5.0
 */

/* ================= RESERVATION MODEL ================= */

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

    public void displayRequest() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}


/* ================= BOOKING REQUEST QUEUE ================= */

class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add booking request
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    // Display queued requests
    public void displayRequests() {

        System.out.println("\nCurrent Booking Request Queue:");

        for (Reservation r : requestQueue) {
            r.displayRequest();
        }
    }
}


/* ================= MAIN APPLICATION ================= */

public class MyStayApp {

    public static void main(String[] args) {

        System.out.println("=========================================");
        System.out.println("           BOOK MY STAY APP              ");
        System.out.println("=========================================");
        System.out.println("Version : 5.0");

        // Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Guests submit booking requests
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Display requests in FIFO order
        bookingQueue.displayRequests();

        System.out.println("\nRequests stored in arrival order (FIFO).");
        System.out.println("No inventory allocation performed yet.");
    }
}