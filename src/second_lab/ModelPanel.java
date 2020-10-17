package second_lab;

import javax.swing.*;
import java.awt.*;

public class ModelPanel extends JPanel{
    Model model;

    public ModelPanel(Model model){
        this.model = model;
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        g.translate(getWidth()/2, getHeight()/2);
        model.draw(g);
    }
}
