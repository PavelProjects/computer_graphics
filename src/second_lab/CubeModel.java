package second_lab;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CubeModel extends Model{
    int height;

    public CubeModel(int h) {
        this.height = h;
        this.center = new Vertex(height / 2, height / 2, 0);

        Vertex one = new Vertex(-height, -height, -height);
        Vertex two = new Vertex(-height, -height, height);
        Vertex three = new Vertex(height, -height, height);
        Vertex four = new Vertex(height, -height, -height);
        Vertex five = new Vertex(-height, height, -height);
        Vertex six = new Vertex(-height, height, height);
        Vertex seven = new Vertex(height, height, height);
        Vertex eight = new Vertex(height, height, -height);

        CenterConcumer<List<Vertex>, Vertex> centerFunction = (l, v) -> {
            v.setZ(l.get(0).getZ() + (l.get(2).getZ() - l.get(0).getZ()) / 2);
        };
        DrawConsumer<Graphics, List<Vertex>> drawConsumer = (g, l) ->{
           g.fillPolygon(l.stream().mapToInt(Vertex::getIntX).toArray(), l.stream().mapToInt(Vertex::getIntY).toArray(), l.size());
           g.setColor(Color.GRAY);
            for (int i = 0; i < l.size() - 1; i++) {
                g.drawLine(l.get(i).getIntX(), l.get(i).getIntY(), l.get(i + 1).getIntX(), l.get(i + 1).getIntY());
            }
            g.drawLine(l.get(0).getIntX(), l.get(0).getIntY(), l.get(l.size() - 1).getIntX(), l.get(l.size() - 1).getIntY());
        };

        Color faceColor = Color.WHITE;

        ModelFace front = new ModelFace("front", new ArrayList<>(){{add(one); add(two); add(three); add(four);}}, faceColor, centerFunction, drawConsumer, 0, 90);
        ModelFace bottom = new ModelFace("bottom", new ArrayList<>(){{add(two); add(six); add(seven); add(three);}}, faceColor, centerFunction, drawConsumer, 0, 180);
        ModelFace back = new ModelFace("back", new ArrayList<>(){{add(five);add(six);add(seven);add(eight);}}, faceColor, centerFunction, drawConsumer, 0, 270);
        ModelFace up = new ModelFace("up", new ArrayList<>(){{add(one); add(five); add(eight); add(four);}}, faceColor, centerFunction, drawConsumer, 0, 0);
        ModelFace left = new ModelFace("left", new ArrayList<>(){{add(one);add(two);add(six);add(five);}}, faceColor, centerFunction, drawConsumer, 270, 0);
        ModelFace right  = new ModelFace("right", new ArrayList<>(){{add(four);add(three);add(seven);add(eight);}}, faceColor, centerFunction, drawConsumer, 90, 0);

        front.setPair(back);
        bottom.setPair(up);
        left.setPair(right);
        this.faces.addAll(new ArrayList<>(){{
            add(front);
            add(bottom);
            add(back);
            add(up);
            add(left);
            add(right);
        }});
        this.printFaces(this.getFaces());
    }
}
