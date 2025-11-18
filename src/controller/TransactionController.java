public class TransactionController {
    private List<Transaction> transactions = new ArrayList<>();
    private int nextId = 1;


    public void addTransaction(LocalDate date, double amount, String category, String note){
        Transaction t = new Transaction(nextId++,date,amount,category,note);
        transactions.add(t);

    }
}