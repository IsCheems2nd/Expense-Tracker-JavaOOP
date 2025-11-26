package view;

import controller.TransactionController;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class AddExpenseFrame extends BaseFrame {
    private JTextField amountField;
    private JCheckBox incomeCheckbox;
    private JComboBox<String> categoryCombo;
    private JTextArea descriptionArea;
    private JTextField dateField;

    public AddExpenseFrame(String title, TransactionController controller, int width, int height) {
        super(title, controller, width, height);
    }

    @Override
    protected void addGuiComponents() {
        addTitleLabel();
        addSeparator();
        addAmountPanel();
        addTransactionTypePanel();
        addCategoryPanel();
        addDatePanel();
        addDescriptionPanel();
        addButtonPanel();
    }

    private void addTitleLabel() {
        JLabel titleLabel = UIComponentFactory.createLabel(
                "Add New Transaction", 0, 0, getWidth() - 10, 50, 24, SwingConstants.CENTER
        );
        add(titleLabel);
    }

    private void addSeparator() {
        JSeparator separator = UIComponentFactory.createSeparator(15, 50, getWidth() - 30, 10);
        add(separator);
    }

    private void addAmountPanel() {
        int contentWidth = Math.min(getWidth() - 20, 450);
        int contentX = (getWidth() - contentWidth) / 2;
        JLabel amountLabel = UIComponentFactory.createLabel("Amount:", contentX, 70, 100, 25, 14, SwingConstants.LEFT);
        add(amountLabel);

        amountField = UIComponentFactory.createTextField("0.00", contentX, 100, contentWidth, 40, 16, true);
        add(amountField);
    }

    private void addTransactionTypePanel() {
        int contentWidth = Math.min(getWidth() - 20, 450);
        int contentX = (getWidth() - contentWidth) / 2;
        incomeCheckbox = UIComponentFactory.createCheckBox(
                "Income (unchecked = Expense)", contentX, 150, contentWidth, 25, 12, false
        );
        add(incomeCheckbox);
    }

    private void addCategoryPanel() {
        int contentWidth = Math.min(getWidth() - 20, 450);
        int contentX = (getWidth() - contentWidth) / 2;
        JLabel categoryLabel = UIComponentFactory.createLabel("Category:", contentX, 185, 100, 25, 14, SwingConstants.LEFT);
        add(categoryLabel);

        categoryCombo = UIComponentFactory.createStringComboBox(
                new String[]{"Food", "Transport", "Entertainment", "Utilities", "Other"},
                contentX, 215, contentWidth, 35, 14
        );
        add(categoryCombo);
    }

    private void addDatePanel() {
        int contentWidth = Math.min(getWidth() - 20, 450);
        int contentX = (getWidth() - contentWidth) / 2;
        JLabel dateLabel = UIComponentFactory.createLabel("Date:", contentX, 260, 100, 25, 14, SwingConstants.LEFT);
        add(dateLabel);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        dateField = UIComponentFactory.createTextField(
                now.format(formatter),
                contentX, 290, contentWidth, 40, 14, true
        );
        add(dateField);
    }

    private void addDescriptionPanel() {
        int contentWidth = Math.min(getWidth() - 20, 450);
        int contentX = (getWidth() - contentWidth) / 2;
        JLabel descLabel = UIComponentFactory.createLabel("Description:", contentX, 340, 100, 25, 14, SwingConstants.LEFT);
        add(descLabel);

        descriptionArea = UIComponentFactory.createTextArea(contentX, 370, contentWidth, 120, 12);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setBounds(contentX, 370, contentWidth, 120);
        add(scrollPane);
    }

    private void addButtonPanel() {
        int contentWidth = Math.min(getWidth() - 20, 450);
        int contentX = (getWidth() - contentWidth) / 2;
        int btnWidth = (contentWidth - 10) / 2;
        
        JButton saveBtn = UIComponentFactory.createButton("Save", contentX, 500, btnWidth, 40, 14);
        saveBtn.addActionListener(e -> onSave());
        add(saveBtn);

        JButton backBtn = UIComponentFactory.createButton("Back", contentX + btnWidth + 10, 500, btnWidth, 40, 14);
        backBtn.addActionListener(e -> onBack());
        add(backBtn);
    }

    private void onSave() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (!incomeCheckbox.isSelected()) {
                amount = -Math.abs(amount);
            } else {
                amount = Math.abs(amount);
            }
            String category = (String) categoryCombo.getSelectedItem();
            String description = descriptionArea.getText().trim();

            controller.addTransaction(LocalDateTime.now(), amount, category, description);
            JOptionPane.showMessageDialog(this, "Transaction added successfully!");
            onBack();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onBack() {
        dispose();
        new MainFrame(controller).setVisible(true);
    }
}
