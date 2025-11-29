package frontend.app.mainFrame;

import javax.swing.JFrame;
import javax.swing.JSeparator;

import backend.controller.TransactionController;
import frontend.components.UIComponentFactory;


public abstract class BaseFrame extends JFrame {
    protected TransactionController controller;

    public BaseFrame(String title, TransactionController controller, int width, int height) {
        this.controller = controller;
        initializeComponents(title, width, height);
        
    }

    public TransactionController getController() { //encapsulation
        return controller;
    }
    
    private void initializeComponents(String title, int width, int height) {
        setTitle(title);
        setSize(width, height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        addGuiComponents();
    }

    protected JSeparator createSeparator()
    {
        return UIComponentFactory.createSeparator(15, 50, getWidth() - 30, 10);
    }

    protected abstract void addGuiComponents();
}
