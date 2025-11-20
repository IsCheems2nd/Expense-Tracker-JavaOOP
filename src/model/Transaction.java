package model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private int id;
    private double amount;
    private String category;
    private String description;
    private LocalDateTime dateTime;

    public Transaction(int id, LocalDateTime dateTime,
     double amount, String category, String description){

        this.id = id;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.dateTime = dateTime;

    }

    public int getId(){
    return id;
    }

    public LocalDateTime getDateTime(){
    return dateTime;
    }

    public double getAmount(){
    return amount;
    }

    public String getCategory(){
    return category;
    }

    public String getdescription(){
    return description;
    }


    public String getFormattedDateTime(){
        DateTimeFormatter f = DateTimeFormatter.ofPattern("HH:mm:ss | dd/MM/yyyy");
        
        return dateTime.format(f);
    
    }
}

 