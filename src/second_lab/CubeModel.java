package second_lab;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

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
            for (int i = 0; i < l.size() - 1; i++) {
                g.drawLine(l.get(i).getIntX(), l.get(i).getIntY(), l.get(i + 1).getIntX(), l.get(i + 1).getIntY());
            }
            g.drawLine(l.get(0).getIntX(), l.get(0).getIntY(), l.get(l.size() - 1).getIntX(), l.get(l.size() - 1).getIntY());

            g.drawLine(l.get(0).getIntX(), l.get(0).getIntY(), l.get(2).getIntX(), l.get(2).getIntY());
            g.drawLine(l.get(1).getIntX(), l.get(1).getIntY(), l.get(3).getIntX(), l.get(3).getIntY());
        };

        ModelFace front = new ModelFace(new ArrayList<>(){{add(one); add(two); add(three); add(four);}}, Color.MAGENTA, centerFunction, drawConsumer);
        ModelFace upper = new ModelFace(new ArrayList<>(){{add(two); add(six); add(seven); add(three);}}, Color.ORANGE, centerFunction, drawConsumer);
        ModelFace bottom = new ModelFace(new ArrayList<>(){{add(one); add(five); add(eight); add(four);}}, Color.BLUE, centerFunction, drawConsumer);
        ModelFace left = new ModelFace(new ArrayList<>(){{add(one);add(two);add(six);add(five);}}, Color.RED, centerFunction, drawConsumer);
        ModelFace right  = new ModelFace(new ArrayList<>(){{add(four);add(three);add(seven);add(eight);}}, Color.GREEN, centerFunction, drawConsumer);
        ModelFace back = new ModelFace(new ArrayList<>(){{add(five);add(six);add(seven);add(eight);}}, Color.GRAY, centerFunction, drawConsumer);

        front.setPair(back);
        upper.setPair(bottom);
        left.setPair(right);
        this.faces.addAll(new ArrayList<>(){{
            add(front);
            add(upper);
            add(bottom);
            add(left);
            add(right);
            add(back);
        }});
        this.printFaces(this.getFaces());
    }
}
