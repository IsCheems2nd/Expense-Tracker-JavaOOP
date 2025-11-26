package view;

import controller.TransactionController;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import model.Transaction;

public class MainFrame extends BaseFrame implements ActionListener {
    private TransactionTableModel mainTableModel;

    public MainFrame(TransactionController controller) {
        super("Expense Tracker", controller, 600, 800);
    }

    @Override
    protected void addGuiComponents() {
        addWelcomeLabel();
        addBalanceLabel();
        addBalanceTextField();
        addAddTransactionButton();
        addBrowseHistoryButton();
        addGenerateReportButton();
        addMoneyFlowPanel();
        addRecentTransactionsPanel();
    }

    private void addWelcomeLabel() {
        String welcomeText = "<html><body style='text-align:center'><b>Expense Tracker</b><br>Manage your finances</body></html>";
        JLabel welcomeLabel = UIComponentFactory.createLabel(welcomeText, 0, 20, getWidth() - 10, 50, 18, SwingConstants.CENTER);
        add(welcomeLabel);
    }

    private void addBalanceLabel() {
        JLabel balanceLabel = UIComponentFactory.createLabel("Current Balance", 0, 100, getWidth() - 10, 30, 18, SwingConstants.CENTER);
        add(balanceLabel);
    }

    private void addBalanceTextField() {
        double totalBalance = calculateBalance();
        JTextField balanceTextField = UIComponentFactory.createTextField(
                String.format("$%.2f", totalBalance),
                20, 135, getWidth() - 50, 40, 24, true
        );
        balanceTextField.setHorizontalAlignment(SwingConstants.CENTER);
        balanceTextField.setEditable(false);
        add(balanceTextField);
    }

    private double calculateBalance() {
        List<Transaction> transactions = controller.getAllTransactions();
        double balance = 0;
        for (Transaction t : transactions) {
            balance += t.getAmount();
        }
        return balance;
    }

    private void addAddTransactionButton() {
        int contentWidth = Math.min(getWidth() - 10, 500);
        int btnX = (getWidth() - contentWidth) / 2;
        JButton addBtn = UIComponentFactory.createButton("Add Transaction", btnX, 190, contentWidth, 40, 16);
        addBtn.setActionCommand("Add Transaction");
        addBtn.addActionListener(this);
        add(addBtn);
    }

    private void addBrowseHistoryButton() {
        int contentWidth = Math.min(getWidth() - 10, 500);
        int btnX = (getWidth() - contentWidth) / 2;
        JButton historyBtn = UIComponentFactory.createButton("Browse Transactions History", btnX, 240, contentWidth, 40, 16);
        historyBtn.setActionCommand("Browse History");
        historyBtn.addActionListener(this);
        add(historyBtn);
    }

    private void addGenerateReportButton() {
        int contentWidth = Math.min(getWidth() - 10, 500);
        int btnX = (getWidth() - contentWidth) / 2;
        JButton reportBtn = UIComponentFactory.createButton("Generate Report", btnX, 290, contentWidth, 40, 16);
        reportBtn.setActionCommand("Generate Report");
        reportBtn.addActionListener(this);
        add(reportBtn);
    }

    private void addMoneyFlowPanel() {
        int contentWidth = Math.min(getWidth() - 10, 500);
        int panelX = (getWidth() - contentWidth) / 2;
        JPanel moneyFlowPanel = new MoneyFlowPanel(controller);
        moneyFlowPanel.setBounds(panelX, 340, contentWidth, 80);
        add(moneyFlowPanel);
    }

    private void addRecentTransactionsPanel() {
        int contentWidth = Math.min(getWidth() - 10, 500);
        int labelX = (getWidth() - contentWidth) / 2;
        JLabel recentLabel = UIComponentFactory.createLabel("Recent Transactions", labelX, 425, contentWidth, 25, 14, SwingConstants.LEFT);
        add(recentLabel);

        mainTableModel = new TransactionTableModel(controller.getAllTransactions());
        JTable table = new JTable(mainTableModel);
        table.setFont(new Font("Dialog", Font.PLAIN, 10));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(labelX, 450, contentWidth, 100);
        add(scrollPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("Add Transaction")) {
            dispose();
            new AddExpenseFrame("Add Transaction", controller, 550, 700).setVisible(true);
        } else if (cmd.equals("Browse History")) {
            dispose();
            new TransactionHistoryFrameWindow("Transaction History", controller, 600, 700).setVisible(true);
        } else if (cmd.equals("Generate Report")) {
            dispose();
            new GenerateReportFrameWindow("Generate Report", controller, 600, 700).setVisible(true);
        }
    }

    public void refreshBalance() {
        removeAll();
        addGuiComponents();
        revalidate();
        repaint();
    }
}
