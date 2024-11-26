package jdbc;

import java.sql.*;

public class update_tables {

    String city =  AdminService.adminmap.get("city");

    //----------------------------------------------------------------------------------------------------------------------

    public boolean update_mg(String streetname , String newname){
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE "+ city +" SET "+city+"_mw_name = ? WHERE Street = '"+ streetname +"'";
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

    public boolean street_status (String tablename){
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
                int count = rs.getInt(1);
                return (count > 0);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //----------------------------------------------------------------------------------------------------------------------

    public boolean addstreet(String mgname, String streetname){
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO "+city+" ("+city+"_mw_name, Street, emailid) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, mgname);
            pstmt.setString(2, streetname);
            pstmt.setString(3, (mgname.toLowerCase().replace(" ", "_")+"@gmail.com"));
            pstmt.executeUpdate();
            createstreettable(streetname);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createstreettable(String streetname){
        String tableName = streetname.toLowerCase().replace(" ", "_");
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "CREATE TABLE IF NOT EXISTS " + tableName + " (sno INT AUTO_INCREMENT PRIMARY KEY, ID VARCHAR(10), status ENUM('abnormality', 'working') DEFAULT 'working')";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addstreetlight(String tablename, String id){
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO "+tablename+" (ID ) VALUES ('"+id+"')";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //----------------------------------------------------------------------------------------------------------------------

    public boolean deletestreetlight(String tablename, String id){
        String sql = "DELETE FROM "+tablename+" WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deletestreet(String streetname){
        String sql = "DELETE FROM "+city+" WHERE street = ?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, streetname);
            int rowsAffected = preparedStatement.executeUpdate();

            String streettable = streetname.replace(" ","_");
            deletestreettable(streettable);

            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void deletestreettable (String tablename){
        String sql = "DROP TABLE IF EXISTS "+tablename;
        try (Connection conn = DatabaseConnection.getConnection()) {
            Statement statement = conn.createStatement();
            int result = statement.executeUpdate(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------------------------------------------------------------

    public boolean editprofile(String name, String username, String password, String email){
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE admin SET name = ? , userName =  ? , password =  ? , email_id =  ? WHERE city  =  '"+city+"'";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, email);
            pstmt.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //----------------------------------------------------------------------------------------------------------------------

}
