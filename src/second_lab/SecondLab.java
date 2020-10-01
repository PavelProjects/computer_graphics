package second_lab;

import javax.swing.*;
import java.awt.*;

public class SecondLab extends JPanel {
    public static String lab_name = "First Lab";

    private static JPanel modelFrame;

    private static Dimension mainWindowSize = new Dimension(300, 50);


    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            JFrame mainWindow = new JFrame(lab_name);
            mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            ModelPanel modelPanel = new ModelPanel();
            mainWindow.add(modelPanel, BorderLayout.CENTER);

            mainWindow.addComponentListener(modelPanel);
            mainWindow.addMouseListener(modelPanel);
            mainWindow.addMouseMotionListener(modelPanel);
            mainWindow.addMouseWheelListener(modelPanel);

            mainWindow.setPreferredSize(mainWindowSize);
            mainWindow.setResizable(false);
            mainWindow.pack();
            mainWindow.setVisible(true);
        });
    }
}
