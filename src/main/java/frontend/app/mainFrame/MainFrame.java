package frontend.app.mainFrame;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import backend.controller.TransactionController;
import backend.fileExporter.PDFExporter;
import backend.model.Transaction;
import frontend.app.addFrame.frame.AddExpenseFrame;
import frontend.app.exportFrame.DateRangeDialog;
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

    private void handleGenerateReport() {
        // Show date range dialog
        DateRangeDialog dialog = new DateRangeDialog(this);
        dialog.setVisible(true);

        if (!dialog.isConfirmed()) {
            return;
        }

        LocalDate startDate = dialog.getStartDate();
        LocalDate endDate = dialog.getEndDate();

        // Validate date range
        if (startDate.isAfter(endDate)) {
            JOptionPane.showMessageDialog(this,
                    "Start date must be before or equal to end date.",
                    "Invalid Date Range",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get transactions in date range
        List<Transaction> transactions = controller.filterByDateRange(startDate, endDate);

        if (transactions.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No transactions found in the selected date range.",
                    "No Data",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Generate default filename from date range
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String defaultFilename = startDate.format(formatter) + "_to_" + endDate.format(formatter);

        // Show file chooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save PDF Report");
        fileChooser.setSelectedFile(new File(defaultFilename + ".pdf"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Files (*.pdf)", "pdf"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();

            // Ensure .pdf extension
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf";
            }

            try {
                // Export PDF
                PDFExporter exporter = new PDFExporter(filePath, transactions, startDate, endDate, controller);
                exporter.exportFile();

                JOptionPane.showMessageDialog(this,
                        "PDF report generated successfully!\nSaved to: " + filePath,
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error generating PDF report: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
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
            handleGenerateReport();
        }
    }

}
