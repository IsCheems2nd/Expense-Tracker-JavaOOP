package frontend.app.transactionHistory.frame;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import backend.controller.TransactionController;
import frontend.app.mainFrame.BaseFrame;
import frontend.app.transactionHistory.panels.HistoryCardsPanel;
import frontend.app.transactionHistory.panels.TransactionHistoryButtonPanel;
import frontend.components.UIComponentFactory;

public class TransactionHistoryFrame extends BaseFrame {
    private final TransactionController controller; 
    
    private JScrollPane historyCardsScrollPane;
    private HistoryCardsPanel historyCardsPanel;
    private TransactionHistoryButtonPanel transactionHistoryButtonPanel;

    public TransactionHistoryFrame(String title, TransactionController controller, int width, int height) {
        super(title, controller, width, height); 
        this.controller = controller;
    }

    @Override
    protected void addGuiComponents() {
        addWelcomingComponents();
        addPanels();
    }

    private void addPanels()
    {
        initializePanels();
        arrangePanels();

        add(historyCardsScrollPane);
        add(transactionHistoryButtonPanel);

        revalidate();
        repaint();
        refreshTransactionFrame();
    }

    private void initializePanels()
    {
        // Pass the controller
        historyCardsPanel = new HistoryCardsPanel(this, controller);
        historyCardsScrollPane = new JScrollPane(historyCardsPanel);
        historyCardsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        historyCardsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        historyCardsScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        historyCardsScrollPane.getVerticalScrollBar().setBlockIncrement(100);

        // Pass the controller
        transactionHistoryButtonPanel = new TransactionHistoryButtonPanel(this, controller, getWidth());
    }

    private void arrangePanels()
    {
        // Adjust layout slightly to make room for potential filters later
        historyCardsScrollPane.setBounds(0, 70, getWidth(), 400);
        transactionHistoryButtonPanel.setBounds(0, 500, getWidth(), 40);
    }

    private void addWelcomingComponents()
    {
        // Placeholder for filters and history label
        add(createSeparator());
        add(createTransactionHistoryLabel());
    }

    private JLabel createTransactionHistoryLabel()
    {
        return UIComponentFactory.createLabel(
                "Transaction History", 0, 0, getWidth() - 10, 50, 30, SwingConstants.CENTER
        );
    }
    
    // Getter for the controller
    public TransactionController getController() {
        return controller;
    }

    // Method to call from EditDialog to force the frame and panel to reload data
    public void refreshTransactionFrame() {
        historyCardsPanel.refreshCards();
    }
}