package second_lab;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SecondLab extends JPanel {
    public static String lab_name = "First Lab";

    private static Dimension mainWindowSize = new Dimension(800, 800);


    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            JFrame mainWindow = new JFrame(lab_name);
            mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            CubeModel cube ;
            ArrayList<Vertex> vertexList = new ArrayList<>();
            vertexList.add(new Vertex(0, 0, 0));
            cube = new CubeModel(vertexList, 100);

            ModelPanel modelPanel = new ModelPanel(cube);
            mainWindow.add(modelPanel, BorderLayout.CENTER);

            mainWindow.addComponentListener(cube);
            mainWindow.addMouseListener(cube);
            mainWindow.addMouseMotionListener(cube);
            mainWindow.addMouseWheelListener(cube);

            mainWindow.setPreferredSize(mainWindowSize);
            mainWindow.setResizable(false);
            mainWindow.pack();
            mainWindow.setVisible(true);
        });
    }
}
