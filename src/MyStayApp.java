import java.util.ArrayList;
import java.util.List;

/**
 * UseCase8BookingHistoryReport
 *
 * Demonstrates how confirmed reservations are stored in booking history
 * and how reports can be generated from historical data.
 *
 * @author Aditya Kadam
 * @version 8.0
 */


/* ================= RESERVATION MODEL ================= */

class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String guestName,
                       String roomType, String roomId) {

        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public void displayReservation() {

        System.out.println("Reservation ID : " + reservationId);
        System.out.println("Guest Name     : " + guestName);
        System.out.println("Room Type      : " + roomType);
        System.out.println("Room ID        : " + roomId);
        System.out.println("----------------------------------");
    }
}


/* ================= BOOKING HISTORY ================= */

class BookingHistory {

    private List<Reservation> reservations;

    public BookingHistory() {
        reservations = new ArrayList<>();
    }

    // Add confirmed reservation
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    // Retrieve all reservations
    public List<Reservation> getReservations() {
        return reservations;
    }
}


/* ================= REPORT SERVICE ================= */

class BookingReportService {

    public void displayAllBookings(BookingHistory history) {

        System.out.println("\n===== BOOKING HISTORY REPORT =====\n");

        for (Reservation r : history.getReservations()) {
            r.displayReservation();
        }
    }

    public void displaySummary(BookingHistory history) {

        int totalBookings = history.getReservations().size();

        System.out.println("\n===== SUMMARY REPORT =====");
        System.out.println("Total Confirmed Bookings : " + totalBookings);
    }
}


/* ================= MAIN APPLICATION ================= */

public class MyStayApp {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("         BOOK MY STAY APP            ");
        System.out.println("=====================================");
        System.out.println("Version : 8.0");

        BookingHistory history = new BookingHistory();

        // Simulated confirmed reservations
        history.addReservation(new Reservation(
                "RES-101", "Alice", "Single Room", "SIN-201"));

        history.addReservation(new Reservation(
                "RES-102", "Bob", "Double Room", "DBL-305"));

        history.addReservation(new Reservation(
                "RES-103", "Charlie", "Suite Room", "SUI-401"));

        BookingReportService reportService = new BookingReportService();

        // Admin views full booking history
        reportService.displayAllBookings(history);

        // Admin views summary report
        reportService.displaySummary(history);
    }
}