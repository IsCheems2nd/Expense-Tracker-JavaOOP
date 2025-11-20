package view;
import controller.TransactionController;
import java.util.*;
public class ConsoleView {
    private TransactionController controller = new TransactionController();
    private Scanner sc =  new Scanner(System.in);


    public void start(){
        while (true) { 
            
            System.out.println("\nExpense Tracker");
            System.out.println("1. Add Transaction");
            System.out.println("2. View All Transactions");
            System.out.println("3. Delete Transaction");
            System.out.println("4. Exit");
            System.out.println("Choose: ");

            int choice = sc.nextInt();
            sc.nextLine();
            
            switch(choice){
                case 1 -> addTransaction();
                case 2 -> showTransaction();
                case 3 -> deleteTransaction();
                case 4 -> {return; }

            }
            
        }


    }

    private void addTransaction(){
        
    }

    private void showTransaction(){
        
    }

    private void deleteTransaction(){
        
    }


}
    



