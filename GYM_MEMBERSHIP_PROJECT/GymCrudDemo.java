import java.sql.*;
import java.util.Scanner;

public class GymCrudDemo {

    public static void main(String[] args) {

        // Database connection details
        String url = "jdbc:mysql://localhost:3306/gymdb";
        String user = "root";     // your MySQL username
        String pass = "11810523"; // your MySQL password

        Scanner sc = new Scanner(System.in);

        try {
            // Step 1: Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Step 2: Connect to the database
            Connection con = DriverManager.getConnection(url, user, pass);
            System.out.println("Connected to database successfully!");

            // Step 3: Menu loop
            while (true) {
                System.out.println("\n=== GYM MEMBERSHIP MANAGEMENT ===");
                System.out.println("1. Add Member");
                System.out.println("2. Show Members");
                System.out.println("3. Add Membership Plan");
                System.out.println("4. Show Plans");
                System.out.println("5. Add Payment Record");
                System.out.println("6. Show Payments");
                System.out.println("7. Exit");
                System.out.print("Enter your choice: ");
                int ch = sc.nextInt();

                switch (ch) {

                    // ---------------- Add Member ----------------
                    case 1:
                        System.out.print("Enter Member ID: ");
                        int mid = sc.nextInt();
                        sc.nextLine(); // consume newline
                        System.out.print("Enter Member Name: ");
                        String mname = sc.nextLine();
                        System.out.print("Enter Phone Number: ");
                        String phone = sc.nextLine();

                        String q1 = "INSERT INTO member VALUES (?, ?, ?)";
                        PreparedStatement ps1 = con.prepareStatement(q1);
                        ps1.setInt(1, mid);
                        ps1.setString(2, mname);
                        ps1.setString(3, phone);
                        ps1.executeUpdate();
                        System.out.println("Member added successfully!");
                        ps1.close();
                        break;

                    // ---------------- Show Members ----------------
                    case 2:
                        String q2 = "SELECT * FROM member";
                        Statement st1 = con.createStatement();
                        ResultSet rs1 = st1.executeQuery(q2);

                        System.out.println("\nID\tName\t\tPhone");
                        System.out.println("--------------------------------");
                        while (rs1.next()) {
                            System.out.println(rs1.getInt("mid") + "\t" +
                                               rs1.getString("name") + "\t\t" +
                                               rs1.getString("phone"));
                        }
                        rs1.close();
                        st1.close();
                        break;

                    // ---------------- Add Plan ----------------
                    case 3:
                        System.out.print("Enter Plan ID: ");
                        int pid = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Plan Type: ");
                        String type = sc.nextLine();
                        System.out.print("Enter Fee: ");
                        double fee = sc.nextDouble();
                        System.out.print("Enter Duration (in months): ");
                        int duration = sc.nextInt();

                        String q3 = "INSERT INTO membership VALUES (?, ?, ?, ?)";
                        PreparedStatement ps2 = con.prepareStatement(q3);
                        ps2.setInt(1, pid);
                        ps2.setString(2, type);
                        ps2.setDouble(3, fee);
                        ps2.setInt(4, duration);
                        ps2.executeUpdate();
                        System.out.println("Plan added successfully!");
                        ps2.close();
                        break;

                    // ---------------- Show Plans ----------------
                    case 4:
                        String q4 = "SELECT * FROM membership";
                        Statement st2 = con.createStatement();
                        ResultSet rs2 = st2.executeQuery(q4);

                        System.out.println("\nPlanID\tType\t\tFee\tDuration");
                        System.out.println("----------------------------------------------");
                        while (rs2.next()) {
                            System.out.println(rs2.getInt("ms_id") + "\t" +
                                               rs2.getString("type") + "\t\t" +
                                               rs2.getDouble("fee") + "\t" +
                                               rs2.getInt("duration"));
                        }
                        rs2.close();
                        st2.close();
                        break;

                    // ---------------- Add Payment ----------------
                    case 5:
                        System.out.print("Enter Payment ID: ");
                        int payid = sc.nextInt();
                        System.out.print("Enter Member ID: ");
                        int memid = sc.nextInt();
                        System.out.print("Enter Plan ID: ");
                        int planid = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Date (YYYY-MM-DD): ");
                        String date = sc.nextLine();

                        String q5 = "INSERT INTO payment VALUES (?, ?, ?, ?)";
                        PreparedStatement ps3 = con.prepareStatement(q5);
                        ps3.setInt(1, payid);
                        ps3.setInt(2, memid);
                        ps3.setInt(3, planid);
                        ps3.setString(4, date);
                        ps3.executeUpdate();
                        System.out.println("Payment record added!");
                        ps3.close();
                        break;

                    // ---------------- Show Payments ----------------
                    case 6:
                        String q6 = "SELECT p.pay_id, m.name, ms.type, p.date " +
                                    "FROM payment p " +
                                    "JOIN member m ON p.mid = m.mid " +
                                    "JOIN membership ms ON p.ms_id = ms.ms_id";
                        Statement st3 = con.createStatement();
                        ResultSet rs3 = st3.executeQuery(q6);

                        System.out.println("\nPayID\tMember\t\tPlan\tDate");
                        System.out.println("-----------------------------------------");
                        while (rs3.next()) {
                            System.out.println(rs3.getInt("pay_id") + "\t" +
                                               rs3.getString("name") + "\t\t" +
                                               rs3.getString("type") + "\t" +
                                               rs3.getString("date"));
                        }
                        rs3.close();
                        st3.close();
                        break;

                    // ---------------- Exit ----------------
                    case 7:
                        System.out.println("Exiting program... Goodbye!");
                        con.close();
                        sc.close();
                        System.exit(0);

                    default:
                        System.out.println("Invalid choice, please try again!");
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
