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
        // Set layout to null for absolute positioning
        setLayout(null);
        addDateComponents(width);
        // Initialize the components with the current date
        setCurrentDate();
    }

    private void addDateComponents(int width) {
        add(createDateLabel(width));
        
        // Use a sub-panel for the dropdowns to manage horizontal layout
        JPanel comboBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        // Position the panel below the label
        comboBoxPanel.setBounds(5, 40, width - 10, 40);

        // 1. Year ComboBox (e.g., 5 years prior to 1 year ahead)
        yearComboBox = createYearComboBox();
        comboBoxPanel.add(yearComboBox);
        
        // 2. Month ComboBox (1 to 12)
        monthComboBox = createMonthComboBox();
        comboBoxPanel.add(monthComboBox);
        
        // 3. Day ComboBox (1 to 31 - updated dynamically)
        dayComboBox = createDayComboBox();
        comboBoxPanel.add(dayComboBox);
        
        // Add listeners to update the day list when month or year changes
        yearComboBox.addActionListener(e -> updateDays());
        monthComboBox.addActionListener(e -> updateDays());

        add(comboBoxPanel);
    }

    private JLabel createDateLabel(int width) {
        return UIComponentFactory.createLabel(
                "Date (Year - Month - Day)", 5, 0, width - 10, 40, 26, SwingConstants.LEFT
        );
    }
    
    // --- ComboBox Creation Methods ---

    private JComboBox<Integer> createYearComboBox() {
        int currentYear = LocalDate.now().getYear();
        // Years range from 5 years ago to 1 year ahead
        Integer[] years = IntStream.rangeClosed(currentYear - 5, currentYear + 1)
                                   .mapToObj(Integer::valueOf)
                                   .sorted((a, b) -> b.compareTo(a)) // Sort descending (newest first)
                                   .toArray(Integer[]::new);
        
        // Using a standard JComboBox for simplicity, assuming UIComponentFactory.createIntegerComboBox doesn't exist.
        JComboBox<Integer> comboBox = new JComboBox<>(years);
        comboBox.setFont(new Font("Dialog", Font.PLAIN, 20));
        comboBox.setPreferredSize(new Dimension(80, 40));
        return comboBox;
    }

    private JComboBox<Integer> createMonthComboBox() {
        // Months 1 to 12
        Integer[] months = IntStream.rangeClosed(1, 12)
                                    .mapToObj(Integer::valueOf)
                                    .toArray(Integer[]::new);
        
        JComboBox<Integer> comboBox = new JComboBox<>(months);
        comboBox.setFont(new Font("Dialog", Font.PLAIN, 20));
        comboBox.setPreferredSize(new Dimension(60, 40));
        return comboBox;
    }

    private JComboBox<Integer> createDayComboBox() {
        // Start with 31 days (will be dynamically updated)
        Integer[] days = IntStream.rangeClosed(1, 31)
                                  .mapToObj(Integer::valueOf)
                                  .toArray(Integer[]::new);
        
        JComboBox<Integer> comboBox = new JComboBox<>(days);
        comboBox.setFont(new Font("Dialog", Font.PLAIN, 20));
        comboBox.setPreferredSize(new Dimension(60, 40));
        return comboBox;
    }
    
    // --- Logic Methods ---

    /**
     * Sets the default selection to the current date.
     */
    private void setCurrentDate() {
        LocalDate today = LocalDate.now();
        yearComboBox.setSelectedItem(today.getYear());
        monthComboBox.setSelectedItem(today.getMonthValue());
        updateDays(); // Ensure days are correct for the current month
        dayComboBox.setSelectedItem(today.getDayOfMonth());
    }

    /**
     * Updates the days in the dayComboBox based on the selected month and year
     * to handle months with 28, 29, 30, or 31 days (including leap years).
     */
    private void updateDays() {
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

        // Try to re-select the previously selected day, or the last valid day of the month if overflowed
        if (currentDay != null && currentDay <= daysInMonth) {
            dayComboBox.setSelectedItem(currentDay);
        } else if (daysInMonth > 0) {
            dayComboBox.setSelectedItem(daysInMonth); // Select the last day of the new month
        }
    }
    
    // --- Getter and Utility Methods for the Controller ---
    
    /**
     * Gets the selected date as a formatted string YYYY-MM-DD.
     */
    public String getSelectedDateString() {
        Integer year = (Integer) yearComboBox.getSelectedItem();
        Integer month = (Integer) monthComboBox.getSelectedItem();
        Integer day = (Integer) dayComboBox.getSelectedItem();

        // Should not happen if initialized correctly, but as a safeguard:
        if (year == null || month == null || day == null) {
            return LocalDate.now().toString();
        }
        
        // This format guarantees a valid date string from the selected integer values
        return String.format("%04d-%02d-%02d", year, month, day);
    }
    
    /**
     * Used for compatibility with the old interface, delegates to getSelectedDateString().
     */
    public String getDateText() {
        return getSelectedDateString();
    }
    
    public void clear() {
        setCurrentDate();
    }

}