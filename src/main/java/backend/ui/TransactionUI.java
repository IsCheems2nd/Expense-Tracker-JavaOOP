package backend.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import backend.controller.TransactionController;
import backend.model.Transaction;

public class TransactionUI {
        public static void addTransactionUI(TransactionController controller, Scanner sc) {
        
        System.out.print("Enter category: ");
        String category = sc.nextLine().trim();
        if(category.isEmpty()) category = "Uncategorized";

        double amount = -1;
        while(amount < 0) {
            System.out.print("Enter amount: ");
            String input = sc.nextLine().trim();
            try {
                amount = Double.parseDouble(input);
                if(amount < 0) System.out.println("Amount cannot be negative.");
            } catch(NumberFormatException e) {
                System.out.println("Invalid number, please enter a valid amount.");
            }
        }

        System.out.print("Enter description: ");
        String description = sc.nextLine().trim();
        if(description.isEmpty()) description = "No description";

        LocalDateTime dateTime = LocalDateTime.now();
        controller.addTransaction(dateTime, amount, category, description);

        System.out.println("Transaction added successfully!!!");
    }


    public static void viewAllUI(TransactionController controller){
        List<Transaction> transactions = controller.getAllTransactions();
        if (transactions.isEmpty()){
            System.out.println("No existing transaction has been made.");
        } else {
            for (Transaction t : transactions){
                System.out.println(t);
            }
        }
        

    }


    public static void updateTransactionUI(TransactionController controller, Scanner sc){

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

        System.out.print("New category (Income/Expense) ["+t.getCategory()+"]: ");
        String category = sc.nextLine();
        if (category.trim().isEmpty()) category = t.getCategory();

        System.out.print("New amount ["+t.getAmount()+"]: ");
        String amountStr = sc.nextLine();
        double amount = amountStr.trim().isEmpty() ? t.getAmount() : Double.parseDouble(amountStr);

        System.out.print("New description ["+t.getDescription()+"]: ");
        String desc = sc.nextLine();
        if (desc.trim().isEmpty()) desc = t.getDescription();

        controller.updateTransaction(id, amount, category, desc);

        System.out.println("Transaction updated successfully!");


    }

    public static void deleteTransactionUI(TransactionController controller, Scanner sc){
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


    public static void filterSortSearchUI(TransactionController controller,Scanner sc){
        boolean turnback = false;

        while (!turnback){
            System.out.println("---------------Filter / Sort / Search---------------");
            System.out.println("1. Filter by category");
            System.out.println("2. Filter by date range");
            System.out.println("3. Sort by date");
            System.out.println("4. Sort by amount");
            System.out.println("5. Search keywork");
            System.out.println("0. Back to Menu");
            System.out.print("Choose: ");
            
            int choice1;
            try{
                choice1 = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e){
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
                continue;
            }

            if (choice1 < 0 || choice1 > 6) {
                System.out.println("Choice out of range. Please try again.");
                continue;
            }

            switch(choice1){
            case 1: System.out.print("Enter Category: ");
            String Category = sc.nextLine().trim();  
            displayTransaction(controller.filterByCategory(Category));
            break;

            case 2: try{System.out.print("Enter start date (yyyy-MM-dd): ");
            LocalDate start = LocalDate.parse(sc.nextLine().trim());
            System.out.print("Enter end date (yyyy-MM-dd): ");
            LocalDate end = LocalDate.parse(sc.nextLine().trim());
            displayTransaction(controller.filterByDateRange(start, end));
            
            }catch (Exception e) {
                System.out.println("Invalid date format. use yyyy-MM-dd");
            } break;

            case 3: System.out.print("Sort date ascending?(y/n): ");
            boolean ascDate = sc.nextLine().trim().equalsIgnoreCase("y");
            displayTransaction(controller.sortByDate(ascDate));
            break;

            case 4: System.out.print("Sort amount ascending?(y/n): ");
            boolean ascAmount = sc.nextLine().trim().equalsIgnoreCase("y");
            displayTransaction(controller.sortByDate(ascAmount));
            break;

            case 5: System.out.print("Enter keyword to search: ");
            String keyword = sc.nextLine().trim();
            displayTransaction(controller.search(keyword));
            break;

            case 0:
                turnback = true;
                break;

            default:
                System.out.println("Invalid option.");

            }   
        }
    }

    public static void displayTransaction(List<Transaction> transactions){
        if (transactions.isEmpty()){
            System.out.println("No such transaction found.");
        } else {
            for (Transaction t : transactions){
                System.out.println(t);
            }
        }
    }
}