package second_lab;

import java.awt.*;
import java.util.ArrayList;

public class CubeModel extends Model{
    int height;

    public CubeModel(int h) {
        this.height = h;
        this.center = new Vertex(height / 2, height / 2, 0);
        Vertex one = new Vertex(0, 0, 0);
        Vertex two = new Vertex(0, 0, height);
        Vertex three = new Vertex(height, 0, height);
        Vertex four = new Vertex(height, 0, 0);
        Vertex five = new Vertex(0, height, 0);
        Vertex six = new Vertex(0, height, height);
        Vertex seven = new Vertex(height, height, height);
        Vertex eight = new Vertex(height, height, 0);

        ModelFace front = new ModelFace(new ArrayList<>(){{add(one); add(two); add(three); add(four);}}, Color.MAGENTA);
        ModelFace upper = new ModelFace(new ArrayList<>(){{add(two); add(six); add(seven); add(three);}}, Color.ORANGE);
        ModelFace bottom = new ModelFace(new ArrayList<>(){{add(one); add(five); add(eight); add(four);}}, Color.BLUE);
        ModelFace left = new ModelFace(new ArrayList<>(){{add(one);add(two);add(six);add(five);}}, Color.RED);
        ModelFace right  = new ModelFace(new ArrayList<>(){{add(four);add(three);add(seven);add(eight);}}, Color.GREEN);
        ModelFace back = new ModelFace(new ArrayList<>(){{add(five);add(six);add(seven);add(eight);}}, Color.GRAY);

        this.facesOriginal.addAll(new ArrayList<>(){{
            add(front);
            add(upper);
            add(bottom);
            add(left);
            add(right);
            add(back);
        }});
        this.printFaces(this.getFacesOriginal());
    }
}
