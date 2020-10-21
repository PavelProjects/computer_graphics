package second_lab;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Model {
    protected List<ModelFace> facesOriginal, facesRender;
    protected Vertex center;

    public Model() {
        this.facesOriginal = new ArrayList<>();
    }

    public void draw(Graphics g, double angleX, double angleY, double scale) {
        Matrix m = Matrix.getIdentityMatrix();
        m = m.multiplyMatrixToMatrix(Matrix.rotateMatrixX(angleX));
        m = m.multiplyMatrixToMatrix(Matrix.rotateMatrixY(angleY));
        m = m.multiplyMatrixToMatrix(Matrix.getTranslateMatrix(0, 0, 2.0f));
        m = m.multiplyMatrixToMatrix(Matrix.getScalingMatrix(scale, -scale, 1));

        facesRender = transform(m);
        System.out.println("----------------------------");
//        printFaces(facesRender);
        facesRender.forEach(f -> f.draw(g));
    }

    public Vertex getCenter(){
        return center;
    }

    private List<ModelFace> transform( Matrix m) {
        return facesOriginal.stream().map(f -> f.transform(m)).collect(Collectors.toList());
    }

    public void printFaces(List<ModelFace> faces) {
        for (int i = 0; i <  faces.size(); i++) {
            System.out.println(String.format("Face #%d", i));
            for (Vertex v :  faces.get(i).getVertexes()) {
                v.printVert();
            }
            System.out.println("-------------------------\n");
        }
    }

    public List<ModelFace> getFacesOriginal() {
        return facesOriginal;
    }

    public void setFacesOriginal(List<ModelFace> facesOriginal) {
        this.facesOriginal = facesOriginal;
    }
}

class ModelFace {
    private List<Vertex> vertexes;
    private boolean visible = true;
    private Color color;

    public ModelFace(List<Vertex> vertices, Color color) {
        this.vertexes = vertices;
        this.color = color;
    }

    public List<Vertex> getVertexes() {
        return vertexes;
    }

    public void setVertexes(List<Vertex> vertexes) {
        this.vertexes = vertexes;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public ModelFace transform(Matrix m) {
        ModelFace res = new ModelFace(vertexes.stream().map(v -> m.multiplyMatrixToVector(v.getX(), v.getY(), v.getZ())).collect(Collectors.toList()), this.color);
        double ax = res.getVertexes().get(2).getX() - res.getVertexes().get(0).getX();
        double ay = res.getVertexes().get(2).getY() - res.getVertexes().get(0).getY();
        double bx = res.getVertexes().get(3).getX() - res.getVertexes().get(1).getX();
        double by = res.getVertexes().get(3).getY() - res.getVertexes().get(1).getY();
        System.out.println(ax * by - ay * bx);
        res.setVisible(ax * by - ay * bx > 0); //сделать так, что бы только задняя стенка не отрисовывалась

        return res;
    }

    public void draw(Graphics g) {
        if (visible) {
            g.setColor(color);
            for (int i = 0; i < vertexes.size() - 1; i++) {
                g.drawLine(vertexes.get(i).getIntX(), vertexes.get(i).getIntY(), vertexes.get(i + 1).getIntX(), vertexes.get(i + 1).getIntY());
            }
            g.drawLine(vertexes.get(0).getIntX(), vertexes.get(0).getIntY(), vertexes.get(vertexes.size() - 1).getIntX(), vertexes.get(vertexes.size() - 1).getIntY());

            g.drawLine(vertexes.get(0).getIntX(), vertexes.get(0).getIntY(), vertexes.get(2).getIntX(), vertexes.get(2).getIntY());
            g.drawLine(vertexes.get(1).getIntX(), vertexes.get(1).getIntY(), vertexes.get(3).getIntX(), vertexes.get(3).getIntY());
        }
    }
}

class Vertex {
    double x, y, z;

    public Vertex(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public int getIntX() {
        return (int) x;
    }

    public int getIntY() {
        return (int) y;
    }

    public int getIntZ() {
        return (int) z;
    }

    public void printVert() {
        System.out.println(String.format("|x=%-10f y=%-10f z=%-10f|", x, y, z));
    }
}
