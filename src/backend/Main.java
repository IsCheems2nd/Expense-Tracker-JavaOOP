import controller.TransactionController;
import view.MainFrame;

public class Main {
    public static void main(String[] args) {
        TransactionController controller = new TransactionController();
        controller.loadFromFile("src/backend/data/transactions.csv");

        javax.swing.SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame(controller);
            frame.setVisible(true);
        });
    }
}

