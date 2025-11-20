
import controller.TransactionController;
import java.time.LocalDateTime;
import model.Transaction;



public class Main {
    public static void main(String[] args) {
        TransactionController controller = new TransactionController();

        controller.addTransaction(LocalDateTime.now(), 10.5, "Food", "Lunch");
        controller.addTransaction(LocalDateTime.now(), 25, "Entertainment", "Games");


        for (Transaction t : controller.getAllTransactions()){
            System.out.println(t.getId() + " - " + t.getCategory() + " - " + t.getAmount() + " - " + t.getFormattedDateTime());
        }
    }
}
