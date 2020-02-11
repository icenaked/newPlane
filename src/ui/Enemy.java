package ui;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

public class Enemy extends FlyObject {
      int hp=3;
      int type;
    public Enemy(){
        Random rd =new Random();
        int index=rd.nextInt(8)+1;
        type=index;
        try {
            img= ImageIO.read(this.getClass().getResource("../image/enemy"+index+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        w=img.getWidth();
        h=img.getHeight();
        x=rd.nextInt(520-w);// range from [0,num)
        y=-h;

    }
    public void move(){
        Random rd=new Random();
        y+=rd.nextInt(10)+1;  //enemy plane has different speed in every second......
    }
    public boolean shootBy(Fire f){
        boolean hit=x<=f.x+f.w&&
                x>=f.x-w &&
                y<=f.y+f.h&&
                y>=f.y-h;
        return hit;
    }
    public boolean hitBy(Hero f){
        boolean hit=x<=f.x+f.w&&
                x>=f.x-w &&
                y<=f.y+f.h&&
                y>=f.y-h;
        return hit;
    }
}
