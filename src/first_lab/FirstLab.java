package first_lab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class FirstLab extends JPanel {
    public static String lab_name = "First Lab";

    private static JFrame graphFrame;

    private static Dimension controlWindowSize = new Dimension(300, 50);
    private static Dimension graphWindowSize = new Dimension(500, 500);


    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            JFrame controlFrame = new JFrame(lab_name);
            controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JTextField textFieldA = new JTextField(20);
            textFieldA.setMaximumSize(new Dimension(100, 20));

            JTextField textFieldB = new JTextField(20);
            textFieldB.setMaximumSize(new Dimension(100, 20));

            textFieldA.addActionListener(new OpenGraphListener(textFieldA, textFieldB));
            textFieldB.addActionListener(new OpenGraphListener(textFieldA, textFieldB));

            JButton button = new JButton("Create");
            button.addActionListener(new OpenGraphListener(textFieldA, textFieldB));

            JPanel controlPanel = new JPanel();
            controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
            controlPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
            controlPanel.add(Box.createHorizontalGlue());
            controlPanel.add(new JLabel("a="));
            controlPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            controlPanel.add(textFieldA);
            controlPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            controlPanel.add(new JLabel("b="));
            controlPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            controlPanel.add(textFieldB);
            controlPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            controlPanel.add(button);

            controlFrame.getContentPane().add(controlPanel, BorderLayout.CENTER);
            controlFrame.setPreferredSize(controlWindowSize);
            controlFrame.setResizable(false);
            controlFrame.pack();
            controlFrame.setVisible(true);
        });
    }

    public static void createGraphWindow(float a, float b) {
        graphFrame = new JFrame(String.format("GRAPH :: x=%s*sin(t); y=%s*cos(t)", a, b));
        GraphPanel panel = new GraphPanel(a, b);
        graphFrame.add(panel, BorderLayout.CENTER);

        graphFrame.addMouseWheelListener(panel);
        graphFrame.addMouseMotionListener(panel);
        graphFrame.addMouseListener(panel);
        graphFrame.addComponentListener(panel);

        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
        graphFrame.getContentPane().setCursor(blankCursor);

        graphFrame.setPreferredSize(graphWindowSize);
        graphFrame.setPreferredSize(new Dimension(500, 500));
        graphFrame.setMinimumSize(new Dimension(100, 100));
        graphFrame.pack();
        graphFrame.setVisible(true);
    }
}

class OpenGraphListener implements ActionListener {
    JTextField textFieldA;
    JTextField textFieldB;
    public static String values = "1234567890.";

    public OpenGraphListener(JTextField textFieldA, JTextField textFieldB) {
        this.textFieldA = textFieldA;
        this.textFieldB = textFieldB;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!textFieldA.getText().isBlank() && !textFieldB.getText().isBlank()) {
            StringBuilder valueA = new StringBuilder();
            textFieldA.getText().chars().forEach((c) -> {
                valueA.append(values.indexOf((char) c) != -1 ? (char) c : "");
            });
            textFieldA.setText(valueA.toString());

            StringBuilder valueB = new StringBuilder();
            textFieldB.getText().chars().forEach((c) -> {
                valueB.append(values.indexOf((char) c) != -1 ? (char) c : "");
            });
            textFieldB.setText(valueB.toString());

            if (!valueA.toString().isBlank() && !valueB.toString().isBlank()) {
                FirstLab.createGraphWindow(Float.parseFloat(valueA.toString()), Float.parseFloat(valueB.toString()));
            }
        }
    }
}