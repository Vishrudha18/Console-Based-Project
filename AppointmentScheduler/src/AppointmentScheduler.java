import java.sql.*;
import java.util.Scanner;

public class AppointmentScheduler {

    // Database connection details
    static final String DB_URL = "jdbc:mysql://localhost:3306/appointment_system";
    static final String USER = "root";
    static final String PASS = "v1i8s1h1@2005"; // Replace with your MySQL root password

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
                System.out.println("Connected to the database.");

                while (true) {
                    System.out.println("\n1. Add Appointment");
                    System.out.println("\n2. Update Appointment");
                    System.out.println("\n3. Delete Appointment");
                    System.out.println("\n4. Search Appointment");
                    System.out.println("\n5. Exit");
                    System.out.print("Choose an option: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Clear buffer

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
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to add appointment
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

    // Rest of the methods (updateAppointment, deleteAppointment, searchAppointment)
    // remain the same.
    // Method to update appointment
    public static void updateAppointment(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter appointment ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        System.out.print("Enter new client name: ");
        String clientName = scanner.nextLine();
        System.out.print("Enter new appointment date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter new appointment time (HH:MM:SS): ");
        String time = scanner.nextLine();
        System.out.print("Enter new purpose: ");
        String purpose = scanner.nextLine();

        String query = "UPDATE appointments SET client_name = ?, appointment_date = ?, appointment_time = ?, purpose = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, clientName);
            pstmt.setDate(2, Date.valueOf(date));
            pstmt.setTime(3, Time.valueOf(time));
            pstmt.setString(4, purpose);
            pstmt.setInt(5, id);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Appointment updated successfully.");
            } else {
                System.out.println("Appointment ID not found.");
            }
        }
    }

    // Method to delete appointment
    public static void deleteAppointment(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter appointment ID to delete: ");
        int id = scanner.nextInt();

        String query = "DELETE FROM appointments WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Appointment deleted successfully.");
            } else {
                System.out.println("Appointment ID not found.");
            }
        }
    }

    // Method to search appointment
    public static void searchAppointment(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter appointment ID to search: ");
        int id = scanner.nextInt();

        String query = "SELECT * FROM appointments WHERE id = ?";
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
