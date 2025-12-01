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

        List<Transaction> filteredTransactions = getFilteredTransactions(period, isExpense);

        Map<String, Double> categoryTotals = groupByCategory(filteredTransactions, isExpense);

        dataset.clear();
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            String category = entry.getKey();
            double amount = entry.getValue();
            dataset.addValue(Math.abs(amount), "Amount", category);
        }

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

        CategoryPlot plot = chart.getCategoryPlot();
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setMaximumCategoryLabelWidthRatio(0.8f);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());

        NumberFormat numberFormat = new DecimalFormat("#,###.##");
        numberFormat.setGroupingUsed(true);
        rangeAxis.setNumberFormatOverride(numberFormat);

        if (this.chartPanelComponent != null) {
            remove(this.chartPanelComponent);
        }

        this.chartPanelComponent = new org.jfree.chart.ChartPanel(chart);

        int width = getWidth() > 0 ? getWidth() : 800;
        int height = getHeight() > 0 ? getHeight() : 400;
        this.chartPanelComponent.setBounds(0, 0, width, height);

        add(this.chartPanelComponent);
        revalidate();
        repaint();
    }

    private List<Transaction> getFilteredTransactions(String period, boolean isExpense) {
        List<Transaction> allTransactions = controller.getAllTransactions();

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

        LocalDate now = LocalDate.now();
        LocalDate startDate;

        if ("Week".equals(period)) {
            startDate = now.minusWeeks(1);
        } else {
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

        String[] categories = isExpense
                ? CategoryUtil.getExpenseCategories()
                : CategoryUtil.getIncomeCategories();

        for (String category : categories) {
            totals.put(category, 0.0);
        }

        for (Transaction t : transactions) {
            String category = t.getCategory();
            double amount = Math.abs(controller.getBaseCurrencyAmount(t));

            totals.put(category, totals.getOrDefault(category, 0.0) + amount);
        }

        totals.entrySet().removeIf(entry -> entry.getValue() == 0.0);

        return totals;
    }
}
