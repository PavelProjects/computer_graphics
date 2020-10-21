package first_lab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GraphPanel extends JPanel implements MouseWheelListener, MouseMotionListener, MouseListener, ComponentListener {
    float a, b;
    String s_a, s_b;
    int x0, y0, oldW, oldH, gap;
    float[] x_values = new float[361], y_values = new float[361];

    int mousePosX, mousePosY;
    int prevX = -1, prevY = -1;
    int scale = 10;
    boolean aim = true;

    private static final int dash_size = 3;

    public GraphPanel(float a, float b) {
        this.a = a;
        this.b = b;
        s_a = String.valueOf(a);
        s_b = String.valueOf(b);
        for (int i = 0; i <= 360; i++) {
            x_values[i] = (float) (a * Math.sin(Math.toRadians(i)));
            y_values[i] = (float) (b * Math.cos(Math.toRadians(i)));
            System.out.println(String.format("|degree::%-5d | x=%-10f | y=%-10f|", i, x_values[i], y_values[i]));
        }
        System.out.println("--------------------------------------------");
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);

        g.drawLine(0, y0, getWidth(), y0);
        g.drawLine(getWidth() - dash_size, y0 - dash_size, getWidth(), y0);
        g.drawLine(getWidth() - dash_size, y0 + dash_size, getWidth(), y0);

        g.drawLine(x0, 0, x0, getHeight());
        g.drawLine(x0 - dash_size, dash_size, x0, 0);
        g.drawLine(x0 + dash_size, dash_size, x0, 0);

        for (int i = 0; i <= getWidth() / 2; i++) {
            if (scale > 5) {
                g.drawLine(x0 + i * scale, y0 + dash_size, x0 + i * scale, y0 - dash_size);
                g.drawLine(x0 - i * scale, y0 + dash_size, x0 - i * scale, y0 - dash_size);
                if (scale > 10) {
                    g.drawLine(x0 + i * scale / 2, y0 + dash_size / 2, x0 + i * scale / 2, y0 - dash_size / 2);
                    g.drawLine(x0 - i * scale / 2, y0 + dash_size / 2, x0 - i * scale / 2, y0 - dash_size / 2);
                }
            } else {
                g.drawLine(x0 + 10 * i * scale, y0 + dash_size, x0 + 10 * i * scale, y0 - dash_size);
                g.drawLine(x0 - 10 * i * scale, y0 + dash_size, x0 - 10 * i * scale, y0 - dash_size);
            }
        }
        for (int i = 0; i <= getHeight() / 2; i++) {
            if (scale > 5) {
                g.drawLine(x0 + dash_size, y0 + i * scale, x0 - dash_size, y0 + i * scale);
                g.drawLine(x0 + dash_size, y0 - i * scale, x0 - dash_size, y0 - i * scale);
                if (scale > 10) {
                    g.drawLine(x0 + dash_size / 2, y0 + i * scale / 2, x0 - dash_size / 2, y0 + i * scale / 2);
                    g.drawLine(x0 + dash_size / 2, y0 - i * scale / 2, x0 - dash_size / 2, y0 - i * scale / 2);
                }
            } else {
                g.drawLine(x0 + dash_size, y0 + 10 * i * scale, x0 - dash_size, y0 + 10 * i * scale);
                g.drawLine(x0 + dash_size, y0 - 10 * i * scale, x0 - dash_size, y0 - 10 * i * scale);
            }
        }

        g.drawString("0", x0, y0 + 15);

        if (scale > 5)
            g.drawString("1", x0 + scale, y0 + 15);
        else
            g.drawString("10", x0 + 10 * scale, y0 + 15);

        g.drawString(s_a, (int) (x0 + scale * a), y0 - 10);
        g.drawString(s_b, x0 + 10, (int) (y0 - scale * b));

        g.drawString("x", getWidth() - 15, y0 + 15);
        g.drawString("y", x0 + 10, 10);

        g.setColor(Color.GREEN);
        int x1 = x0 + (int) (scale * x_values[0]), y1 = y0 - (int) (scale * y_values[0]);
        int x2, y2;
        for (int i = 0; i <= 360; i++) {
            x2 = (int) (x0 + scale * x_values[i]);
            y2 = (int) (y0 - scale * y_values[i]);
            g.drawLine(x1, y1, x2, y2);
            x1 = x2;
            y1 = y2;
        }

        g.setColor(Color.BLACK);
        g.drawString("scale x" + scale, 10, 20);

        if (aim) {
            g.setColor(Color.GRAY);
            g.drawLine(mousePosX, 0, mousePosX, getHeight());
            g.drawLine(0, mousePosY, getWidth(), mousePosY);
            g.setColor(Color.RED);
            int y = scale != 0 ? -1 * (mousePosY - y0) * 10 / scale : 0;
            int x = scale != 0 ? (mousePosX - x0) * 10 / scale : 0;
            g.drawString(String.format("%s.%s", y / 10, y % 10), x0 + 5, mousePosY - 5);
            g.drawString(String.format("%s.%s", x / 10, x % 10), mousePosX + 5, y0 - 5);
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() > 0) {
            scale = scale > 1 ? scale - e.getWheelRotation() : scale;
        } else {
            scale = scale < 1000 ? scale - e.getWheelRotation() : scale;
        }
        int gapX = (int) (x0 - a * scale);
        int gapY = (int) (y0 - b * scale);
        gap = Math.min(gapX, gapY);
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        x0 += e.getX() - prevX;
        y0 += e.getY() - prevY;

        repaint();

        prevX = e.getX();
        prevY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePosX = e.getX();
        mousePosY = e.getY() - 30;
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        prevX = e.getX();
        prevY = e.getY();
        aim = false;
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        aim = true;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void componentResized(ComponentEvent e) {
        x0 = getWidth() / 2;
        y0 = getHeight() / 2;
        if (x0 - a * scale < y0 - b * scale) {
            scale = (int) ((x0 - gap) / a);
        } else {
            scale = (int) ((y0 - gap) / b);
        }
        oldW = getWidth();
        oldH = getHeight();
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
