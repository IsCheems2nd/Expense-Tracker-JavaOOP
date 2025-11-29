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

    private final JTextField amountEnteringTextField;
    private final JTextArea descriptionTextArea;
    private final JComboBox<String> categoryComboBox;
    private final JComboBox<String> currencyComboBox;
    private final TransactionTypePanel transactionTypePanel;

    private final DatePanel datePanel;

    public AddExpenseFrameButtonPanel(AddExpenseFrame source, TransactionController controller, int width) {
        this.sourceFrame = source;
        this.controller = controller;

        this.amountEnteringTextField = source.getAmountPanel().getAmountEnteringTextField();
        this.descriptionTextArea = source.getDescriptionPanel().getDescriptionTextArea();
        this.categoryComboBox = source.getCategoryPanel().getCategoryComboBox();
        this.transactionTypePanel = source.getTransactionTypePanel();
        this.datePanel = source.getDatePanel();
        this.currencyComboBox = sourceFrame.getAmountPanel().getCurrencyComboBox();

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

            String amountText = amountEnteringTextField.getText();
            if (!TransactionFlowFilter.validateAmountEntered(amountText)) {
                JOptionPane.showMessageDialog(sourceFrame, "Amount entered must be a positive number!");
                return;
            }
            BigDecimal amount = TransactionFlowFilter.filterAmountEntered(amountText);

            String dateString = datePanel.getDateText();

            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            LocalDateTime dateTime = date.atTime(LocalTime.MIDNIGHT);

            double amountValue = amount.doubleValue();

            if (ExpenseWrapper.isExpense()) {
                amountValue = -amountValue;
            }

            String currencyCode = (String) currencyComboBox.getSelectedItem();

            String category = (String) categoryComboBox.getSelectedItem();
            category = category != null ? category : "Other";
            String description = descriptionTextArea.getText();

            controller.addTransaction(dateTime, amountValue, category, description, currencyCode);

            clearAllTheFieldsUponAdding();
            JOptionPane.showMessageDialog(sourceFrame, "Transaction added successfully!");

        };
    }

    private void clearAllTheFieldsUponAdding() {
        amountEnteringTextField.setText("");
        descriptionTextArea.setText("");
        datePanel.clear();
        transactionTypePanel.getExpenseCheckBox().setSelected(true);
    }
}
