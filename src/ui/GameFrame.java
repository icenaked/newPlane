package ui;
import javax.swing.JFrame;
public class GameFrame extends JFrame{
    public GameFrame(){
        setTitle("南京大学教务处");//title
        setSize(520,768);//size
        setLocationRelativeTo(null);// put the window in the centre
        setResizable(false);// not allowed to change the size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//CLOSE the process when you close the window
    }

    public static void main(String[] args){
        GameFrame frame=new GameFrame();
        GamePanel panel=new GamePanel(frame);
        panel.action();
        frame.add(panel);//put panel and frame together!!!
        frame.setVisible(true);
    }


}
