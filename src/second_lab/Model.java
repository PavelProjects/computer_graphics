package second_lab;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Model  implements MouseWheelListener, MouseMotionListener, MouseListener, ComponentListener {
    List<Vertex> vertexes;
    int vertexes_count;

    public Model(List<Vertex> vertexes){
        this.vertexes = vertexes;
        vertexes_count = vertexes.size();
    }

    public void draw(Graphics g){};

    public int getVertexes_count() {
        return vertexes_count;
    }

    public List<Vertex> getVertexes() {
        return vertexes;
    }

    public void setVertexes(List<Vertex> vertexes) {
        this.vertexes = vertexes;
    }

    @Override
    public void componentResized(ComponentEvent e) {

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
