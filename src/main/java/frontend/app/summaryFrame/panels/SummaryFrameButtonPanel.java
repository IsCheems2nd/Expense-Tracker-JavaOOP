package frontend.app.summaryFrame.panels;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import backend.controller.TransactionController;
import frontend.app.mainFrame.MainFrame;
import frontend.app.summaryFrame.frame.SummaryFrame;
import frontend.components.UIComponentFactory;

public class SummaryFrameButtonPanel extends JPanel {
    private final SummaryFrame source;
    private final TransactionController controller;

    public SummaryFrameButtonPanel(SummaryFrame source, TransactionController controller, int width) {
        this.source = source;
        this.controller = controller;

        setLayout(null);
        addButtons(width);
    }

    private void addButtons(int width) {
        add(createGoBackButton(width));
    }

    private JButton createGoBackButton(int width) {
        JButton button = UIComponentFactory.createButton(
                "Go Back", 10, 0, width - 20, 40, 25
        );
        button.addActionListener(createGoBackButtonActionListener());
        return button;
    }

    private ActionListener createGoBackButtonActionListener() {
        return e -> {
            source.dispose();
            new MainFrame(controller).setVisible(true);
        };
    }
}
