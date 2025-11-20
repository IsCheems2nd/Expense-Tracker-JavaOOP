package controller;
import java.time.LocalDateTime;
import java.util.*;
import model.Transaction;

public class TransactionController {
    private List<Transaction> transactions = new ArrayList<>();
    private int nextId = 1;


    public void addTransaction(LocalDateTime dateTime, double amount,
     String category, String description){
        Transaction t = new Transaction(nextId++,dateTime,
                                    amount,category,description);
        transactions.add(t);

    }

    public List<Transaction> getAllTransactions(){
        return transactions;
    }


    public boolean deleteTransaction(int id){
        return transactions.removeIf(t -> t.getId() == id);
    }

    public Transaction findById(int id){
        return transactions.stream()
                .filter(t->t.getId() == id)
                .findFirst()
                .orElse(null);
    }
}