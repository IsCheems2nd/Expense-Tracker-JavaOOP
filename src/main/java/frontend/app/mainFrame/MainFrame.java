package frontend.app.mainFrame;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import backend.controller.TransactionController;
import frontend.app.addFrame.frame.AddExpenseFrame;
import frontend.components.UIComponentFactory;

public class MainFrame extends BaseFrame implements ActionListener {
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
        addPeriodicSummary();
        addGenerateReportButton();
        addMoneyFlowPanel();
        //addRecentTransactionsPanel();
    }

    private void addWelcomeLabel() {
        String welcomeText = "<html><body style='text-align:center'><b>Welcome!</b><br>What would you like to do today?</body></html>";
        JLabel welcomeLabel = UIComponentFactory.createLabel(welcomeText, 0, 20, getWidth() - 10, 50, 18, SwingConstants.CENTER);
        add(welcomeLabel);
    }

    private void addBalanceLabel() {
        JLabel balanceLabel = UIComponentFactory.createLabel("Current Balance", 0, 370, getWidth() - 10, 30, 22, SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Dialog", Font.BOLD, 30));
        add(balanceLabel);
    }

    private void addBalanceTextField() {
        double totalBalance = calculateBalance(); 
        
        // Adjusted coordinates/size/font to match sample look
        JTextField balanceTextField = UIComponentFactory.createTextField(
            String.format("$%.2f", totalBalance),
            20, 410, getWidth() - 50, 40, 28, false
        );
        balanceTextField.setHorizontalAlignment(SwingConstants.CENTER);
        balanceTextField.setEditable(false);
        add(balanceTextField);
    }
    
    private double calculateBalance() {
        return controller.getCurrentBalance(); 
    }


    private void addAddTransactionButton() {
        JButton addBtn = UIComponentFactory.createButton("Add Transaction", 5, 90, getWidth() - 10, 40, 22);
        addBtn.setActionCommand("Add Transaction");
        addBtn.addActionListener(this);
        add(addBtn);
    }

    private void addBrowseHistoryButton() {
        JButton historyBtn = UIComponentFactory.createButton("Browse Transactions History", 5, 160, getWidth() - 10, 40, 22);
        historyBtn.setActionCommand("Browse Transactions History");
        historyBtn.addActionListener(this);
        add(historyBtn);
    }

    private void addPeriodicSummary() {
        JButton summaryBtn = UIComponentFactory.createButton("Periodic Summary", 5, 230, getWidth() - 10, 40, 22);
        summaryBtn.setActionCommand("Periodic Summary");
        summaryBtn.addActionListener(this);
        add(summaryBtn);
    }

    private void addGenerateReportButton() {
        JButton reportBtn = UIComponentFactory.createButton("Generate Report", 5, 300, getWidth() - 10, 40, 22);
        reportBtn.setActionCommand("Generate Report");
        reportBtn.addActionListener(this);
        add(reportBtn);
    }

    private void addMoneyFlowPanel() {
        JPanel moneyFlowPanel = new MoneyFlowPanel(controller);
        moneyFlowPanel.setBounds(5, 450, getWidth() - 10, 120);
        add(moneyFlowPanel);
    }
    /*
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
    */
    /*
    @Override
    public void actionPerformed(ActionEvent e) {

        String cmd = e.getActionCommand();
        if (cmd.equalsIgnoreCase("Add Transaction")) {
            MainFrame.this.dispose();
            new AddExpenseFrame("Add Transaction", controller, 420, 600).setVisible(true);
        } else if (cmd.equalsIgnoreCase("Browse Transactions History")) {
            MainFrame.this.dispose();
            new TransactionHistoryFrameWindow("Transaction History", controller, 420, 600).setVisible(true);
        } else if (cmd.equalsIgnoreCase("Generate Report")) {
            MainFrame.this.dispose();
            new GenerateReportFrameWindow("Generate Report", controller, 420, 600).setVisible(true);
        }
    }
    */
    // Inside frontend.app.mainFrame.MainFrame.java

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        
        if (cmd.equalsIgnoreCase("Add Transaction")) {
            System.out.println("ACTION: Add Transaction button clicked.");
            dispose();
            new AddExpenseFrame("Add Transaction", controller, 600, 800).setVisible(true); 
        } else if (cmd.equalsIgnoreCase("Browse Transactions History")) {
            System.out.println("ACTION: Browse History button clicked. (Frame not yet implemented)");
            // dispose();
            // new TransactionHistoryFrameWindow("Transaction History", controller, 420, 600).setVisible(true); // Comment out or delete
        } else if (cmd.equalsIgnoreCase("Generate Report")) {
            System.out.println("ACTION: Generate Report button clicked. (Frame not yet implemented)");
            // dispose();
            // new GenerateReportFrameWindow("Generate Report", controller, 420, 600).setVisible(true); // Comment out or delete
        }
    }

}