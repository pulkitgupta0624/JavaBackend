import java.sql.*;

public class JDBCDemo {
    private static final String url = "jdbc:mysql://localhost:3306/jdbc";
    private static final String user = "root";
    private static final String password = "Puki@2403@0624";

    public static void main(String[] args) {
        try(Connection conn = DriverManager.getConnection(url, user, password);){
            System.out.println("Connected to the database");
            insertStudents(conn, "Alice", "alice@gmail.com");
            updateStudents(conn, 2, "Bob", "alice@gmail.com");
            selectStudents(conn);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    private static void insertStudents(Connection conn, String name, String email){
        String sql = "INSERT INTO student (name, email) VALUES ('" + name + "', '" + email + "')";
        try(Statement stmt = conn.createStatement()) {
            int rows = stmt.executeUpdate(sql);
            System.out.println("Inserted " + rows + " records into the database");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void selectStudents(Connection conn){
        String sql = "SELECT * FROM student";
        try (Statement stmt = conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(sql);
            System.out.println("Retrieved records from the database: ");
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                System.out.println(id + " : " + name + " : " + email);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void updateStudents(Connection conn, int id, String name, String email){
        String sql = "UPDATE student SET name = ?, email = ? WHERE id = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setInt(3, id);
            int rows = pstmt.executeUpdate();
            System.out.println("UPDATED: " + rows);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void deleteStudent(Connection conn, int id) {
        String sql = "DELETE FROM student WHERE id = " + id;
        try (Statement stmt = conn.createStatement()) {
            int rows = stmt.executeUpdate(sql);
            System.out.println("DELETED: " + rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

//Connection conn = null;
//        try {
//conn = DriverManager.getConnection(url, user, password);
//            System.out.println("Connected to database successfully");
//        } catch (SQLException e) {
//        e.printStackTrace();
//        } finally {
//                try {
//                conn.close();
//                System.out.println("Connection closed successfully");
//            } catch (SQLException e) {
//        throw new RuntimeException(e);
//            }
//                    }
