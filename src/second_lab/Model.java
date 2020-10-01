package second_lab;

import java.awt.*;
import java.util.List;

public class Model {
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

}

class Vertex {
    double x, y, z;

    public Vertex(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
