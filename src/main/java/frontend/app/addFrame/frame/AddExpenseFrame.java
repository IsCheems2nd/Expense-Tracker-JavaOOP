package frontend.app.addFrame.frame;

import backend.controller.TransactionController;
import frontend.app.mainFrame.BaseFrame;
import frontend.app.addFrame.panels.*;
import frontend.components.UIComponentFactory;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;

public class AddExpenseFrame extends BaseFrame implements ActionListener {
    
    // --- Panels ---
    private AmountPanel amountPanel;
    private TransactionTypePanel transactionTypePanel;
    private CategoryPanel categoryPanel;
    private DatePanel datePanel;
    private DescriptionPanel descriptionPanel;
    private AddExpenseFrameButtonPanel buttonPanel;
    
    public AddExpenseFrame(String title, TransactionController controller, int width, int height) {
        super(title, controller, width, height);
    }

    @Override
    protected void addGuiComponents() {
        addWelcomingComponents();
        addPanels();
    }

    private void addPanels() {
        initializePanels();
        arrangePanels();

        add(amountPanel);
        add(transactionTypePanel);
        add(categoryPanel);
        add(datePanel);
        add(descriptionPanel);
        add(buttonPanel);

        revalidate();
        repaint();
    }

    private void initializePanels() {
        // Initialize panels
        amountPanel = new AmountPanel(getWidth());
        categoryPanel = new CategoryPanel(getWidth(), true);
        transactionTypePanel = new TransactionTypePanel(categoryPanel, getWidth());
        datePanel = new DatePanel(getWidth()); // DatePanel now contains JComboBoxes
        descriptionPanel = new DescriptionPanel(getWidth());
        
        buttonPanel = new AddExpenseFrameButtonPanel(this, controller, getWidth());
    }

    private void arrangePanels() {
        // Bounds adjusted based on DatePanel's simplified layout
        amountPanel.setBounds(0, 60, getWidth(), 80);
        transactionTypePanel.setBounds(0, 140, getWidth(), 40);
        categoryPanel.setBounds(0, 180, getWidth(), 80);
        datePanel.setBounds(0, 260, getWidth(), 100); 
        descriptionPanel.setBounds(0, 360, getWidth(), 200);
        buttonPanel.setBounds(0, 560, getWidth(), 40);
    }

    private void addWelcomingComponents() {
        add(createSeparator());
        add(createAddExpenseLabel());
    }

    private JLabel createAddExpenseLabel() {
        JLabel label = UIComponentFactory.createLabel(
                "Add New Transaction", 0, 0, getWidth(), 50, 24, SwingConstants.CENTER
        );
        label.setFont(label.getFont().deriveFont(Font.BOLD, 24f)); 
        return label;
    }
    
    // --- Accessor Methods (Getters) ---
    
    public AmountPanel getAmountPanel() { return amountPanel; }
    public TransactionTypePanel getTransactionTypePanel() { return transactionTypePanel; }
    public CategoryPanel getCategoryPanel() { return categoryPanel; }
    public DatePanel getDatePanel() { return datePanel; } 
    public DescriptionPanel getDescriptionPanel() { return descriptionPanel; }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // Event handling (if any)
    }
}