package second_lab;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Model {
    protected List<ModelFace> faces;
    protected Vertex center;
    private Matrix m;

    public Model() {
        this.faces = new ArrayList<>();
    }

    public void draw(Graphics g, double angleX, double angleY, double scale) {
        m = Matrix.getIdentityMatrix();
        m = m.multiplyMatrixToMatrix(Matrix.rotateMatrixX(angleX));
        m = m.multiplyMatrixToMatrix(Matrix.rotateMatrixY(angleY));
        m = m.multiplyMatrixToMatrix(Matrix.getTranslateMatrix(0, 0, 2.0f));
        m = m.multiplyMatrixToMatrix(Matrix.getScalingMatrix(scale, -scale, 1));
//        m = m.multiplyMatrixToMatrix(Matrix.getProjectionMatrix(2, 0.1));

        faces.forEach(f -> f.draw(g, m));
    }

    public Vertex getCenter() {
        return center;
    }

    public void printFaces(List<ModelFace> faces) {
        for (int i = 0; i < faces.size(); i++) {
            System.out.println(String.format("Face #%d", i));
            for (Vertex v : faces.get(i).getVertexes()) {
                v.printVert();
            }
            System.out.println("-------------------------\n");
        }
    }

    public List<ModelFace> getFaces() {
        return faces;
    }

    public int getMaxX(){
        return  faces.stream().map(ModelFace::getMaxX).max(Integer::compare).get();
    }

    public int getMaxY(){
        return  faces.stream().map(ModelFace::getMaxY).max(Integer::compare).get();
    }
}

class ModelFace {
    private List<Vertex> vertexes, vertexRendered;
    private final Vertex center;
    private final CenterConcumer<List<Vertex>, Vertex> centerConsumer;
    private final DrawConsumer<Graphics, List<Vertex>> drawConsumer;
    private boolean visible = true;
    private Color color;
    private ModelFace pair;
    private String name;
    private final int visibleAngleX;
    private final int visibleAngleY;

    public ModelFace(String name, List<Vertex> vertices, Color color, CenterConcumer<List<Vertex>, Vertex> centerFunction, DrawConsumer<Graphics, List<Vertex>> drawConsumer, int vX, int vY) {
        this.vertexes = this.vertexRendered = vertices;
        this.color = color;
        this.centerConsumer = centerFunction;
        this.drawConsumer = drawConsumer;
        this.center = new Vertex(0, 0, 0);
        this.name = name;
        this.visibleAngleX = vX;
        this.visibleAngleY = vY;
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

    public void setPair(ModelFace pair) {
        this.pair = pair;
    }

    public ModelFace getPair() {
        return pair;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getVisibleAngleX() {
        return visibleAngleX;
    }

    public int getVisibleAngleY() {
        return visibleAngleY;
    }

    public int getMaxX() {
        return vertexRendered.stream().map(Vertex::getIntX).max(Integer::compare).get();
    }

    public int getMaxY() {
        return  vertexRendered.stream().map(Vertex::getIntY).max(Integer::compare).get();
    }

    public void transform(Matrix m) {
        vertexRendered = vertexes.stream().map(v -> m.multiplyMatrixToVector(v.getX(), v.getY(), v.getZ())).collect(Collectors.toList());
        if (pair != null) {
            pair.updateCenter();
            double vz = Math.signum(pair.getCenter().getZ() - this.getCenter().getZ());
            if (vz > 0) {
                pair.setVisible(false);
                this.setVisible(true);
            } else {
                pair.setVisible(true);
                this.setVisible(false);
            }
        }
    }

    public void draw(Graphics g, Matrix m) {
        updateCenter();
        transform(m);
        if (isVisible()) {
            g.setColor(color);
            drawConsumer.draw(g, vertexRendered);
        }
    }

    public Vertex getCenter() {
        return center;
    }

    public void updateCenter() {
        centerConsumer.calculate(vertexRendered, center);
    }
}

class Vertex {
    double x, y, z;

    public Vertex(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
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

@FunctionalInterface
interface CenterConcumer<T, U>{
    void calculate(T l, U v);
}
@FunctionalInterface
interface DrawConsumer<T, U>{
    void draw(T a, U b);
}