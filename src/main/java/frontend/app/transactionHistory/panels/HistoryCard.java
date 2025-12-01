package frontend.app.transactionHistory.panels;

import backend.controller.TransactionController;
import frontend.components.IconLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class HistoryCard extends JPanel {

    private HistoryCardsPanel source;
    private int id;
    private double amount;
    private LocalDateTime dateTime;
    private String category;
    private String description;
    private String currencyCode;

    public HistoryCard(HistoryCardsPanel source, int id, double amount, LocalDateTime dateTime, String category, String description, String currencyCode) {
        this.id = id;
        this.source = source;
        this.amount = amount;
        this.dateTime = dateTime;
        this.category = category;
        this.description = description;
        this.currencyCode = currencyCode;

        initializeCardLook();
        addComponents();
    }

    private void initializeCardLook() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        int cardWidth = source.getWidth() - 40;
        setPreferredSize(new Dimension(cardWidth, 100));
        setMaximumSize(new Dimension(cardWidth, 100));
    }

    private void addComponents() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        contentPanel.add(createCategoryLabel(), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(createButtonPanel(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPanel.add(createAmountLabel(), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(createDateLabel(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(createDescriptionTextArea(), gbc);

        add(contentPanel, BorderLayout.CENTER);
    }

    private JLabel createCategoryLabel() {
        JLabel categoryLabel = new JLabel(category);
        categoryLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        return categoryLabel;
    }

    private JLabel createAmountLabel() {
        Color amountColor = (amount < 0) ? new Color(220, 60, 60) : new Color(60, 180, 60);
        String text = String.format("%.2f %s", amount, currencyCode);
        JLabel amountLabel = new JLabel(text);
        amountLabel.setFont(new Font("Dialog", Font.PLAIN, 30));
        amountLabel.setForeground(amountColor);

        return amountLabel;
    }

    private JLabel createDateLabel() {

        String text = dateTime.toLocalDate().toString();
        JLabel dateLabel = new JLabel(text);
        dateLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        return dateLabel;
    }

    private JTextArea createDescriptionTextArea() {
        JTextArea textArea = new JTextArea(description);
        textArea.setFont(new Font("Dialog", Font.PLAIN, 15));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setPreferredSize(new Dimension(380, 40));
        textArea.setMaximumSize(new Dimension(380, 40));
        return textArea;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setPreferredSize(new Dimension(80, 24));

        buttonPanel.add(createEditButton());
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(createDeleteButton());

        return buttonPanel;
    }

    private JButton createDeleteButton() {
        String deleteButtonIconPath = "/assets/delete.png";
        JButton button = new JButton(IconLoader.loadIcon(deleteButtonIconPath));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        button.setFocusable(false);
        button.setPreferredSize(new Dimension(24, 24));
        button.setMinimumSize(new Dimension(24, 24));
        button.setMaximumSize(new Dimension(24, 24));
        button.addActionListener(createDeleteButtonActionListener());
        return button;
    }

    private JButton createEditButton() {
        String editButtonIconPath = "/assets/edit.png";
        JButton button = new JButton(IconLoader.loadIcon(editButtonIconPath));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        button.setFocusable(false);
        button.setPreferredSize(new Dimension(24, 24));
        button.setMinimumSize(new Dimension(24, 24));
        button.setMaximumSize(new Dimension(24, 24));
        button.addActionListener(createEditButtonActionListener());
        return button;
    }

    private ActionListener createDeleteButtonActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(source, "Do you really want to delete this card?");
                switch (result) {
                    case JOptionPane.YES_OPTION -> {
                        TransactionController controller = new TransactionController();
                        boolean success = controller.deleteTransaction(id);

                        if (success) {
                            JOptionPane.showMessageDialog(source, "Card deleted successfully!");
                            source.removeCard(HistoryCard.this);
                        } else {
                            JOptionPane.showMessageDialog(source, "Error occurred while deleting a card!");
                        }
                    }
                    case JOptionPane.NO_OPTION, JOptionPane.CANCEL_OPTION, JOptionPane.CLOSED_OPTION -> {

                    }
                }
            }
        };
    }

    private ActionListener createEditButtonActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditDialog(source, HistoryCard.this).setVisible(true);
                source.refreshCards();
            }
        };
    }

    //getters
    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }
}
