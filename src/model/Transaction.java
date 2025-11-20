public class Transaction {
    private int id;
    private LocalDate date;
    private double amount;
    private String category;
    private String note;

    public Transaction(int id, LocalDate date, double amount, String category, String note){

        this.id = id;
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.note = note;

    }
}