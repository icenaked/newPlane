package ui;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    BufferedImage bg;
    Hero hero=new Hero();
     //Enemy enemy=new Enemy();        original code......
    List<Enemy> enemies=new ArrayList<Enemy>();
    List<Fire> fs=new ArrayList<Fire>();
    int score=0;
    boolean gameover=false;
    int power=1;

    public void action(){
        //fixed format
        //new Thread(){public void run(){线程需要做的是。。。}}.start();
        new Thread(){
            public void run(){
                while (true){
               if(!gameover){
                enemyEnter();
                enemyMove();
                shoot();
                fireMove();
                shootEnemy();
                hit();
                    }
                try{
                    Thread.sleep(10);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                repaint();//end-less loop，让游戏一直进行下去
                }
            }
        }.start();
    }

      protected void enemyMove(){
        for(int i=0;i<enemies.size();i++){
            Enemy e=enemies.get(i);
            e.move();
      }
    }

           int index=0;
       protected void enemyEnter(){
           index++;
           if(index>=50) {
               Enemy e = new Enemy();
               enemies.add(e);
               index=0;
           }
       }
        int findex=0;
       protected void shoot(){
           findex++;
           if(findex>=30) {
               if(power==1){
                   Fire fire1 = new Fire(hero.x, hero.y);
                   fs.add(fire1);
               }
               if(power==2){
                   Fire fire2 = new Fire(hero.x-50, hero.y+50);
                   fs.add(fire2);
                   Fire fire3 = new Fire(hero.x+50, hero.y+50);
                   fs.add(fire3);
               }
               if(power==3){
                   Fire fire1 = new Fire(hero.x, hero.y);
                   fs.add(fire1);
                   Fire fire2 = new Fire(hero.x-50, hero.y+50);
                   fs.add(fire2);
                   Fire fire3 = new Fire(hero.x+50, hero.y+50);
                   fs.add(fire3);
               }

               findex=0;
           }
       }
       protected void fireMove(){
           for(int i=0;i<fs.size();i++){
               Fire f =fs.get(i);
               f.move();

           }
       }
       protected void shootEnemy(){
           for(int i=0;i<fs.size();i++){
               Fire f =fs.get(i);
               bang(f);
           }
       }

       private void bang(Fire f){
           for(int i=0;i<enemies.size();i++){
               Enemy e=enemies.get(i);
               if(e.shootBy(f)){
                   e.hp--;
                   if(e.hp<=0){
                       if(e.type==6){
                           power++;
                           if(power>3){
                               hero.hp++;
                               power=3;
                           }
                       }
                   enemies.remove(e);
                   score+=1;
                   }
                   fs.remove(f);
               }
           }
       }

       protected void hit(){
           for(int i=0;i<enemies.size();i++){
               Enemy e=enemies.get(i);
               if(e.hitBy(hero)) {

               enemies.remove(e);
                   if(e.type==6){
                       power++;
                       if(power>3){
                           hero.hp++;
                           power=3;
                       }
                   }

               hero.hp--;
               score+=5;
               if(hero.hp<=0){
                   gameover=true;
               }
                  }
               }
           }



    public GamePanel(GameFrame frame){
        setBackground(Color.yellow);
        try {
            bg= ImageIO.read(this.getClass().getResource("../image/background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //mouse monitor!!!!!!!!!!!!!!!!!!!!!!!!!!!
        MouseAdapter adapter=new MouseAdapter() {

            public void mouseClicked(MouseEvent e){
                if(gameover){
                    hero=new Hero();
                    gameover=false;
                    score=0;
                    enemies.clear();
                    fs.clear();
                    power=1;//火力设置没有放在Hero类里，大失误，故在此处补救！！！
                }
            }

            public void mouseMoved(MouseEvent e) {
               int mx=e.getX();
               int my=e.getY();
          if (!gameover)
               hero.moveToMouse(mx,my);
               //repaint the hero plane !!!
                repaint();
            }
        };
               addMouseListener (adapter);
                addMouseMotionListener(adapter);


                //keyboard monitor, 与鼠标监听器二者取其一即可！！！
        KeyAdapter kd=new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                //every key has a code
                int keyCode=e.getKeyCode();
                if(keyCode==KeyEvent.VK_UP){     //在这里也可以用println来看对应的code，例如：KeyEvent.VK_UP=38
                    hero.moveUp();
                    repaint();
                }
                if(keyCode==KeyEvent.VK_DOWN){
                    hero.moveDown();
                    repaint();
                }
                if(keyCode==KeyEvent.VK_LEFT){
                    hero.moveLeft();
                    repaint();
                }
                if(keyCode==KeyEvent.VK_RIGHT){
                    hero.moveRight();
                    repaint();
                }
            }
        };
        frame.addKeyListener(kd);//此处要将键盘适配器加入窗体中，而不是面板中。

    }





    public void paint(Graphics g){
        super.paint(g);
        g.drawImage(bg,0,0,520,768,null);
        g.drawImage(hero.img,hero.x,hero.y,hero.w,hero.h,null);

                for(int i=0;i<enemies.size();i++) {
                      Enemy enemy=enemies.get(i);
                    g.drawImage(enemy.img, enemy.x, enemy.y, enemy.w, enemy.h, null);
                }
                for (int i=0;i<fs.size();i++){
                    Fire fire=fs.get(i);
                    g.drawImage(fire.img,fire.x,fire.y,fire.w,fire.h,null);
                }
        g.setColor(Color.white);
        g.setFont(new Font("楷体",Font.BOLD,30));
        g.drawString("score: "+score,10,30);
             for(int i=0;i<hero.hp;i++){
                 g.drawImage(hero.img,350+i*35,5,30,30,null);
             }
             if(gameover) {
                 g.setColor(Color.white);
                 g.setFont(new Font("楷体", Font.BOLD, 30));
                 g.drawString("崩盘", 200, 250);
                 g.drawString("RNG周身环绕着虚空之力，小心点！", 30, 350);
                 g.drawString("作者：张悦祺", 150, 450);
                 g.setColor(Color.red);
                 g.drawString("点击屏幕任意位置重开",80,100);
             }
    }


}
