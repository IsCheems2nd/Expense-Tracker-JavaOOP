package view;

import controller.TransactionController;
import model.Transaction;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {
    private TransactionController controller;
    private TransactionTableModel tableModel;
    private JTable table;

    public MainFrame(TransactionController controller) {
        super("Expense Tracker");
        this.controller = controller;
        tableModel = new TransactionTableModel(controller.getAllTransactions());
        table = new JTable(tableModel);

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton delBtn = new JButton("Delete");
        buttons.add(addBtn);
        buttons.add(editBtn);
        buttons.add(delBtn);
        add(buttons, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> onAdd());
        delBtn.addActionListener(e -> onDelete());
        editBtn.addActionListener(e -> onEdit());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
    }

    private void onAdd() {
        TransactionFormPanel form = new TransactionFormPanel(null);
        int result = JOptionPane.showConfirmDialog(this, form, "Add Transaction",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            Transaction t = form.toTransaction();
            if (t != null) {
                controller.addTransaction(t.getDateTime(), t.getAmount(), t.getCategory(), t.getDescription());
                refresh();
            }
        }
    }

    private void onDelete() {
        int sel = table.getSelectedRow();
        if (sel >= 0) {
            int id = tableModel.getTransactionAt(sel).getId();
            controller.deleteTransaction(id);
            refresh();
        } else {
            JOptionPane.showMessageDialog(this, "Select a row first.");
        }
    }

    private void onEdit() {
        int sel = table.getSelectedRow();
        if (sel >= 0) {
            Transaction t = tableModel.getTransactionAt(sel);
            TransactionFormPanel form = new TransactionFormPanel(t);
            int result = JOptionPane.showConfirmDialog(this, form, "Edit Transaction",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                double amt = form.getAmount();
                String cat = form.getCategory();
                String desc = form.getDescription();
                controller.updateTransaction(t.getId(), amt, cat, desc);
                refresh();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a row first.");
        }
    }

    public void refresh() {
        List<Transaction> list = controller.getAllTransactions();
        tableModel.setTransactions(list);
    }
}
