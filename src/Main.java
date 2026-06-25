package src;

import src.ui.SwingUI;

public class Main {
    public static void main(String[] args) {
        // Run GUI on Event Dispatch Thread
        javax.swing.SwingUtilities.invokeLater(() -> {
            new SwingUI();
        });
    }
}