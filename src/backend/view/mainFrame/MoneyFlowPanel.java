package view;

import controller.TransactionController;
import model.Transaction;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MoneyFlowPanel extends JPanel {
    private TransactionController controller;

    public MoneyFlowPanel(TransactionController controller) {
        this.controller = controller;
        setLayout(new GridBagLayout());
        setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        List<Transaction> transactionList = controller.getAllTransactions();
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(createExpenseLabel("Expenses"), gbc);

        gbc.gridx = 1;
        add(createIncomeLabel("Income"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(createMoneyFlowLabel(calculateExpenses(transactionList), Color.RED), gbc);

        gbc.gridx = 1;
        add(createMoneyFlowLabel(calculateIncome(transactionList), Color.GREEN), gbc);
    }

    private double calculateExpenses(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .mapToDouble(Transaction::getAmount)
                .map(Math::abs)
                .sum();
    }

    private double calculateIncome(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() > 0)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    private JLabel createExpenseLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Dialog", Font.PLAIN, 14));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    private JLabel createIncomeLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Dialog", Font.PLAIN, 14));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    private JLabel createMoneyFlowLabel(double amount, Color color) {
        JLabel label = new JLabel(String.format("$%.2f", amount));
        label.setFont(new Font("Dialog", Font.BOLD, 16));
        label.setForeground(color);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }
}
