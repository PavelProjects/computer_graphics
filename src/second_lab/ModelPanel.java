package second_lab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ModelPanel extends JPanel implements MouseWheelListener, MouseMotionListener, MouseListener, ComponentListener {
    int x0, y0;
    CubeModel cube;
    ArrayList<Vertex> vertexes = new ArrayList<>();

    public ModelPanel(){
        vertexes.add(new Vertex(0, 0, 1));
        vertexes.add(new Vertex(1, 0, 1));
        vertexes.add(new Vertex(1, 1, 0));
        vertexes.add(new Vertex(0, 0, 1));
        vertexes.add(new Vertex(0, 0, 0));
        cube = new CubeModel(vertexes);
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);

    }

    @Override
    public void componentResized(ComponentEvent e) {
        x0 = getWidth() / 2;
        y0 = getHeight() / 2;
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

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }
}
