package view;

import controller.TransactionController;
import java.awt.*;
import javax.swing.*;

public class TransactionHistoryFrameWindow extends BaseFrame {
    private TransactionTableModel tableModel;
    private JTextField searchField;

    public TransactionHistoryFrameWindow(String title, TransactionController controller, int width, int height) {
        super(title, controller, width, height);
    }

    @Override
    protected void addGuiComponents() {
        addTitleLabel();
        addSeparator();
        addSearchPanel();
        addTablePanel();
        addButtonPanel();
    }

    private void addTitleLabel() {
        JLabel titleLabel = UIComponentFactory.createLabel(
                "Transaction History", 0, 0, getWidth() - 10, 50, 24, SwingConstants.CENTER
        );
        add(titleLabel);
    }

    private void addSeparator() {
        JSeparator separator = UIComponentFactory.createSeparator(15, 50, getWidth() - 30, 10);
        add(separator);
    }

    private void addSearchPanel() {
        int centerX = (getWidth() - 400) / 2;
        int labelX = centerX + 10;
        int fieldWidth = 250;
        int fieldX = labelX + 80;
        int btnX = fieldX + fieldWidth + 10;
        
        JLabel searchLabel = UIComponentFactory.createLabel("Search:", labelX, 70, 70, 25, 12, SwingConstants.LEFT);
        add(searchLabel);

        searchField = UIComponentFactory.createTextField(fieldX, 70, fieldWidth, 25, 12, true);
        add(searchField);

        JButton searchBtn = UIComponentFactory.createButton("Search", btnX, 70, 100, 25, 8);
        searchBtn.addActionListener(e -> performSearch());
        add(searchBtn);
    }

    private void addTablePanel() {
        int contentWidth = Math.min(getWidth() - 40, 700);
        int contentX = (getWidth() - contentWidth) / 2;
        tableModel = new TransactionTableModel(controller.getAllTransactions());
        JTable table = new JTable(tableModel);
        table.setFont(new Font("Dialog", Font.PLAIN, 10));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(contentX, 110, contentWidth, 350);
        add(scrollPane);
    }

    private void addButtonPanel() {
        int btnX = (getWidth() - 100) / 2;
        JButton backBtn = UIComponentFactory.createButton("Back", btnX, 470, 100, 40, 14);
        backBtn.addActionListener(e -> onBack());
        add(backBtn);
    }

    private void performSearch() {
        String query = searchField.getText().trim();
        if (query.isEmpty()) {
            tableModel.setTransactions(controller.getAllTransactions());
        } else {
            tableModel.setTransactions(controller.search(query));
        }
    }

    private void onBack() {
        dispose();
        new MainFrame(controller).setVisible(true);
    }
}
