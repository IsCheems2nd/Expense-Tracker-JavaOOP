package backend.model;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private int id;
    private double amount;
    private String category;
    private String description;
    private LocalDateTime dateTime;
    private LocalDate date;

    @Override
    public String toString(){
        return String.format("ID: %d | %s | %.2f | %s | %s",
                             id,
                             getFormattedDateTime(),  // formatted date/time
                             amount,
                             category,
                             description);
    }

    public Transaction(int id, LocalDateTime dateTime,
     double amount, String category, String description){

        this.id = id;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.dateTime = dateTime;
        this.date = dateTime.toLocalDate();

    }



    public int getId(){
        return id;
    }

    public LocalDateTime getDateTime(){
        return dateTime;
    }

    public LocalDate getDate(){
        return date;
    }

    public double getAmount(){
        return amount;
    }

    public String getCategory(){
        return category;
    }

    public String getDescription(){
        return description;
    }


    public String getFormattedDateTime(){
        DateTimeFormatter f = DateTimeFormatter.ofPattern("HH:mm:ss | dd/MM/yyyy");
        
        return dateTime.format(f);
    
    }

    public void setDateTime(LocalDateTime dateTime){
        this.dateTime = dateTime;
        this.date = dateTime.toLocalDate();
    }
    public void setAmount(double amount){
        this.amount = amount;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public void setDescription(String description){
        this.description = description;
    }
}

 