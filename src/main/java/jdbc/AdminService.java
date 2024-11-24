package jdbc;

import java.sql.*;
import java.util.HashMap;

public class AdminService {
    public static HashMap<String, String> adminmap = new HashMap<>();

    public boolean newsignin(String key){
        String query = "SELECT City FROM admin WHERE special_code = ?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1 , key);
            ResultSet rs = pstmt.executeQuery();
            String city = null;
            if (rs.next()) {
                city = rs.getString("City");
            }
            if (city != null) {
                String createTableSQL = "CREATE TABLE IF NOT EXISTS `" + city + "` (" +
                        "`"+city+"_dw_name` VARCHAR(75), " +
                        "`street` VARCHAR(100), " +
                        "`emailid` VARCHAR(100))";

                Statement stmt = conn.createStatement();
                stmt.executeUpdate(createTableSQL);
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isProductKeyValid(String productkey) {
        String query = "SELECT Special_Code FROM admin WHERE Special_Code = ? AND username IS NULL";
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, productkey);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean login(String username, String password) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT password FROM admin WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                if(rs.getString("password").equals(password)){
                    query = "SELECT * FROM admin WHERE username = ?";
                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, username);
                    rs = pstmt.executeQuery();
                    if (rs.next()) {
                        adminmap.put("city", rs.getString("city"));
                        adminmap.put("username", rs.getString("username"));
                        adminmap.put("password", rs.getString("password"));
                        adminmap.put("name", rs.getString("name"));
                        adminmap.put("email_id", rs.getString("email_id"));
                    }

                    return true;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean signup(String username, String password, String name, String email, String productKey) {
        if (isProductKeyValid(productKey)) {
	        try (Connection conn = DatabaseConnection.getConnection()) {

	        	String query = "UPDATE admin SET username = ?, password = ?, name = ?, email_Id = ? WHERE special_Code = ?";
	
	        	PreparedStatement pstmt = conn.prepareStatement(query);
	            pstmt.setString(1, username);
	            pstmt.setString(2, password);
	            pstmt.setString(3, name);
	            pstmt.setString(4, email);
	            pstmt.setString(5, productKey);
	
	            pstmt.executeUpdate();

	            return true;
	        } 
	        catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
        else{
            return false;
        }
    }

    public void assignWorkers(String MW_name) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE name FROM streets WHERE assigned_worker IS ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, MW_name);

            stmt.executeUpdate();

//            while (rs.next()) {
//                String streetName = rs.getString("name");
//                System.out.println("Unassigned street: " + streetName);
//                // Logic to assign workers via email or UI
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    public void checkStreetLights() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT street_name, id, status FROM street_lights WHERE status != 'WORKING'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String streetName = rs.getString("street_name");
                int id = rs.getInt("id");
                String status = rs.getString("status");

                System.out.println("Issue detected: Street: " + streetName + ", Light ID: " + id + ", Status: " + status);
                notifyMaintenanceWorker(streetName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void notifyMaintenanceWorker(String streetName) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT email FROM maintenance_workers WHERE street_assigned = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, streetName);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String email = rs.getString("email");
                System.out.println("Notified worker via email: " + email);
                // Add email notification logic here
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    */
}
