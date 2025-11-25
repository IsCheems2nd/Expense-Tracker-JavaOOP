package view;

import model.Transaction;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class TransactionFormPanel extends JPanel {
    private JTextField amountField = new JTextField(15);
    private JTextField categoryField = new JTextField(15);
    private JTextField descField = new JTextField(20);

    public TransactionFormPanel(Transaction t) {
        setLayout(new GridLayout(0,2,5,5));
        add(new JLabel("Amount:")); add(amountField);
        add(new JLabel("Category:")); add(categoryField);
        add(new JLabel("Description:")); add(descField);

        if (t != null) {
            amountField.setText(String.valueOf(t.getAmount()));
            categoryField.setText(t.getCategory());
            descField.setText(t.getDescription());
        }
    }

    public Transaction toTransaction() {
        try {
            double amt = Double.parseDouble(amountField.getText().trim());
            String cat = categoryField.getText().trim();
            String desc = descField.getText().trim();
            return new Transaction(0, LocalDateTime.now(), amt, cat, desc);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount.");
            return null;
        }
    }

    public double getAmount() {
        try { return Double.parseDouble(amountField.getText().trim()); } catch (NumberFormatException e) { return 0.0; }
    }

    public String getCategory() { return categoryField.getText().trim(); }

    public String getDescription() { return descField.getText().trim(); }
}
