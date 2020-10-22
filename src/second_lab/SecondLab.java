package second_lab;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SecondLab extends JPanel {
    public static String lab_name = "Second Lab";

    private static Dimension mainWindowSize = new Dimension(800, 800);
    private static Dimension minWindowSize = new Dimension(200, 200);


    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            JFrame mainWindow = new JFrame(lab_name);
            mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainWindow.setLocation(500, 1800);
            mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);

            CubeModel cube = new CubeModel(200);
            ModelPanel modelPanel = new ModelPanel(cube);

            String[] values = {"default", "red", "blue", "green", "orange", "cyan", "yellow"};
            JPanel toolsPanel = new JPanel();

            for(ModelFace face : cube.getFaces()){
                JComboBox<String> comboBox = new JComboBox<>(values);
                comboBox.addActionListener(new ComboBoxListener(face, comboBox, modelPanel));
                comboBox.setFocusable(false);

                JButton button = new JButton(face.getName());
                button.setFocusable(false);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        modelPanel.showFace(face);
                    }
                });

                JPanel block = new JPanel();
                block.add(button);
                block.add(comboBox);
                block.setBorder(new EmptyBorder(10,10,10,10));
                toolsPanel.add(block);
            }

            JButton button = new JButton("default view");
            button.setFocusable(false);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    modelPanel.showFace(null);
                }
            });
            toolsPanel.add(button);

            mainWindow.add(modelPanel, BorderLayout.CENTER);
            mainWindow.add(toolsPanel, BorderLayout.SOUTH);

            mainWindow.addMouseMotionListener(modelPanel);
            mainWindow.addMouseListener(modelPanel);
            mainWindow.addMouseWheelListener(modelPanel);
            mainWindow.addComponentListener(modelPanel);
            mainWindow.addKeyListener(modelPanel);

//            mainWindow.setPreferredSize(mainWindowSize);
            mainWindow.setMinimumSize(minWindowSize);
            mainWindow.setResizable(true);
            mainWindow.pack();
            mainWindow.setVisible(true);
        });
    }
}

class ComboBoxListener implements ActionListener {
    ModelFace f;
    JComboBox comboBox;
    JPanel panel;

    public ComboBoxListener(ModelFace i, JComboBox comboBox, JPanel panel){
        this.f = i;
        this.comboBox = comboBox;
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch ((String) comboBox.getSelectedItem()){
            case "red":
                f.setColor(Color.RED);
                break;
            case "blue":
                f.setColor(Color.BLUE);
                break;
            case "green":
                f.setColor(Color.GREEN);
                break;
            case "cyan":
                f.setColor(Color.CYAN);
                break;
            case "orange":
                f.setColor(Color.ORANGE);
                break;
            case "yellow":
                f.setColor(Color.YELLOW);
                break;
            default:
                f.setColor(Color.BLACK);
                break;
        }
        panel.repaint();
    }
}
