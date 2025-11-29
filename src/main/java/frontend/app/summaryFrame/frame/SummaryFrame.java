package frontend.app.summaryFrame.frame;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import backend.controller.TransactionController;
import frontend.app.mainFrame.BaseFrame;
import frontend.app.summaryFrame.panels.ChartPanel;
import frontend.app.summaryFrame.panels.FilterPanel;
import frontend.app.summaryFrame.panels.SummaryFrameButtonPanel;
import frontend.components.UIComponentFactory;

public class SummaryFrame extends BaseFrame {
    private FilterPanel filterPanel;
    private ChartPanel chartPanel;
    private SummaryFrameButtonPanel buttonPanel;

    public SummaryFrame(String title, TransactionController controller, int width, int height) {
        super(title, controller, width, height);
    }

    @Override
    protected void addGuiComponents() {
        addWelcomingComponents();
        addPanels();
    }

    private void addPanels() {
        initializePanels();
        arrangePanels();

        add(filterPanel);
        add(chartPanel);
        add(buttonPanel);

        revalidate();
        repaint();
    }

    private void initializePanels() {
        chartPanel = new ChartPanel(controller);
        
        filterPanel = new FilterPanel(getWidth(), (period, type) -> {
            chartPanel.updateChart(period, type);
        });

        buttonPanel = new SummaryFrameButtonPanel(this, controller, getWidth());
    }

    private void arrangePanels() {
        filterPanel.setBounds(0, 60, getWidth(), 80);
        chartPanel.setBounds(10, 150, getWidth() - 20, getHeight() - 250);
        buttonPanel.setBounds(0, getHeight() - 100, getWidth(), 40);
    }

    private void addWelcomingComponents() {
        add(createSeparator());
        add(createSummaryLabel());
    }

    private JLabel createSummaryLabel() {
        JLabel label = UIComponentFactory.createLabel(
                "Summary Report", 0, 0, getWidth(), 50, 24, SwingConstants.CENTER
        );
        label.setFont(label.getFont().deriveFont(Font.BOLD, 24f));
        return label;
    }
}
