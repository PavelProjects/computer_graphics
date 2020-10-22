package second_lab;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SecondLab extends JPanel {
    public static String lab_name = "Second Lab";

    private static Dimension mainWindowSize = new Dimension(800, 800);
    private static Dimension minWindowSize = new Dimension(200, 200);


    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            JFrame mainWindow = new JFrame(lab_name);
            mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            CubeModel cube = new CubeModel(200);

            ModelPanel modelPanel = new ModelPanel(cube);
            mainWindow.add(modelPanel, BorderLayout.CENTER);

            mainWindow.addMouseMotionListener(modelPanel);
            mainWindow.addMouseListener(modelPanel);
            mainWindow.addMouseWheelListener(modelPanel);
            mainWindow.addComponentListener(modelPanel);
            mainWindow.addKeyListener(modelPanel);

            mainWindow.setPreferredSize(mainWindowSize);
            mainWindow.setMinimumSize(minWindowSize);
            mainWindow.setResizable(true);
            mainWindow.pack();
            mainWindow.setVisible(true);
        });
    }
}
