package frontend.app.mainFrame;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets; // Needed for resource loading
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import backend.controller.TransactionController;
import backend.model.Transaction;
import frontend.components.IconLoader;

public class MoneyFlowPanel extends JPanel {
    private TransactionController controller;

    public MoneyFlowPanel(TransactionController controller) {
        this.controller = controller;
        setLayout(new GridBagLayout());
        setOpaque(false);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 20, 5, 20); 

        List<Transaction> transactionList = null;
        try {
            transactionList = controller.getAllTransactions();
            
            double expenses = calculateExpenses(transactionList);
            double income = calculateIncome(transactionList);
            
            // ROW 0: Icons (Expense Icon on the left, Income Icon on the right)
            gbc.gridx = 0;
            gbc.gridy = 0;
            add(createIconLabel("expense"), gbc);

            gbc.gridx = 1;
            add(createIconLabel("income"), gbc);

            // ROW 1: Money Amounts
            gbc.gridx = 0;
            gbc.gridy = 1;
            add(createMoneyFlowLabel(expenses, false), gbc);

            gbc.gridx = 1;
            add(createMoneyFlowLabel(income, true), gbc);
            
        } catch (Exception e) { 
            e.printStackTrace();
            JLabel errorLabel = new JLabel("Error loading money flow data.");
            errorLabel.setForeground(Color.RED);
            gbc.gridx = 0;
            gbc.gridwidth = 2; 
            gbc.gridy = 0;
            add(errorLabel, gbc);
        }
    }

    public JLabel createIconLabel(String fileName) {
    
        //URL imageUrl = getClass().getResource("/src/main/java/assets/" + fileName); 
        String imagePath = "/assets/" + fileName + "-icon.png";
        ImageIcon imageIcon = IconLoader.loadIcon(imagePath);
        if (imagePath != null) {
            return new JLabel(imageIcon);
        } else {
            // Fallback to text if the image isn't found
            System.err.println("Could not find resource: " + fileName);
            return new JLabel(fileName.toUpperCase());
        }
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

    // --- Label Creation Methods (Adapted) ---

    private JLabel createMoneyFlowLabel(double amount, boolean isIncome) {
        String sign = isIncome ? " + " : " - ";
        String text = sign + String.format("$%.2f", Math.abs(amount)); 
        
        JLabel moneyFlowLabel = new JLabel(text);
        
        // Dark Green (Income) vs Dark Red (Expense)
        Color color = isIncome ? new Color(0x008000) : new Color(0x800000); 
        
        moneyFlowLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
        moneyFlowLabel.setForeground(color);
        moneyFlowLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return moneyFlowLabel;
    }
}