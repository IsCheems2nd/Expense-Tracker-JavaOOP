package controller;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
        for (int i = 0; i < transactions.size();i++){
            
            Transaction t = transactions.get(i);

            if (t.getId() == id ){
                transactions.remove(i);

            }
        }
        return false;
    }

    public boolean updateTransaction(int id, LocalDateTime newDateTime, double newAmount, String newCategory, String newDescription){
        
        Transaction t = findById(id);

        if (t == null){
            return false;
        }

        t.setDateTime(newDateTime);
        t.setAmount(newAmount);
        t.setCategory(newCategory);
        t.setDescription(newDescription);

        return true;
    }

    public Transaction findById(int id){
        return transactions.stream()
                .filter(t->t.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void saveToFile(String filename){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){ 
            //This does write.close(), helps prevent memory leaks

            for (Transaction t : transactions){
                String line = t.getId() + "," +
                              t.getDateTime().toString() + "," +
                              t.getAmount() + "," +
                              t.getCategory() + "," +
                              t.getDescription();
                
                writer.write(line);
                writer.newLine();                            
            
            }

            System.out.println("Saved Successfully!");

        } catch(IOException e) {
            System.err.println("Error saving file" + e.getMessage());
        }
    }


    public void loadFromFile(String filename){
        transactions.clear(); //clear list so it won't duplicate when we reload


        try(BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while((line = reader.readLine()) != null){ //Read transactions' data

                String[] parts = line.split(","); //Split with comma

                int id = Integer.parseInt(parts[0]); //Extract pieces (divided by comma)

                LocalDateTime dateTime = LocalDateTime.parse(parts[1]); //Extract ISO format instead of custom format for easier processing

                double amount = Double.parseDouble(parts[2]);
                String category = parts[3];
                String description = parts[4];
                
                Transaction t = new Transaction(id, dateTime, amount, category, description);


                transactions.add(t);

                if (id >= nextId){
                    nextId = id+1;

                }
            } 

            System.out.println("Loaded transacted from files.");
        } catch (IOException e) {
            System.out.println("(Error loading profile.)" + e.getMessage());
        }





    }
    
}