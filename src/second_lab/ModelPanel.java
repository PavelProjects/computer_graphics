package second_lab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ModelPanel extends JPanel implements MouseWheelListener, MouseMotionListener, MouseListener, ComponentListener, KeyListener {
    Model model;
    double angleY = 50, angleX = 30;
    int prevX = 0, prevY = 0;
    int x0, y0;
    int gap = 50;
    double scale = 1;
    private AtomicInteger threadRunFlag = new AtomicInteger(0);
    private Runnable updateViewRunnable;

    Thread updateViewThread = new Thread();
    Thread spinModelThread = new Thread();
    Thread changeAngleThread = new Thread();

    public ModelPanel(Model model) {
        this.model = model;
        updateViewRunnable = new Runnable() {
            @Override
            public void run() {
                while (threadRunFlag.get() != 0) {
                    repaint();
                    try {
                        TimeUnit.MILLISECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        threadRunFlag.set(0);
                    }
                }
                System.out.println("update stoped");
            }
        };
    }

    public void rescale() {
        int gapX = x0 - model.getMaxX();
        int gapY = y0 - model.getMaxY();

        if (gap < Math.min(gapX, gapY)) {
            scale += 0.025;
        } else {
            scale -= 0.025;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.translate(x0, y0);
        g.setColor(Color.BLACK);

        model.draw(g, angleY, angleX, scale);
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        prevY = e.getY();
        prevX = e.getX();
        threadRunFlag.set(0);
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
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() > 0) {
            scale = scale > 0 ? scale - 0.1 : scale;
        } else {
            scale = scale < 1000 ? scale + 0.1 : scale;
        }
        int gapX = x0 - model.getMaxX();
        int gapY = y0 - model.getMaxY();
        gap = Math.min(gapX, gapY);

        repaint();
    }

    @Override
    public void componentResized(ComponentEvent e) {
        x0 = getWidth() / 2;
        y0 = getHeight() / 2;
        rescale();
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

    public void showFaceByIndex(int index) {
        if (index >= 0 && index < model.getFaces().size()) {
            showFace(model.getFaces().get(index));
        } else {
            showFace(null);
        }
    }


    public void showFace(ModelFace face) {
        threadRunFlag.set(0);

        try {
            if(spinModelThread.isAlive()) {
                spinModelThread.join();
            }
            if(changeAngleThread.isAlive()){
                changeAngleThread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final int aX, aY;
        if (face != null) {
            aX = face.getVisibleAngleX();
            aY = face.getVisibleAngleY();
        } else {
            aX = 45;
            aY = 45;
        }

        double signX = aX < angleX ? -0.5 : 0.5;
        double signY = aY < angleY ? -0.5 : 0.5;

        changeAngleThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while ((angleX != aX || angleY != aY) && threadRunFlag.get() == 1) {
                    try {
                        angleX = (int) angleX != aX ? angleX + signX : (int) angleX;
                        angleY = (int) angleY != aY ? angleY + signY : (int) angleY;
                        TimeUnit.MILLISECONDS.sleep(5);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                        threadRunFlag.set(0);
                    }
                }
                threadRunFlag.set(0);
                System.out.println("change angle stoped");
            }
        });
        updateViewThread = new Thread(updateViewRunnable);

        threadRunFlag.set(1);
        updateViewThread.start();
        changeAngleThread.start();
    }

    public void spinModel() {
        threadRunFlag.set(0);

        try {
            if(spinModelThread.isAlive()) {
                spinModelThread.join();
            }
            if(changeAngleThread.isAlive()){
                changeAngleThread.join();
            }
            if(updateViewThread.isAlive()){
                updateViewThread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        updateViewThread = new Thread(updateViewRunnable);
        spinModelThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (threadRunFlag.get() != 0) {
                    angleX = angleX < 360 ? angleX + 0.2 : 0;
                    angleY = angleY > 0 ? angleY - 0.2 : 360;
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        threadRunFlag.set(0);
                    }
                }
                System.out.println("spin stoped");
            }
        });

        threadRunFlag.set(1);
        spinModelThread.start();
        updateViewThread.start();
    }
}