
package backend;
import java.util.InputMismatchException;
import java.util.Scanner;

import backend.controller.TransactionController;
import backend.db.Database;
import backend.ui.TransactionUI;

public class Main {
    public static void main(String[] args) {

        TransactionController controller = new TransactionController();
        Scanner sc = new Scanner(System.in);

        Database.init();

        boolean run = true;
        
        

        while(run){

            System.out.println("---------------Transaction Manager---------------");
            System.out.println("1. Add Transaction");
            System.out.println("2. View All Transaction");
            System.out.println("3. Update Transaction");
            System.out.println("4. Delete Transaction");
            System.out.println("5. Filter/Sort/Search Transactions");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            int choice;
            try{
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e){
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
                continue;
            }

            if (choice < 0 || choice > 5) {
                System.out.println("Choice out of range. Please try again.");
                continue;
            }

            switch(choice){
                case 1: TransactionUI.addTransactionUI(controller,sc); break;
                case 2: TransactionUI.viewAllUI(controller); break;
                case 3: TransactionUI.updateTransactionUI(controller,sc); break;
                case 4: TransactionUI.deleteTransactionUI(controller,sc); break;
                case 5: TransactionUI.filterSortSearchUI(controller, sc); break;
                case 0: run = false; break;

                default:
                    System.out.println("Invalid Choice, Please choose again.");
            }
        }

        sc.close();
    }

    
}

