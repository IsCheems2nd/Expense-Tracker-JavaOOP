package frontend.app.addFrame.panels;

import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import backend.controller.TransactionController;
import frontend.app.addFrame.frame.AddExpenseFrame;
import frontend.app.mainFrame.MainFrame;
import frontend.components.ExpenseWrapper;
import frontend.components.TransactionFlowFilter;
import frontend.components.UIComponentFactory;

public class AddExpenseFrameButtonPanel extends JPanel {
    private final AddExpenseFrame sourceFrame;
    private final TransactionController controller;
    
    // References to panel fields
    private final JTextField amountEnteringTextField;
    private final JTextArea descriptionTextArea;
    private final JComboBox<String> categoryComboBox;
    private final TransactionTypePanel transactionTypePanel;
    
    // Reference to the DatePanel
    private final DatePanel datePanel; 

    public AddExpenseFrameButtonPanel(AddExpenseFrame source, TransactionController controller, int width) {
        this.sourceFrame = source;
        this.controller = controller;
        
        // --- Get component references from the source frame ---
        this.amountEnteringTextField = source.getAmountPanel().getAmountEnteringTextField();
        this.descriptionTextArea = source.getDescriptionPanel().getDescriptionTextArea();
        this.categoryComboBox = source.getCategoryPanel().getCategoryComboBox();
        this.transactionTypePanel = source.getTransactionTypePanel();
        this.datePanel = source.getDatePanel();

        setLayout(null);
        addButtons(width);
    }

    private void addButtons(int width) {
        add(createGoBackButton(width));
        add(createAddButton(width));
    }

    private JButton createGoBackButton(int width) {
        JButton button = UIComponentFactory.createButton(
                "Go Back", 5, 0, (width - 10) / 2, 40, 30
        );
        button.addActionListener(createGoBackButtonActionListener());
        return button;
    }

    private JButton createAddButton(int width) {
        int offset = (width - 10) / 2;
        JButton button = UIComponentFactory.createButton(
                "Add", offset + 5, 0, offset, 40, 30
        );
        button.addActionListener(createAddTransactionActionListener());
        return button;
    }

    private ActionListener createGoBackButtonActionListener() {
        return e -> {
            sourceFrame.dispose();
            new MainFrame(controller).setVisible(true); 
        };
    }

    private ActionListener createAddTransactionActionListener() {
        return e -> {
            // --- 1. Validation & Data Filtering ---
            String amountText = amountEnteringTextField.getText();
            if (!TransactionFlowFilter.validateAmountEntered(amountText)) {
                JOptionPane.showMessageDialog(sourceFrame, "Amount entered must be a positive number!");
                return;
            }
            BigDecimal amount = TransactionFlowFilter.filterAmountEntered(amountText); 

            // Retrieve date from the new JComboBox setup
            String dateString = datePanel.getDateText(); // datePanel.getDateText() now returns YYYY-MM-DD from dropdowns.
            
            // --- 2. Data Conversion & Type Logic ---
            
            // Convert date string (YYYY-MM-DD) to LocalDate
            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            // Combine date with midnight time to get LocalDateTime, matching controller requirement
            LocalDateTime dateTime = date.atTime(LocalTime.MIDNIGHT);
            
            // Convert BigDecimal to double for the controller
            double amountValue = amount.doubleValue();
            
            // Apply Expense/Income logic: Negate amount if it's an expense
            if (ExpenseWrapper.isExpense()) {
                amountValue = -amountValue;
            }
            
            String category = (String) categoryComboBox.getSelectedItem();
            category = category != null ? category : "Other";
            String description = descriptionTextArea.getText();

            // --- 3. Persistence (via Controller) ---
            
            // Call the controller's addTransaction method.
            // Note: The controller handles SQLExceptions internally, matching your provided TransactionController.
            controller.addTransaction(dateTime, amountValue, category, description);
            
            clearAllTheFieldsUponAdding();
            JOptionPane.showMessageDialog(sourceFrame, "Transaction added successfully!");
            
        };
    }

    private void clearAllTheFieldsUponAdding() {
        amountEnteringTextField.setText("");
        descriptionTextArea.setText("");
        datePanel.clear(); // Reset date to today using the new clear method
        // Reset type to expense (default)
        transactionTypePanel.getExpenseCheckBox().setSelected(true); 
    }
}