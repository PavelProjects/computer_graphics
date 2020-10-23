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
    private static Dimension minWindowSize = new Dimension(600, 300);


    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            JFrame mainWindow = new JFrame(lab_name);
            mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainWindow.setLocation(500, 1800);

            CubeModel cube = new CubeModel(200);
            ModelPanel modelPanel = new ModelPanel(cube);

            JPanel toolsPanel = new JPanel();
            toolsPanel.setLayout(new BoxLayout(toolsPanel, BoxLayout.Y_AXIS));

            String[] values = {"default", "red", "blue", "green", "orange", "cyan", "yellow"};
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
                block.setMaximumSize(new Dimension(200,50));
                block.setMinimumSize(new Dimension(200,50));
                toolsPanel.add(block, BorderLayout.WEST);
            }
            JPanel block = new JPanel();
            block.setMaximumSize(new Dimension(200,50));
            block.setMinimumSize(new Dimension(200,50));
            JButton defaultViewButt = new JButton("default view");
            defaultViewButt.setFocusable(false);
            defaultViewButt.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    modelPanel.showFace(null);
                }
            });
            block.add(defaultViewButt);
            toolsPanel.add(block, BorderLayout.WEST);

            JPanel block1 = new JPanel();
            block1.setMaximumSize(new Dimension(200,50));
            block1.setMinimumSize(new Dimension(200,50));
            JButton spinButton = new JButton("spin model");
            spinButton.setFocusable(false);
            spinButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    modelPanel.spinModel();
                }
            });
            block1.add(spinButton);
            toolsPanel.add(block1, BorderLayout.WEST);

            JPanel block2 = new JPanel();
            block2.setMaximumSize(new Dimension(200,50));
            block2.setMinimumSize(new Dimension(200,50));
            JButton projButton = new JButton("projected");
            projButton.setFocusable(false);
            projButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cube.changeProjected();
                    modelPanel.rescale();
                    modelPanel.repaint();
                }
            });
            block2.add(projButton);
            toolsPanel.add(block2, BorderLayout.WEST);

            JScrollPane scrollPane = new JScrollPane(toolsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            mainWindow.add(modelPanel, BorderLayout.CENTER);
            mainWindow.add(scrollPane, BorderLayout.WEST);

            mainWindow.addMouseMotionListener(modelPanel);
            mainWindow.addMouseListener(modelPanel);
            mainWindow.addMouseWheelListener(modelPanel);
            mainWindow.addComponentListener(modelPanel);
            mainWindow.addKeyListener(modelPanel);

            mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
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
