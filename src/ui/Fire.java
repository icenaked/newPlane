package ui;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Fire extends FlyObject{
    public Fire(int hx,int hy){
        try {
            img= ImageIO.read(this.getClass().getResource("../image/fire.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        w=img.getWidth();
        h=img.getHeight();
        x=hx+60;
        y=hy;
    }

    public void move(){
        y-=10;
    }
}
