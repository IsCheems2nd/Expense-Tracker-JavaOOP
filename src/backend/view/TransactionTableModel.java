package view;

import model.Transaction;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TransactionTableModel extends AbstractTableModel {
    private List<Transaction> transactions;
    private final String[] cols = {"ID","DateTime","Amount","Category","Description"};

    public TransactionTableModel(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
        fireTableDataChanged();
    }

    public Transaction getTransactionAt(int row) {
        return transactions.get(row);
    }

    @Override
    public int getRowCount() {
        return transactions == null ? 0 : transactions.size();
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public String getColumnName(int column) {
        return cols[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Transaction t = transactions.get(rowIndex);
        switch (columnIndex) {
            case 0: return t.getId();
            case 1: return t.getFormattedDateTime();
            case 2: return t.getAmount();
            case 3: return t.getCategory();
            case 4: return t.getDescription();
            default: return null;
        }
    }
}
