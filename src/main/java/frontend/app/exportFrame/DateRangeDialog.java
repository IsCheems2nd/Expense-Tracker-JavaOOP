package frontend.app.exportFrame;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.stream.IntStream;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import frontend.components.UIComponentFactory;

public class DateRangeDialog extends JDialog {

    private JComboBox<Integer> startYearComboBox;
    private JComboBox<Integer> startMonthComboBox;
    private JComboBox<Integer> startDayComboBox;
    private JComboBox<Integer> endYearComboBox;
    private JComboBox<Integer> endMonthComboBox;
    private JComboBox<Integer> endDayComboBox;
    private boolean confirmed = false;

    public DateRangeDialog(java.awt.Frame parent) {
        super(parent, "Select Date Range", true);
        setSize(500, 300);
        setLocationRelativeTo(parent);
        setLayout(null);
        setResizable(false);

        addDateRangeComponents();
    }

    private void addDateRangeComponents() {

        JLabel startDateLabel = UIComponentFactory.createLabel(
                "Start Date (Year - Month - Day)", 20, 20, 200, 30, 16, SwingConstants.LEFT
        );
        add(startDateLabel);

        JPanel startDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        startDatePanel.setBounds(20, 50, 450, 40);

        startYearComboBox = createYearComboBox();
        startDatePanel.add(startYearComboBox);

        startMonthComboBox = createMonthComboBox();
        startDatePanel.add(startMonthComboBox);

        startDayComboBox = createDayComboBox();
        startDatePanel.add(startDayComboBox);

        startYearComboBox.addActionListener(e -> updateDays(startYearComboBox, startMonthComboBox, startDayComboBox));
        startMonthComboBox.addActionListener(e -> updateDays(startYearComboBox, startMonthComboBox, startDayComboBox));

        add(startDatePanel);

        JLabel endDateLabel = UIComponentFactory.createLabel(
                "End Date (Year - Month - Day)", 20, 110, 200, 30, 16, SwingConstants.LEFT
        );
        add(endDateLabel);

        JPanel endDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        endDatePanel.setBounds(20, 140, 450, 40);

        endYearComboBox = createYearComboBox();
        endDatePanel.add(endYearComboBox);

        endMonthComboBox = createMonthComboBox();
        endDatePanel.add(endMonthComboBox);

        endDayComboBox = createDayComboBox();
        endDatePanel.add(endDayComboBox);

        endYearComboBox.addActionListener(e -> updateDays(endYearComboBox, endMonthComboBox, endDayComboBox));
        endMonthComboBox.addActionListener(e -> updateDays(endYearComboBox, endMonthComboBox, endDayComboBox));

        add(endDatePanel);

        JButton okButton = UIComponentFactory.createButton("OK", 150, 200, 80, 35, 16);
        okButton.addActionListener(e -> {
            confirmed = true;
            dispose();
        });
        add(okButton);

        JButton cancelButton = UIComponentFactory.createButton("Cancel", 270, 200, 120, 35, 16);
        cancelButton.addActionListener(e -> {
            confirmed = false;
            dispose();
        });
        add(cancelButton);

        setCurrentDates();
    }

    private JComboBox<Integer> createYearComboBox() {
        int currentYear = LocalDate.now().getYear();
        Integer[] years = IntStream.rangeClosed(currentYear - 5, currentYear + 1)
                .mapToObj(Integer::valueOf)
                .sorted((a, b) -> b.compareTo(a))
                .toArray(Integer[]::new);

        JComboBox<Integer> comboBox = new JComboBox<>(years);
        comboBox.setFont(new Font("Dialog", Font.PLAIN, 18));
        comboBox.setPreferredSize(new Dimension(80, 35));
        return comboBox;
    }

    private JComboBox<Integer> createMonthComboBox() {
        Integer[] months = IntStream.rangeClosed(1, 12)
                .mapToObj(Integer::valueOf)
                .toArray(Integer[]::new);

        JComboBox<Integer> comboBox = new JComboBox<>(months);
        comboBox.setFont(new Font("Dialog", Font.PLAIN, 18));
        comboBox.setPreferredSize(new Dimension(60, 35));
        return comboBox;
    }

    private JComboBox<Integer> createDayComboBox() {
        Integer[] days = IntStream.rangeClosed(1, 31)
                .mapToObj(Integer::valueOf)
                .toArray(Integer[]::new);

        JComboBox<Integer> comboBox = new JComboBox<>(days);
        comboBox.setFont(new Font("Dialog", Font.PLAIN, 18));
        comboBox.setPreferredSize(new Dimension(60, 35));
        return comboBox;
    }

    private void setCurrentDates() {
        LocalDate today = LocalDate.now();
        LocalDate oneMonthAgo = today.minusMonths(1);

        startYearComboBox.setSelectedItem(oneMonthAgo.getYear());
        startMonthComboBox.setSelectedItem(oneMonthAgo.getMonthValue());
        updateDays(startYearComboBox, startMonthComboBox, startDayComboBox);
        startDayComboBox.setSelectedItem(oneMonthAgo.getDayOfMonth());

        endYearComboBox.setSelectedItem(today.getYear());
        endMonthComboBox.setSelectedItem(today.getMonthValue());
        updateDays(endYearComboBox, endMonthComboBox, endDayComboBox);
        endDayComboBox.setSelectedItem(today.getDayOfMonth());
    }

    private void updateDays(JComboBox<Integer> yearComboBox, JComboBox<Integer> monthComboBox, JComboBox<Integer> dayComboBox) {
        Integer selectedYear = (Integer) yearComboBox.getSelectedItem();
        Integer selectedMonth = (Integer) monthComboBox.getSelectedItem();

        if (selectedYear == null || selectedMonth == null) {
            return;
        }

        YearMonth yearMonth = YearMonth.of(selectedYear, selectedMonth);
        int daysInMonth = yearMonth.lengthOfMonth();

        Integer currentDay = (Integer) dayComboBox.getSelectedItem();

        DefaultComboBoxModel<Integer> model = new DefaultComboBoxModel<>();
        for (int i = 1; i <= daysInMonth; i++) {
            model.addElement(i);
        }

        dayComboBox.setModel(model);

        if (currentDay != null && currentDay <= daysInMonth) {
            dayComboBox.setSelectedItem(currentDay);
        } else if (daysInMonth > 0) {
            dayComboBox.setSelectedItem(daysInMonth);
        }
    }

    public LocalDate getStartDate() {
        Integer year = (Integer) startYearComboBox.getSelectedItem();
        Integer month = (Integer) startMonthComboBox.getSelectedItem();
        Integer day = (Integer) startDayComboBox.getSelectedItem();

        if (year == null || month == null || day == null) {
            return LocalDate.now().minusMonths(1);
        }

        return LocalDate.of(year, month, day);
    }

    public LocalDate getEndDate() {
        Integer year = (Integer) endYearComboBox.getSelectedItem();
        Integer month = (Integer) endMonthComboBox.getSelectedItem();
        Integer day = (Integer) endDayComboBox.getSelectedItem();

        if (year == null || month == null || day == null) {
            return LocalDate.now();
        }

        return LocalDate.of(year, month, day);
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
