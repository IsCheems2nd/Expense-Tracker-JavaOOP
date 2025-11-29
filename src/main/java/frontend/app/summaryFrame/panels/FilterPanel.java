package frontend.app.summaryFrame.panels;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import frontend.components.UIComponentFactory;

public class FilterPanel extends JPanel {
    private JComboBox<String> periodComboBox;
    private JComboBox<String> typeComboBox;
    private FilterChangeListener listener;

    public interface FilterChangeListener {
        void onFilterChanged(String period, String type);
    }

    public FilterPanel(int width, FilterChangeListener listener) {
        this.listener = listener;
        setLayout(null);
        addFilterComponents(width);
    }

    private void addFilterComponents(int width) {
        int labelWidth = (width - 30) / 2;
        int comboWidth = labelWidth;
        
        // Period filter (Week/Month)
        add(createPeriodLabel(5, 0, labelWidth));
        periodComboBox = createPeriodComboBox(5, 40, comboWidth);
        add(periodComboBox);

        // Type filter (Income/Expense)
        int typeX = labelWidth + 20;
        add(createTypeLabel(typeX, 0, labelWidth));
        typeComboBox = createTypeComboBox(typeX, 40, comboWidth);
        add(typeComboBox);
    }

    private JLabel createPeriodLabel(int x, int y, int width) {
        return UIComponentFactory.createLabel(
                "Period", x, y, width, 40, 20, SwingConstants.LEFT
        );
    }

    private JComboBox<String> createPeriodComboBox(int x, int y, int width) {
        String[] periods = {"Week", "Month"};
        JComboBox<String> combo = UIComponentFactory.createStringComboBox(
                periods, x, y, width - 5, 35, 18
        );
        combo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED && listener != null) {
                listener.onFilterChanged(
                        (String) periodComboBox.getSelectedItem(),
                        (String) typeComboBox.getSelectedItem()
                );
            }
        });
        return combo;
    }

    private JLabel createTypeLabel(int x, int y, int width) {
        return UIComponentFactory.createLabel(
                "Type", x, y, width, 40, 20, SwingConstants.LEFT
        );
    }

    private JComboBox<String> createTypeComboBox(int x, int y, int width) {
        String[] types = {"Expense", "Income"};
        JComboBox<String> combo = UIComponentFactory.createStringComboBox(
                types, x, y, width - 5, 35, 18
        );
        combo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED && listener != null) {
                listener.onFilterChanged(
                        (String) periodComboBox.getSelectedItem(),
                        (String) typeComboBox.getSelectedItem()
                );
            }
        });
        return combo;
    }

    public String getSelectedPeriod() {
        return (String) periodComboBox.getSelectedItem();
    }

    public String getSelectedType() {
        return (String) typeComboBox.getSelectedItem();
    }
}
