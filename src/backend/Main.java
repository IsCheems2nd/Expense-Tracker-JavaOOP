
import controller.TransactionController;
import java.time.LocalDateTime;
import java.util.*;
import model.Transaction;


public class Main {
    public static void main(String[] args) {

        TransactionController controller = new TransactionController();
        Scanner sc = new Scanner(System.in);

        boolean run = true;

        controller.loadFromFile("data/transactions.csv");

        while(run){

            System.out.println("---------------Transaction Manager---------------");
            System.out.println("1. Add Transaction");
            System.out.println("2. View All Transaction");
            System.out.println("3. Update Transaction");
            System.out.println("4. Delete Transaction");
            System.out.println("5. Save to File");
            System.out.println("6. Load File");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            int choice;
            try{
                choice = sc.nextInt();
            } catch (InputMismatchException e){
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
                continue;
            }

            if (choice < 0 || choice > 6) {
                System.out.println("Choice out of range. Please try again.");
                continue;
            }

            switch(choice){
                case 1: addTransactionUI(controller,sc); break;
                case 2: viewAllUI(controller); break;
                case 3: updateTransactionUI(controller,sc); break;
                case 4: deleteTransactionUI(controller,sc); break;
                case 5: controller.saveToFile("data/transactions.csv"); System.out.println("Saved.");; break;
                case 6: controller.loadFromFile("data/transactions.csv"); System.out.println("Loaded.");; break;
                case 0: run = false; break;

                default:
                    System.out.println("Invalid Choice, Please choose again.");
            }
        }

 
    }

        private static void addTransactionUI(TransactionController controller, Scanner sc){
        System.out.print("Enter type (Income/Expense): ");
        String  category = sc.nextLine();

        System.out.print("Enter amount: ");
        Double amount = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter description: ");
        String description = sc.nextLine();

        LocalDateTime dateTime = LocalDateTime.now();

        controller.addTransaction(dateTime, amount, category, description);

        System.out.println("Transaction added successfully!!!");


    }

    private static void viewAllUI(TransactionController controller){
        List<Transaction> transactions = controller.getAllTransactions();
        if (transactions.isEmpty()){
            System.out.println("No existing transaction has been made.");
        } else {
            for (Transaction t : transactions){
                System.out.println(t);
            }
        }
        

    }


    private static void updateTransactionUI(TransactionController controller, Scanner sc){

        System.out.println("Enter Transaction's Id to edit/update: ");
        int id = sc.nextInt();
        sc.nextLine();

        Transaction t = controller.findById(id);
        if (t == null) {
            System.out.println("No transaction with such id.");
            return;
        } 

        
        System.out.println("Current transaction: ");
        System.out.println(t);

            System.out.println("\n--- Enter new values (leave empty to keep current) ---");

        System.out.print("New category (Income/Expense) [" + t.getCategory() + "]: ");
        String category = sc.nextLine();
        if (category.trim().isEmpty()) category = t.getCategory();

        System.out.print("New amount [" + t.getAmount() + "]: ");
        String amountStr = sc.nextLine();
        double amount = amountStr.trim().isEmpty() ? t.getAmount() : Double.parseDouble(amountStr);

        System.out.print("New description [" + t.getDescription() + "]: ");
        String desc = sc.nextLine();
        if (desc.trim().isEmpty()) desc = t.getDescription();

        controller.updateTransaction(id, amount, category, desc);

        System.out.println("Transaction updated successfully!");


    }

    private static void deleteTransactionUI(TransactionController controller, Scanner sc){
        System.out.print("Enter transaction ID to delete: ");

        int id = sc.nextInt();
        sc.nextLine();

        boolean success = controller.deleteTransaction(id);

        if(success){
            System.err.println("Transaction deleted successfully.");
        } else {
            System.err.println("Transaction not found.");
        }
    }
}

