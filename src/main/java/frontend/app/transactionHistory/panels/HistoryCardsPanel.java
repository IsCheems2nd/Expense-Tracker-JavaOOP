package frontend.app.transactionHistory.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import backend.controller.TransactionController;
import backend.model.Transaction;
import frontend.app.transactionHistory.frame.TransactionHistoryFrame;

public class HistoryCardsPanel extends JPanel {
    private final TransactionHistoryFrame source;
    private final TransactionController controller;

    public HistoryCardsPanel(TransactionHistoryFrame source, TransactionController controller)
    {
        this.source = source;
        this.controller = new TransactionController();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        addTransactionHistoryCards();
    }

    private void addTransactionHistoryCards() {
        List<Transaction> transactions = controller.getAllTransactions();

        for (Transaction t : transactions) {
            JPanel cardPanel = new JPanel(new BorderLayout());
            cardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            HistoryCard card = new HistoryCard(this, t.getId(), t.getAmount(), t.getDateTime(), t.getCategory(), t.getDescription());

            int panelWidth = source.getWidth() - 20;
            cardPanel.setMaximumSize(new Dimension(panelWidth, 120));

            cardPanel.add(card, BorderLayout.CENTER);
            add(cardPanel);
        }

        updatePanel();
    }

    public TransactionController getController() {
        return controller;
    }

    public TransactionHistoryFrame getSource() {
        return source;
    }

    public void updatePanel()
    {
        revalidate();
        repaint();
    }

    public void removeCard(HistoryCard historyCard)
    {
        //remove(historyCard);
        //updatePanel();
        refreshCards(); //the two above somehow does not work
    }

    public void refreshCards()
    {
        removeAll();
        addTransactionHistoryCards();
        updatePanel();
    }
}