package view;
import controller.TransactionController;
import java.time.LocalDateTime;
import java.util.Scanner;
import model.Transaction;


public class ConsoleView {
    private TransactionController controller = new TransactionController();
    private Scanner sc = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("\nExpense Tracker");
            System.out.println("1. Add Transaction");
            System.out.println("2. View All Transactions");
            System.out.println("3. Delete Transaction");
            System.out.println("4. Exit");
            System.out.print("Choose: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addTransaction();
                case 2 -> showTransactions();
                case 3 -> deleteTransaction();
                case 4 -> { return; }
            }
        }
    }

    private void addTransaction() {
        System.out.print("Date (YYYY-MM-DD): ");
        LocalDateTime dateTime = LocalDateTime.parse(sc.nextLine());

        System.out.print("Amount: ");
        double amount = Double.parseDouble(sc.nextLine());

        System.out.print("Category: ");
        String cat = sc.nextLine();

        System.out.print("Note: ");
        String note = sc.nextLine();

        controller.addTransaction(dateTime, amount, cat, note);
        System.out.println("Added!");
    }

    private void showTransactions() {
        for (Transaction t : controller.getAllTransactions()) {
            System.out.println(t.getId() + " | " + t.getDateTime() + " | " + t.getAmount() 
                               + " | " + t.getCategory() + " | " + t.getDescription());
        }
    }

    private void deleteTransaction() {
        System.out.print("Enter ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (controller.deleteTransaction(id)) {
            System.out.println("Deleted.");
        } else {
            System.out.println("ID not found.");
        }
    }
}
