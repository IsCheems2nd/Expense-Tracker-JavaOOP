import controller.TransactionController;
import java.time.LocalDateTime;
import model.Transaction;

public class Main {
    public static void main(String[] args) {

        TransactionController controller = new TransactionController();

        // ---------------------------------------------------------
        // 1. LOAD the existing CSV file into the ArrayList
        // ---------------------------------------------------------
        System.out.println("Loading file...");

        controller.loadFromFile("data/transactions.csv");

        System.out.println("Loaded transactions:");
        for (Transaction t : controller.getAllTransactions()) {
            System.out.println(t.getId() + " | " + t.getCategory() + " | " + t.getAmount());
        }

        // ---------------------------------------------------------
        // 2. UPDATE a specific transaction by ID
        // ---------------------------------------------------------
        System.out.println("\nUpdating transaction with ID = 2 ...");

        boolean updated = controller.updateTransaction(
                2,                                       // ID you want to update
                LocalDateTime.now(),                     // new date/time
                500.5,                                   // new amount
                "Shopping",                              // new category
                "Bought new shoes"                       // new note
        );

        if (updated) {
            System.out.println("Update successful!");
        } else {
            System.out.println("Update failed! ID not found.");
        }

        // ---------------------------------------------------------
        // 3. SAVE the updated list back to CSV
        // ---------------------------------------------------------
        System.out.println("\nSaving updated data to file...");
        controller.saveToFile("data/transactions.csv");
        System.out.println("Save completed!");

        // ---------------------------------------------------------
        // 4. Show all final transactions
        // ---------------------------------------------------------
        System.out.println("\nFinal transactions in memory:");
        for (Transaction t : controller.getAllTransactions()) {
            System.out.println(
                    t.getId() + " | "
                    + t.getCategory() + " | "
                    + t.getDescription() + " | "
                    + t.getAmount()
            );
        }
    }
}
// Hard coded testing