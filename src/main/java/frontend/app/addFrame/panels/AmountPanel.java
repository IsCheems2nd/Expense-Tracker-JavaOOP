package frontend.app.addFrame.panels;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.PlainDocument;

import frontend.components.AmountDocumentFilter;
import frontend.components.UIComponentFactory;

public class AmountPanel extends JPanel {
    private JTextField amountEnteringTextField;
    private JComboBox<String> currencyComboBox;

    public AmountPanel(int width) {
        setLayout(null);
        addAmountEnteringComponents(width);
    }

    public JTextField getAmountEnteringTextField() {
        return amountEnteringTextField;
    }

    public JComboBox<String> getCurrencyComboBox() { 
        return currencyComboBox;
    }

    private void addAmountEnteringComponents(int width)
    {
        add(createAmountLabel(width));
        int totalSpace = width - 10;
        int textFieldWidth = (int) (totalSpace * 0.55); // ~55% width
        int comboBoxWidth = (int) (totalSpace * 0.35); // ~35% width
        int gap = 10;
        int startX = 5;
        int y = 40;
        int height = 40;
        amountEnteringTextField = createAmountTextField(startX, y, textFieldWidth, height);
        add(amountEnteringTextField);

        currencyComboBox = createCurrencyComboBox(startX + textFieldWidth + gap, y, comboBoxWidth, height); 
        add(currencyComboBox);
    }

    private JLabel createAmountLabel(int width)
    {
        return UIComponentFactory.createLabel(
                "Amount", 5, 0, width - 10, 40, 26, SwingConstants.LEFT
        );
    }

    private JTextField createAmountTextField(int x, int y, int w, int h)
    {
        JTextField amountTextField =  UIComponentFactory.createTextField(
                x, y, w, h, 20, true
        );

        PlainDocument plainDocument = (PlainDocument) amountTextField.getDocument();
        plainDocument.setDocumentFilter(new AmountDocumentFilter()); 

        return amountTextField;
    }

    private JComboBox<String> createCurrencyComboBox(int x, int y, int w, int h)
    {
        // Currencies requested by the user
        String[] currencies = {"USD", "EUR", "VND"}; 
        
        JComboBox<String> comboBox = UIComponentFactory.createStringComboBox(
                currencies, x, y, w, h, 20
        );
        comboBox.setSelectedItem("USD"); // Set default currency
        return comboBox;
    }

    public String getAmountText() {
        return amountEnteringTextField.getText();
    }

    public void setAmountText(String amountText) {
        amountEnteringTextField.setText(amountText);
    }

    public void clear() {
        amountEnteringTextField.setText("");
    }
}