package second_lab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ModelPanel extends JPanel implements MouseWheelListener, MouseMotionListener, MouseListener, ComponentListener {
    Model model;
    double angleX = 200, angleY = 200;
    int prevX = 0, prevY = 0;
    int x0, y0;
    double scale = 1;

    public ModelPanel(Model model) {
        this.model = model;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.translate(x0, y0);
        g.setColor(Color.BLACK);
//        model.draw(g, sliderX.getValue(), sliderY.getValue());
        model.draw(g, angleX, angleY, scale);
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        prevY = e.getY();
        prevX = e.getX();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        prevX = prevY = 0;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        angleY += prevX - e.getX();
        angleX += prevY - e.getY();
        repaint();
        prevX = e.getX();
        prevY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() > 0) {
            scale = scale >= 1 ? scale - 0.1 : scale;
        } else {
            scale = scale < 1000 ? scale + 0.1 : scale;
        }
        repaint();
    }

    @Override
    public void componentResized(ComponentEvent e) {
        x0 = getWidth() / 2 - model.getCenter().getIntX() / 2;
        y0 = getHeight() / 2 - model.getCenter().getIntY() / 2;
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
