package ui;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Hero extends FlyObject {
    int hp=3;
    public Hero(){
        try {
            img= ImageIO.read(this.getClass().getResource("../image/hero_plane.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        x=150;
        y=500;
        w=img.getWidth()-100;
        h=img.getHeight()-100;

    }
    public void moveToMouse(int mx,int my){
        x=mx-75;
        y=my-105;
    }
    public void moveUp(){
        x=x;
        y=y-10;
    }
    public void moveDown(){
        x=x;
        y=y+10;
    }
    public void moveLeft(){
        x=x-10;
        y=y;
    }
    public void moveRight(){
        x=x+10;
        y=y;
    }
}
