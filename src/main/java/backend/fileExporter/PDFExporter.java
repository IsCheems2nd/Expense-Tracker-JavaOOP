package backend.fileExporter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import backend.controller.TransactionController;
import backend.model.Transaction;

public class PDFExporter {
    private String filePath;
    private List<Transaction> transactionList;
    private LocalDate startDate;
    private LocalDate endDate;
    private TransactionController controller;

    private final String[] headers = {"Date", "Type", "Category", "Amount", "Currency", "Description"};

    public PDFExporter(String filePath, List<Transaction> transactionList, LocalDate startDate, LocalDate endDate, TransactionController controller) {
        this.filePath = filePath;
        this.transactionList = transactionList;
        this.startDate = startDate;
        this.endDate = endDate;
        this.controller = controller;
    }

    public void exportFile() {
        if (transactionList == null || transactionList.isEmpty()) {
            return;
        }

        try {
            PdfWriter pdfWriter = new PdfWriter(filePath);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);

            Table summaryTable = createSummaryTable();
            document.add(summaryTable);

            Table transactionTable = createTransactionTable();
            document.add(transactionTable);

            document.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + filePath, e);
        } catch (IOException e) {
            throw new RuntimeException("Error while closing the document", e);
        }
    }

    private Table createSummaryTable() {
        float[] tableCols = new float[]{1, 1, 1};
        Table table = new Table(tableCols);
        table.setWidth(UnitValue.createPercentValue(100));
        table.setMarginBottom(20);
        table.setBorder(Border.NO_BORDER);
        populateSummaryTable(table);
        return table;
    }

    private Table createTransactionTable() {
        float[] tableCols = new float[]{2, 1, 2, 2, 1, 3};
        Table table = new Table(tableCols);
        table.setWidth(UnitValue.createPercentValue(100));
        table.setMarginTop(10);
        populateTransactionTable(table);
        return table;
    }

    private void populateTransactionTable(Table table) {
        addTransactionHeaders(table);

        for (Transaction transaction : transactionList) {
            String type = transaction.getAmount() >= 0 ? "Income" : "Expense";
            String amountStr = String.format("%.2f", Math.abs(transaction.getAmount()));
            String dateStr = transaction.getDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            
            addTransactionCell(table,
                dateStr,
                type,
                transaction.getCategory(),
                amountStr,
                transaction.getCurrencyCode(),
                transaction.getDescription() != null ? transaction.getDescription() : ""
            );
        }
    }

    private void populateSummaryTable(Table table) {
        addSummaryCell(table, createReportDateBoundsInfo(), 10, false);
        addSummaryCell(table, createReportGeneratedDate(), 10, false);
        table.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));

        addSummaryCell(table, createBalanceInfo(), 12, true);
        addSummaryCell(table, createTotalExpensesInfo(), 12, true);
        addSummaryCell(table, createTotalIncomeInfo(), 12, true);
    }

    private void addSummaryCell(Table table, String text, int fontSize, boolean isBold) {
        Paragraph paragraph = new Paragraph(text).setFontSize(fontSize).setTextAlignment(TextAlignment.LEFT);
        if (isBold) {
            paragraph.setBold();
        }
        Cell cell = new Cell().add(paragraph).setTextAlignment(TextAlignment.LEFT).setBorder(null).setPadding(5);
        table.addCell(cell);
    }

    private void addTransactionCell(Table table, String... texts) {
        for (String text : texts) {
            Cell cell = new Cell().add(new Paragraph(text));
            cell.setPadding(5);
            table.addCell(cell);
        }
    }

    private void addTransactionHeaders(Table table) {
        for (String header : headers) {
            Cell cell = new Cell().add(new Paragraph(header));
            cell.setBackgroundColor(new DeviceRgb(200, 200, 200));
            cell.setTextAlignment(TextAlignment.CENTER);
            cell.setPadding(5);
            table.addCell(cell);
        }
    }

    private String createReportDateBoundsInfo() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return "Transaction Dates: \n" +
                startDate.format(dateTimeFormatter)
                + "—" +
                endDate.format(dateTimeFormatter);
    }

    private String createReportGeneratedDate() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        return "Report Generated on:\n" + LocalDateTime.now().format(dateTimeFormatter);
    }

    private String createBalanceInfo() {
        double balance = controller.getCurrentBalance();
        return "Balance:\n$" + String.format("%.2f", balance);
    }

    private String createTotalExpensesInfo() {
        double totalExpenses = transactionList.stream()
                .filter(t -> t.getAmount() < 0)
                .mapToDouble(t -> Math.abs(controller.getBaseCurrencyAmount(t)))
                .sum();
        return "Total Expenses:\n$" + String.format("%.2f", totalExpenses);
    }

    private String createTotalIncomeInfo() {
        double totalIncome = transactionList.stream()
                .filter(t -> t.getAmount() > 0)
                .mapToDouble(t -> controller.getBaseCurrencyAmount(t))
                .sum();
        return "Total Income:\n$" + String.format("%.2f", totalIncome);
    }
}

