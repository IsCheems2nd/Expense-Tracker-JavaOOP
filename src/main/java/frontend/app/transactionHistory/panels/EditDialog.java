package frontend.app.transactionHistory.panels;

import javax.swing.JComboBox;
import javax.swing.JDialog;

import frontend.app.addFrame.panels.AmountPanel;
import frontend.app.addFrame.panels.CategoryPanel;
import frontend.app.addFrame.panels.DescriptionPanel;
import frontend.app.addFrame.panels.TransactionTypePanel;

public class EditDialog extends JDialog {
    private HistoryCardsPanel source;
    private HistoryCard historyCard;
    private AmountPanel amountPanel;
    private TransactionTypePanel transactionTypePanel;
    private CategoryPanel categoryPanel;
    // private DatePanel datePanel; // REMOVED
    private DescriptionPanel descriptionPanel;
    private EditButtonPanel editButtonPanel;

    public EditDialog(HistoryCardsPanel source, HistoryCard historyCard)
    {
        this.source = source;
        this.historyCard = historyCard;

        setTitle("Edit Card");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        // Adjusted height (540 - 80 for DatePanel = 460)
        setSize(400, 460); 
        setModal(false);
        setLocationRelativeTo(source);
        setLayout(null);
        setResizable(false);

        initializeAllComponents();
    }

    private void initializeAllComponents()
    {
        initializePanels();
        fillWithExistingValues();

        arrangePanels();

        addComponents();
    }

    private void addComponents()
    {
        add(amountPanel);
        add(transactionTypePanel);
        add(categoryPanel);
        // add(datePanel); // REMOVED
        add(descriptionPanel);
        add(editButtonPanel);
    }

    private void initializePanels()
    {
        amountPanel = new AmountPanel(getWidth());
        categoryPanel = new CategoryPanel(getWidth(), true);
        transactionTypePanel = new TransactionTypePanel(categoryPanel, getWidth());
        descriptionPanel = new DescriptionPanel(getWidth());
        editButtonPanel = new EditButtonPanel(this, getWidth());
    }

    private void arrangePanels()
    {
        amountPanel.setBounds(0, 0, getWidth(), 80);
        transactionTypePanel.setBounds(0, 80, getWidth(), 30);
        categoryPanel.setBounds(0, 100, getWidth(), 80);
        
        // DatePanel (0, 180, W, 80) was here. Removing it shifts DescriptionPanel up.
        
        // New Y: 180 (Old Y of DatePanel)
        descriptionPanel.setBounds(0, 180, getWidth(), 190);
        
        // New Y: 180 + 190 = 370 (Old Y was 450)
        editButtonPanel.setBounds(0, 370, getWidth(), 40);
    }

    private void fillWithExistingValues()
    {

        amountPanel.setAmountText(String.valueOf(historyCard.getAmount()));
        descriptionPanel.getDescriptionTextArea().setText(historyCard.getDescription());

        // 5. Category
        JComboBox<String> categoryComboBox = categoryPanel.getCategoryComboBox();
        String categoryFromDB = historyCard.getCategory();

        if (categoryFromDB != null) {
            categoryComboBox.setSelectedItem(categoryFromDB);
        }
    }

    public AmountPanel getAmountPanel() {
        return amountPanel;
    }

    public TransactionTypePanel getTransactionTypePanel() {
        return transactionTypePanel;
    }

    public CategoryPanel getCategoryPanel() {
        return categoryPanel;
    }

    public DescriptionPanel getDescriptionPanel() {
        return descriptionPanel;
    }

    public HistoryCard getHistoryCard() {
        return historyCard;
    }

    public HistoryCardsPanel getSource() {
        return source;
    }
    
}