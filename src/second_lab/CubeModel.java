package second_lab;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.List;

public class CubeModel extends Model{
    int height;

    public CubeModel(List<Vertex> vertexes, int h) {
        super(vertexes);
        this.height = h;
        Vertex v = vertexes.get(0);
        vertexes.add(new Vertex(v.getX() + height, v.getY(), v.getZ()));
        vertexes.add(new Vertex(v.getX() + height, v.getY() + height, v.getZ()));
        vertexes.add(new Vertex(v.getX(), v.getY() + height, v.getZ()));
        vertexes.forEach(vert -> vert.printVert());
    }

    @Override
    public void draw(Graphics g) {
//        super.draw(g);
//        vertexes.forEach((v)->{transform.transform(v);});
//        Path2D path = new Path2D.Float();
//        path.moveTo(transform.transform(vertexes.get(0)).getX(), );
    }
}
