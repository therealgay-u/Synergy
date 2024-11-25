package jdbc;

import java.sql.*;

public class update_tables {

    String city =  AdminService.adminmap.get("city");

    public boolean update_mg(String streetnum, String newname){
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE "+ city +" SET "+city+"_mw_name = ? WHERE Street = 'delhi street "+streetnum+"'";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, newname);
            pstmt.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean steet_status(String tablename){
        if (count_abnormality(tablename)) {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String query = "UPDATE " + tablename + " SET status = 'working' WHERE status = 'abnormality' ";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public boolean count_abnormality(String tablename){
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT COUNT(*) FROM "+tablename+" WHERE status = 'abnormality'";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
