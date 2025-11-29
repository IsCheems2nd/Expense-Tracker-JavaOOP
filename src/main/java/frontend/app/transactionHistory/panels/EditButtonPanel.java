package frontend.app.transactionHistory.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import backend.controller.TransactionController;
import frontend.components.TransactionFlowFilter;
import frontend.components.UIComponentFactory;

public class EditButtonPanel extends JPanel {
    private EditDialog source;

    public EditButtonPanel(EditDialog source, int width) {
        this.source = source;

        setLayout(null);
        addButtons(width);
    }

    private void addButtons(int width)
    {
        add(createCancelButton(width));
        add(createSaveButton(width));
    }

    private JButton createCancelButton(int width)
    {
        JButton button = UIComponentFactory.createButton(
                "Cancel", (width - 10) / 2 + 5, 0, (width - 10) / 2, 40, 30
        );
        button.addActionListener(e -> source.dispose());
        return button;
    }

    private JButton createSaveButton(int width)
    {
        JButton button = UIComponentFactory.createButton(
                "Save", 5, 0, (width - 10) / 2, 40, 30
        );
        button.addActionListener(createSaveButtonActionListener());
        return button;
    }


    private ActionListener createSaveButtonActionListener() {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int transactionId = source.getHistoryCard().getId();

                if (!TransactionFlowFilter.validateAmountEntered(source.getAmountPanel().getAmountEnteringTextField().getText())) {
                    JOptionPane.showMessageDialog(source, "Amount entered must be a positive number!");
                    return;
                }

                double amount;
                try {
                    amount = Double.parseDouble(source.getAmountPanel().getAmountEnteringTextField().getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(source, "Amount must be a valid number!");
                    return;
                }

                String category = (String) source.getCategoryPanel().getCategoryComboBox().getSelectedItem();
                category = category != null ? category : "Other";
                String description = source.getDescriptionPanel().getDescriptionTextArea().getText();
                
                TransactionController controller = new TransactionController();
                boolean success = controller.updateTransaction(transactionId, amount, category, description);

                if (success) {
                    JOptionPane.showMessageDialog(source, "Transaction updated successfully!");
                    source.getSource().refreshCards(); 
                    source.dispose();
                } else {
                    JOptionPane.showMessageDialog(source, "An error occurred while updating the transaction.", "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
    }
}