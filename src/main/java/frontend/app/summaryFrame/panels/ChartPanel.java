package frontend.app.summaryFrame.panels;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import backend.controller.TransactionController;
import backend.model.Transaction;
import frontend.app.summaryFrame.util.CategoryUtil;

public class ChartPanel extends JPanel {
    private TransactionController controller;
    private org.jfree.chart.ChartPanel chartPanelComponent;
    private DefaultCategoryDataset dataset;

    public ChartPanel(TransactionController controller) {
        this.controller = controller;
        setLayout(null);
        dataset = new DefaultCategoryDataset();
        updateChart("Week", "Expense");
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        if (chartPanelComponent != null) {
            chartPanelComponent.setBounds(0, 0, width, height);
        }
    }

    public void updateChart(String period, String type) {
        boolean isExpense = "Expense".equals(type);
        
        // Get filtered transactions
        List<Transaction> filteredTransactions = getFilteredTransactions(period, isExpense);
        
        // Group by category
        Map<String, Double> categoryTotals = groupByCategory(filteredTransactions, isExpense);
        
        // Update dataset
        dataset.clear();
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            String category = entry.getKey();
            double amount = entry.getValue();
            dataset.addValue(Math.abs(amount), "Amount", category);
        }
        
        // Create or update chart
        String chartTitle = String.format("%s by Category (%s)", type, period);
        String yAxisLabel = "Amount ($)";
        
        JFreeChart chart = ChartFactory.createBarChart(
                chartTitle,
                "Category",
                yAxisLabel,
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );
        
        // Customize chart appearance
        CategoryPlot plot = chart.getCategoryPlot();
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setMaximumCategoryLabelWidthRatio(0.8f);
        
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());
        
        // Format numbers to show full values instead of scientific notation (handles 7-8 digit values)
        NumberFormat numberFormat = new DecimalFormat("#,###.##");
        numberFormat.setGroupingUsed(true);
        rangeAxis.setNumberFormatOverride(numberFormat);
        
        // Remove old chart panel if exists
        if (this.chartPanelComponent != null) {
            remove(this.chartPanelComponent);
        }
        
        // Create new chart panel (using fully qualified name to avoid conflict with this class name)
        this.chartPanelComponent = new org.jfree.chart.ChartPanel(chart);
        
        // Set bounds to fill the parent panel
        int width = getWidth() > 0 ? getWidth() : 800;
        int height = getHeight() > 0 ? getHeight() : 400;
        this.chartPanelComponent.setBounds(0, 0, width, height);
        
        add(this.chartPanelComponent);
        revalidate();
        repaint();
    }

    private List<Transaction> getFilteredTransactions(String period, boolean isExpense) {
        List<Transaction> allTransactions = controller.getAllTransactions();
        
        // Filter by type (expense has negative amounts, income has positive)
        List<Transaction> typeFiltered = allTransactions.stream()
                .filter(t -> {
                    boolean transactionIsExpense = CategoryUtil.isExpenseCategory(t.getCategory());
                    boolean transactionIsIncome = CategoryUtil.isIncomeCategory(t.getCategory());
                    
                    if (isExpense) {
                        return transactionIsExpense;
                    } else {
                        return transactionIsIncome;
                    }
                })
                .collect(java.util.stream.Collectors.toList());
        
        // Filter by date range
        LocalDate now = LocalDate.now();
        LocalDate startDate;
        
        if ("Week".equals(period)) {
            startDate = now.minusWeeks(1);
        } else { // Month
            startDate = now.minusMonths(1);
        }
        
        final LocalDate finalStartDate = startDate;
        List<Transaction> dateFiltered = typeFiltered.stream()
                .filter(t -> {
                    LocalDate transactionDate = t.getDateTime().toLocalDate();
                    return !transactionDate.isBefore(finalStartDate) && !transactionDate.isAfter(now);
                })
                .collect(java.util.stream.Collectors.toList());
        
        return dateFiltered;
    }

    private Map<String, Double> groupByCategory(List<Transaction> transactions, boolean isExpense) {
        Map<String, Double> totals = new HashMap<>();
        
        // Initialize all categories with 0
        String[] categories = isExpense 
                ? CategoryUtil.getExpenseCategories() 
                : CategoryUtil.getIncomeCategories();
        
        for (String category : categories) {
            totals.put(category, 0.0);
        }
        
        // Sum amounts by category
        for (Transaction t : transactions) {
            String category = t.getCategory();
            double amount = Math.abs(controller.getBaseCurrencyAmount(t));
            
            totals.put(category, totals.getOrDefault(category, 0.0) + amount);
        }
        
        // Remove categories with zero amounts for cleaner chart
        totals.entrySet().removeIf(entry -> entry.getValue() == 0.0);
        
        return totals;
    }
}
