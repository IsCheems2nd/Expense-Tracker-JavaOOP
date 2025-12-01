package frontend.app.transactionHistory.panels;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import backend.controller.TransactionController;
import frontend.app.mainFrame.MainFrame;
import frontend.app.transactionHistory.frame.TransactionHistoryFrame;
import frontend.components.UIComponentFactory;

//go back button
public class TransactionHistoryButtonPanel extends JPanel{
    private final TransactionHistoryFrame source;
    private final TransactionController controller;

    public TransactionHistoryButtonPanel(TransactionHistoryFrame source, TransactionController controller, int width) {
        this.source = source;
        this.controller = new TransactionController();

        setLayout(null);
        addButtons(width);
    }

    private void addButtons(int width)
    {
        add(createGoBackButton(width));
    }

    private JButton createGoBackButton(int width)
    {
        JButton button = UIComponentFactory.createButton(
                "Go Back", 10, 0, width - 20, 40, 25
        );
        button.addActionListener(createGoBackButtonActionListener());
        return button;
    }

    private ActionListener createGoBackButtonActionListener()
    {
        return e -> {
            source.dispose();
            new MainFrame(controller).setVisible(true);
        };
    }
}