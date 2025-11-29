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
import frontend.app.summaryFrame.frame.SummaryFrame;
import frontend.app.transactionHistory.frame.TransactionHistoryFrame;
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
        addViewReportButton();
        addGenerateReportButton();
        addMoneyFlowPanel();
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

    private void addViewReportButton() {
        JButton summaryBtn = UIComponentFactory.createButton("View Report", 5, 230, getWidth() - 10, 40, 22);
        summaryBtn.setActionCommand("View Report");
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

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equalsIgnoreCase("Add Transaction")) {
            System.out.println("ACTION: Add Transaction button clicked.");
            dispose();
            new AddExpenseFrame("Add Transaction", controller, 600, 800).setVisible(true);
        } else if (cmd.equalsIgnoreCase("Browse Transactions History")) {
            System.out.println("ACTION: Browse History button clicked.");
            dispose();
            new TransactionHistoryFrame("Transaction History", controller, 600, 800).setVisible(true);
        } else if (cmd.equalsIgnoreCase("View Report")) {
            System.out.println("ACTION: View Report button clicked.");
            dispose();
            new SummaryFrame("Summary Report", controller, 800, 700).setVisible(true);
        } else if (cmd.equalsIgnoreCase("Generate Report")) {
            System.out.println("ACTION: Generate Report button clicked.");
            // dispose();
            // new GenerateReportFrameWindow("Generate Report", controller, 420, 600).setVisible(true); // Comment out or delete
        }
    }

}
