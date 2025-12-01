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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import backend.model.db.Database;
import backend.model.Transaction;

public class TransactionController extends GenericController<Transaction> {

    private static final Map<String, Double> EXCHANGE_RATES;

    static {
        EXCHANGE_RATES = new HashMap<>();
        EXCHANGE_RATES.put("USD", 1.0);
        EXCHANGE_RATES.put("EUR", 1.16); //by 29/11
        EXCHANGE_RATES.put("VND", 0.000038);
    }

    public TransactionController() {
        super("transactions");
    }

    @Override
    protected Transaction mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new Transaction(
            rs.getInt("id"),
            LocalDateTime.parse(rs.getString("dateTime")),
            rs.getDouble("amount"),
            rs.getString("category"),
            rs.getString("description"),
            rs.getString("currency_code")
             );
    }

    @Override
    protected String getInsertSQL(){
        return "INSERT INTO transactions (datetime, amount, category, description, currency_code) VALUES (?, ?, ?, ?, ?)";
    }

    protected void setInsertParameters(PreparedStatement stmt, Transaction entity) throws SQLException{
        stmt.setString(1, entity.getDateTime().toString());
        stmt.setDouble( 2, entity.getAmount());
        stmt.setString(3, entity.getCategory());
        stmt.setString(4, entity.getDescription());
        stmt.setString(5, entity.getCurrencyCode());
        
    }

    @Override
    protected String getUpdateSQL(){
        return "UPDATE transactions SET amount = ?, categpry = ?, description = ?, currency_code = ? WHERE id = ?"; 
    }

    @Override
    protected void setUpdateParameters(PreparedStatement stmt, Transaction entity, int id) throws SQLException{
        double amountUSE = convertToBaseCurrency(entity.getAmount(), entity.getCurrencyCode());
        stmt.setDouble(1, amountUSE);
        stmt.setString(2, entity.getCategory());
        stmt.setString(3, entity.getDescription());
        stmt.setString(4, entity.getCurrencyCode());
        stmt.setInt(5,id);



    }


    private double convertToBaseCurrency(double amount, String currencyCode) {
        Double rate = EXCHANGE_RATES.get(currencyCode);
        if (rate == null) {
            System.err.println("Unknown currency code: " + currencyCode + ". Defaulting to 1.0 rate.");
            return amount;
        }
        return amount * rate;
    }

    public double getBaseCurrencyAmount(Transaction t) {
        return convertToBaseCurrency(t.getAmount(), t.getCurrencyCode());
    }

    public void addTransaction(LocalDateTime dateTime, double amount,
            String category, String description, String currencyCode) {

        String sql = "INSERT INTO transactions (datetime, amount, category, description, currency_code) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dateTime.toString());
            stmt.setDouble(2, amount);
            stmt.setString(3, category);
            stmt.setString(4, description);
            stmt.setString(5, currencyCode);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> list = new ArrayList<>();

        String sql = "SELECT * FROM transactions ORDER BY datetime DESC";

        try (Connection conn = Database.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Transaction t = new Transaction(
                        rs.getInt("id"),
                        LocalDateTime.parse(rs.getString("datetime")),
                        rs.getDouble("amount"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getString("currency_code")
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

        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTransaction(int id, double amount, String category, String description, String currencyCode) {

        // 1. Convert the amount to the base currency (USD) before saving
        double amountUSD = convertToBaseCurrency(amount, currencyCode);

        String sql = "UPDATE transactions SET amount = ?, category = ?, description = ?, currency_code = ? WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, amountUSD); // <-- Saving CONVERTED USD AMOUNT
            stmt.setString(2, category);
            stmt.setString(3, description);
            stmt.setString(4, currencyCode); // <-- Saving ORIGINAL CURRENCY CODE
            stmt.setInt(5, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Transaction findById(int id) {
        String sql = "SELECT * FROM transactions WHERE id = ?";

        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return null;
            }

            return new Transaction(
                    rs.getInt("id"),
                    LocalDateTime.parse(rs.getString("datetime")),
                    rs.getDouble("amount"),
                    rs.getString("category"),
                    rs.getString("description"),
                    rs.getString("currencyCode")
            );

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public double getCurrentBalance() {
        double total = 0.0;

        for (Transaction t : getAllTransactions()) {
            total += getBaseCurrencyAmount(t);
        }

        return total;
    }

    //Filters (done)
    public List<Transaction> filterByCategory(String category) {
        return getAllTransactions().stream()
                .filter(t -> t.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());

    }

    public List<Transaction> filterByDateRange(LocalDate startD, LocalDate endD) {
        return getAllTransactions().stream()
                .filter(t -> {
                    LocalDate date = t.getDateTime().toLocalDate();
                    return !date.isBefore(startD) && !date.isAfter(endD);
                }).collect(Collectors.toList());

    }

    public List<Transaction> sortByDate(boolean ascending) {
        return getAllTransactions().stream().sorted(ascending
                ? Comparator.comparing(Transaction::getDateTime)
                : Comparator.comparing(Transaction::getDateTime).reversed())
                .collect(Collectors.toList());

    }

    public List<Transaction> sortByAmount(boolean ascending) {
        return getAllTransactions().stream().sorted(ascending
                ? Comparator.comparingDouble(Transaction::getAmount)
                : Comparator.comparingDouble(Transaction::getAmount).reversed())
                .collect(Collectors.toList());

    }

    public List<Transaction> search(String keyword) {
        String kw = keyword.toLowerCase();
        return getAllTransactions().stream().filter(t
                -> t.getDescription().toLowerCase().contains(kw)
                || t.getCategory().toLowerCase().contains(kw)
        ).collect(Collectors.toList());

    }

    public List<Transaction> filterByType(boolean isIncome) {
        return getAllTransactions().stream()
                .filter(t -> isIncome ? t.getAmount() > 0 : t.getAmount() < 0)
                .collect(Collectors.toList());
    }

    public Set<String> getUniqueCategories() {
        return getAllTransactions().stream()
                .map(Transaction::getCategory)
                .collect(Collectors.toCollection(HashSet::new));
    }


}
