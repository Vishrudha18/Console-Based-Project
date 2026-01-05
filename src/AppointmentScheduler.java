import java.sql.*;
import java.util.Scanner;

public class AppointmentScheduler {

    static final String DB_URL = System.getenv("DB_URL");
    static final String USER = System.getenv("DB_USER");
    static final String PASS = System.getenv("DB_PASS");


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (DB_URL == null || USER == null || PASS == null) {
            System.out.println("Environment variables not set!");
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
                System.out.println("Connected to the database.");

                while (true) {
                    System.out.println("\n1. Add Appointment");
                    System.out.println("2. Update Appointment");
                    System.out.println("3. Delete Appointment");
                    System.out.println("4. Search Appointment");
                    System.out.println("5. Exit");
                    System.out.print("Choose an option: ");

                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {
                        case 1:
                            addAppointment(connection, scanner);
                            break;
                        case 2:
                            updateAppointment(connection, scanner);
                            break;
                        case 3:
                            deleteAppointment(connection, scanner);
                            break;
                        case 4:
                            searchAppointment(connection, scanner);
                            break;
                        case 5:
                            System.out.println("Exiting...");
                            return;
                        default:
                            System.out.println("Invalid choice.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------------- ADD ----------------
    public static void addAppointment(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter client name: ");
        String clientName = scanner.nextLine();
        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter appointment time (HH:MM:SS): ");
        String time = scanner.nextLine();
        System.out.print("Enter purpose: ");
        String purpose = scanner.nextLine();

        String query = "INSERT INTO appointments (client_name, appointment_date, appointment_time, purpose) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, clientName);
            pstmt.setDate(2, Date.valueOf(date));
            pstmt.setTime(3, Time.valueOf(time));
            pstmt.setString(4, purpose);
            pstmt.executeUpdate();
            System.out.println("Appointment added successfully.");
        }
    }

    // ---------------- SHOW ALL ----------------
    public static void showAllAppointments(Connection connection) throws SQLException {
        String query = "SELECT * FROM appointments";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\n--- Available Appointments ---");
            while (rs.next()) {
                System.out.println(
                        "ID: " + rs.getInt("id") +
                        " | Name: " + rs.getString("client_name") +
                        " | Date: " + rs.getDate("appointment_date") +
                        " | Time: " + rs.getTime("appointment_time") +
                        " | Purpose: " + rs.getString("purpose")
                );
            }
        }
    }

    // ---------------- UPDATE ----------------
    public static void updateAppointment(Connection connection, Scanner scanner) throws SQLException {

        showAllAppointments(connection);

        System.out.print("\nEnter appointment ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter new client name: ");
        String clientName = scanner.nextLine();
        System.out.print("Enter new appointment date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter new appointment time (HH:MM:SS): ");
        String time = scanner.nextLine();
        System.out.print("Enter new purpose: ");
        String purpose = scanner.nextLine();

        String query = "UPDATE appointments SET client_name=?, appointment_date=?, appointment_time=?, purpose=? WHERE id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, clientName);
            pstmt.setDate(2, Date.valueOf(date));
            pstmt.setTime(3, Time.valueOf(time));
            pstmt.setString(4, purpose);
            pstmt.setInt(5, id);

            int rows = pstmt.executeUpdate();
            if (rows > 0)
                System.out.println("Appointment updated successfully.");
            else
                System.out.println("Invalid ID.");
        }
    }

    // ---------------- DELETE ----------------
    public static void deleteAppointment(Connection connection, Scanner scanner) throws SQLException {

        showAllAppointments(connection);

        System.out.print("\nEnter appointment ID to delete: ");
        int id = scanner.nextInt();

        String query = "DELETE FROM appointments WHERE id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);

            int rows = pstmt.executeUpdate();
            if (rows > 0)
                System.out.println("Appointment deleted successfully.");
            else
                System.out.println("Invalid ID.");
        }
    }

    // ---------------- SEARCH ----------------
    public static void searchAppointment(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter appointment ID to search: ");
        int id = scanner.nextInt();

        String query = "SELECT * FROM appointments WHERE id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Client Name: " + rs.getString("client_name"));
                System.out.println("Date: " + rs.getDate("appointment_date"));
                System.out.println("Time: " + rs.getTime("appointment_time"));
                System.out.println("Purpose: " + rs.getString("purpose"));
            } else {
                System.out.println("Appointment not found.");
            }
        }
    }
}
