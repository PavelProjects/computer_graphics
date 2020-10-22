package second_lab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;

public class ModelPanel extends JPanel implements MouseWheelListener, MouseMotionListener, MouseListener, ComponentListener, KeyListener {
    Model model;
    double angleY = 50, angleX = 30;
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
        model.draw(g, angleY, angleX, scale);
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
        angleX += prevX - e.getX();
        angleY += prevY - e.getY();

        if (angleX > 360) {
            angleX = 0;
        } else if (angleX < 0) {
            angleX = 360;
        }
        if (angleY > 360) {
            angleY = 0;
        } else if (angleY < 0) {
            angleY = 360;
        }
        prevX = e.getX();
        prevY = e.getY();

        paintComponent(getGraphics());
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() > 0) {
            scale = scale - 0.1;
        } else {
            scale = scale < 1000 ? scale + 0.1 : scale;
        }
        paintComponent(getGraphics());
    }

    @Override
    public void componentResized(ComponentEvent e) {
        x0 = getWidth() / 2;
        y0 = getHeight() / 2;
        System.out.println(model.getMaxX() + "::" + model.getMaxY() + "::" + scale); //найти функцию для подгона под окна и будет все
        System.out.println("---------------------------------------");
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
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int index;
        try {
            index = Integer.parseInt(String.valueOf(e.getKeyChar()));
        } catch (Exception e1) {
            index = 0;
        }

        index--;
        showFaceByIndex(index);
    }

    public void showFaceByIndex(int index){
        if (index >= 0 && index < model.getFaces().size()) {
            showFace(model.getFaces().get(index));
        }else{
            showFace(null);
        }
    }


    public void showFace(ModelFace face) {
        final int aX, aY;
        if (face != null) {
            aX = face.getVisibleAngleX();
            aY = face.getVisibleAngleY();
        } else {
            aX = 45;
            aY = 45;
        }

        int signX = aX < angleX ? -1 : 1;
        int signY = aY < angleY ? -1 : 1;
        Runnable animation = new Runnable() {
            @Override
            public void run() {
                while (angleX != aX || angleY != aY) {
                    angleX = angleX != aX ? angleX + signX : angleX;
                    angleY = angleY != aY ? angleY + signY : angleY;
                    try {
                        TimeUnit.MILLISECONDS.sleep(5);
                        paintComponent(getGraphics());
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
            }
        };
        animation.run();
    }
}


