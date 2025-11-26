package view;

import controller.TransactionController;
import model.Transaction;
import javax.swing.*;
import java.awt.*;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GenerateReportFrameWindow extends BaseFrame {
    private JComboBox<String> reportTypeCombo;
    private JTextArea outputArea;

    public GenerateReportFrameWindow(String title, TransactionController controller, int width, int height) {
        super(title, controller, width, height);
    }

    @Override
    protected void addGuiComponents() {
        addTitleLabel();
        addSeparator();
        addReportTypePanel();
        addOutputPanel();
        addButtonPanel();
    }

    private void addTitleLabel() {
        JLabel titleLabel = UIComponentFactory.createLabel(
                "Generate Report", 0, 0, getWidth() - 10, 50, 24, SwingConstants.CENTER
        );
        add(titleLabel);
    }

    private void addSeparator() {
        JSeparator separator = UIComponentFactory.createSeparator(15, 50, getWidth() - 30, 10);
        add(separator);
    }

    private void addReportTypePanel() {
        int contentWidth = Math.min(getWidth() - 20, 500);
        int contentX = (getWidth() - contentWidth) / 2;
        int labelWidth = 120;
        int comboWidth = contentWidth - labelWidth - 110;
        
        JLabel typeLabel = UIComponentFactory.createLabel("Report Type:", contentX, 70, labelWidth, 25, 12, SwingConstants.LEFT);
        add(typeLabel);

        reportTypeCombo = UIComponentFactory.createStringComboBox(
                new String[]{"Summary", "By Category", "Monthly"},
                contentX + labelWidth, 70, comboWidth, 30, 12
        );
        add(reportTypeCombo);

        JButton generateBtn = UIComponentFactory.createButton("Generate", contentX + contentWidth - 100, 70, 100, 30, 12);
        generateBtn.addActionListener(e -> generateReport());
        add(generateBtn);
    }

    private void addOutputPanel() {
        int contentWidth = Math.min(getWidth() - 20, 500);
        int contentX = (getWidth() - contentWidth) / 2;
        outputArea = UIComponentFactory.createTextArea(contentX, 120, contentWidth, 310, 11);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBounds(contentX, 120, contentWidth, 310);
        add(scrollPane);
    }

    private void addButtonPanel() {
        int contentWidth = Math.min(getWidth() - 20, 500);
        int contentX = (getWidth() - contentWidth) / 2;
        int btnWidth = (contentWidth - 10) / 2;
        
        JButton exportBtn = UIComponentFactory.createButton("Export", contentX, 440, btnWidth, 40, 12);
        exportBtn.addActionListener(e -> exportReport());
        add(exportBtn);

        JButton backBtn = UIComponentFactory.createButton("Back", contentX + btnWidth + 10, 440, btnWidth, 40, 12);
        backBtn.addActionListener(e -> onBack());
        add(backBtn);
    }

    private void generateReport() {
        String reportType = (String) reportTypeCombo.getSelectedItem();
        java.util.List<Transaction> transactions = controller.getAllTransactions();
        StringBuilder report = new StringBuilder();

        if ("Summary".equals(reportType)) {
            report.append("=== TRANSACTION SUMMARY ===\n\n");
            double totalIncome = 0, totalExpense = 0;
            for (Transaction t : transactions) {
                if (t.getAmount() > 0) totalIncome += t.getAmount();
                else totalExpense += Math.abs(t.getAmount());
            }
            report.append(String.format("Total Transactions: %d\n", transactions.size()));
            report.append(String.format("Total Income: $%.2f\n", totalIncome));
            report.append(String.format("Total Expense: $%.2f\n", totalExpense));
            report.append(String.format("Net: $%.2f\n", totalIncome - totalExpense));
        } else if ("By Category".equals(reportType)) {
            report.append("=== REPORT BY CATEGORY ===\n\n");
            Map<String, Double> categoryMap = new HashMap<>();
            for (Transaction t : transactions) {
                categoryMap.put(t.getCategory(),
                        categoryMap.getOrDefault(t.getCategory(), 0.0) + t.getAmount());
            }
            for (String cat : categoryMap.keySet()) {
                report.append(String.format("%s: $%.2f\n", cat, categoryMap.get(cat)));
            }
        } else if ("Monthly".equals(reportType)) {
            report.append("=== MONTHLY REPORT ===\n\n");
            Map<YearMonth, Double> monthlyMap = new TreeMap<>();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM");
            for (Transaction t : transactions) {
                YearMonth ym = YearMonth.from(t.getDateTime());
                monthlyMap.put(ym, monthlyMap.getOrDefault(ym, 0.0) + t.getAmount());
            }
            for (YearMonth ym : monthlyMap.keySet()) {
                report.append(String.format("%s: $%.2f\n", ym.format(fmt), monthlyMap.get(ym)));
            }
        }

        outputArea.setText(report.toString());
    }

    private void exportReport() {
        String content = outputArea.getText();
        if (content.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Generate a report first.");
            return;
        }
        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                java.nio.file.Files.write(fc.getSelectedFile().toPath(),
                        content.getBytes());
                JOptionPane.showMessageDialog(this, "Report exported successfully.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onBack() {
        dispose();
        new MainFrame(controller).setVisible(true);
    }
}
