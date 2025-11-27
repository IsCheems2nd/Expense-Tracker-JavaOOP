package backend.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:sqlite:data/expense.db";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL);
    }

    public static void init(){
        String sql = """
                CREATE TABLE IF NOT EXISTS transactions (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    datetime TEXT NOT NULL,
                    amount REAL NOT NULL,
                    category TEXT NOT NULL,
                    description TEXT
                    
                    );
                """;

        try (Connection conn = getConnection();
             Statement stm = conn.createStatement()) {
                stm.execute(sql);
             }catch (SQLException e){
                e.printStackTrace();
             }
        
    }

    public static void insertSampleData() {
        String sql = """
            INSERT INTO transactions (datetime, amount, category, description)
            VALUES
            ('2025-11-27T10:00', 1000, 'Salary', 'Monthly salary'),
            ('2025-11-28T15:30', 50, 'Food', 'Lunch'),
            ('2025-11-29T18:00', 200, 'Entertainment', 'Movie ticket');
        """;

        try (Connection conn = getConnection();
            Statement stm = conn.createStatement()) {
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }   

}
