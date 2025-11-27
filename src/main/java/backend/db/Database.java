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
                CREATE TALBE IF NOT EXISTS transactions (
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
}
