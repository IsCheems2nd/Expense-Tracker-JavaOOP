package backend.controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import backend.db.Database;
import backend.model.Transaction;

public class TransactionController {
    

  
    public void addTransaction(LocalDateTime dateTime, double amount,
        String category, String description) {

        String sql = "INSERT INTO transactions (datetime, amount, category, description) VALUES (?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dateTime.toString());
            stmt.setDouble(2, amount);
            stmt.setString(3, category);
            stmt.setString(4, description);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Transaction> getAllTransactions() {
        List<Transaction> list = new ArrayList<>();

        String sql = "SELECT * FROM transactions ORDER BY datetime DESC";

        try (Connection conn = Database.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Transaction t = new Transaction(
                    rs.getInt("id"),
                    LocalDateTime.parse(rs.getString("datetime")),
                    rs.getDouble("amount"),
                    rs.getString("category"),
                    rs.getString("description")
                );

                list.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }



    public boolean deleteTransaction(int id) {
        String sql = "DELETE FROM transactions WHERE id = ?";

        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean updateTransaction(int id, double newAmount, String newCategory, String newDescription) {

        String sql = "UPDATE transactions SET amount = ?, category = ?, description = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, newAmount);
            stmt.setString(2, newCategory);
            stmt.setString(3, newDescription);
            stmt.setInt(4, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public Transaction findById(int id) {
        String sql = "SELECT * FROM transactions WHERE id = ?";

        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) return null;

            return new Transaction(
                rs.getInt("id"),
                LocalDateTime.parse(rs.getString("datetime")),
                rs.getDouble("amount"),
                rs.getString("category"),
                rs.getString("description")
            );

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public double getCurrentBalance() {
    // SQL uses the SUM aggregate function on the 'amount' column
        String sql = "SELECT SUM(amount) AS total_balance FROM transactions";

        try (Connection conn = Database.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getDouble("total_balance"); 
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0.0; 
    }

    //Filters (done)

    public List<Transaction> filterByCategory(String category){
        return getAllTransactions().stream()
                .filter(t -> t.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());

    }

    public List<Transaction> filterByDateRange(LocalDate startD, LocalDate endD){
        return getAllTransactions().stream()
                .filter(t->{
                LocalDate date = t.getDateTime().toLocalDate();
                return !date.isBefore(startD) && !date.isAfter(endD);
        }).collect(Collectors.toList());

    }
    
    public List<Transaction> sortByDate(boolean ascending){
        return getAllTransactions().stream().sorted(ascending
            ? Comparator.comparing(Transaction::getDateTime)
            : Comparator.comparing(Transaction::getDateTime).reversed())
            .collect(Collectors.toList());

        
                        
    }
    
    public List<Transaction> sortByAmount(boolean ascending){
        return getAllTransactions().stream().sorted(ascending
            ? Comparator.comparingDouble(Transaction::getAmount)
            : Comparator.comparingDouble(Transaction::getAmount).reversed())
            .collect(Collectors.toList());

    }

    
    public List<Transaction> search(String keyword){
        String kw = keyword.toLowerCase();
        return getAllTransactions().stream().filter(t->
            t.getDescription().toLowerCase().contains(kw) || 
            t.getCategory().toLowerCase().contains(kw)
        ).collect(Collectors.toList());

        
    }
    
}