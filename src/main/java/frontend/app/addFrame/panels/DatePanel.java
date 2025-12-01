package frontend.app.addFrame.panels;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.stream.IntStream;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import frontend.components.UIComponentFactory;

public class DatePanel extends JPanel {

    private JComboBox<Integer> yearComboBox;
    private JComboBox<Integer> monthComboBox;
    private JComboBox<Integer> dayComboBox;

    public DatePanel(int width) {

        setLayout(null);
        addDateComponents(width);

        setCurrentDate();
    }

    private void addDateComponents(int width) {
        add(createDateLabel(width));

        JPanel comboBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));

        comboBoxPanel.setBounds(5, 40, width - 10, 40);

        yearComboBox = createYearComboBox();
        comboBoxPanel.add(yearComboBox);

        monthComboBox = createMonthComboBox();
        comboBoxPanel.add(monthComboBox);

        dayComboBox = createDayComboBox();
        comboBoxPanel.add(dayComboBox);

        yearComboBox.addActionListener(e -> updateDays());
        monthComboBox.addActionListener(e -> updateDays());

        add(comboBoxPanel);
    }

    private JLabel createDateLabel(int width) {
        return UIComponentFactory.createLabel(
                "Date (Year - Month - Day)", 5, 0, width - 10, 40, 26, SwingConstants.LEFT
        );
    }

    private JComboBox<Integer> createYearComboBox() {
        int currentYear = LocalDate.now().getYear();

        Integer[] years = IntStream.rangeClosed(currentYear - 5, currentYear + 1)
                .mapToObj(Integer::valueOf)
                .sorted((a, b) -> b.compareTo(a))
                .toArray(Integer[]::new);

        JComboBox<Integer> comboBox = new JComboBox<>(years);
        comboBox.setFont(new Font("Dialog", Font.PLAIN, 20));
        comboBox.setPreferredSize(new Dimension(80, 40));
        return comboBox;
    }

    private JComboBox<Integer> createMonthComboBox() {

        Integer[] months = IntStream.rangeClosed(1, 12)
                .mapToObj(Integer::valueOf)
                .toArray(Integer[]::new);

        JComboBox<Integer> comboBox = new JComboBox<>(months);
        comboBox.setFont(new Font("Dialog", Font.PLAIN, 20));
        comboBox.setPreferredSize(new Dimension(60, 40));
        return comboBox;
    }

    private JComboBox<Integer> createDayComboBox() {

        Integer[] days = IntStream.rangeClosed(1, 31)
                .mapToObj(Integer::valueOf)
                .toArray(Integer[]::new);

        JComboBox<Integer> comboBox = new JComboBox<>(days);
        comboBox.setFont(new Font("Dialog", Font.PLAIN, 20));
        comboBox.setPreferredSize(new Dimension(60, 40));
        return comboBox;
    }

    private void setCurrentDate() {
        LocalDate today = LocalDate.now();
        yearComboBox.setSelectedItem(today.getYear());
        monthComboBox.setSelectedItem(today.getMonthValue());
        updateDays();
        dayComboBox.setSelectedItem(today.getDayOfMonth());
    }

    private void updateDays() {
        Integer selectedYear = (Integer) yearComboBox.getSelectedItem();
        Integer selectedMonth = (Integer) monthComboBox.getSelectedItem();

        if (selectedYear == null || selectedMonth == null) {
            return;
        }

        YearMonth yearMonth = YearMonth.of(selectedYear, selectedMonth);
        int daysInMonth = yearMonth.lengthOfMonth(); //handle leap years and special months

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

    public String getSelectedDateString() {
        Integer year = (Integer) yearComboBox.getSelectedItem();
        Integer month = (Integer) monthComboBox.getSelectedItem();
        Integer day = (Integer) dayComboBox.getSelectedItem();

        if (year == null || month == null || day == null) {
            return LocalDate.now().toString();
        }

        return String.format("%04d-%02d-%02d", year, month, day);
    }

    public String getDateText() {
        return getSelectedDateString();
    }

    public void clear() {
        setCurrentDate();
    }

}
