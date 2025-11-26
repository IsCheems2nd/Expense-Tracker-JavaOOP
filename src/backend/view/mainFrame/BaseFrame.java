package view;

import controller.TransactionController;
import javax.swing.*;

public abstract class BaseFrame extends JFrame {
    protected TransactionController controller;

    public BaseFrame(String title, TransactionController controller, int width, int height) {
        this.controller = controller;
        setTitle(title);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLayout(null);
        setLocationRelativeTo(null);
        addGuiComponents();
        
        // Note: avoid recreating components on every resize to prevent event listener issues
        // If you need responsive layout, override and implement a safe repositioning method in subclasses.
    }

    public TransactionController getController() {
        return controller;
    }

    protected abstract void addGuiComponents();
}
