package Elements;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ProofPaintWall extends JFrame{
    public ProofPaintWall(){
        super("Animate Block");
        setBounds(0, 0, 1000, 600);
        setLocationRelativeTo(null);
        LaminaWall l = new LaminaWall();
        add(l);
        //l.start();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public static void main(String args[]){
        new ProofPaintWall();
    }
}

class LaminaWall extends JPanel{
    private Wall te, rect, ele, zeta, square, jota, ese, cross;
    public LaminaWall(){
        //te     = new ShapeT(3, 3, Color.GRAY);
        rect   = new ShapeRect(3, 10, Color.RED);
        rect.rotateLeft();
        /*ele    = new ShapeEle(3, 17, Color.ORANGE);
        zeta   = new ShapeZeta(3, 24, Color.BLUE);
        square = new ShapeSquare(3, 31, Color.GREEN);
        jota   = new ShapeJ(3, 38, Color.PINK);
        ese    = new ShapeS(3, 45, Color.YELLOW);
        cross  = new ShapeCross(3, 3, Color.GRAY);*/
        addKeyListener(new Manager());
        setFocusable(true);
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //cross.paint(g);
        //te.paint(g);
        g.setColor(Color.RED);
        g.drawLine(0, 100, 1000, 100);
        rect.paint(g);
        /*ele.paint(g);
        zeta.paint(g);
        jota.paint(g);
        ese.paint(g);
        square.paint(g);*/
    }
    public void start(){
        Thread h = new Thread(){
            public void run(){
                while(true){
                    try{
                        /*te.rotateLeft();
                        
                        rect.rotateLeft();
                        
                        ele.rotateLeft();
                        
                        zeta.rotateLeft();
                        
                        jota.rotateLeft();
                        
                        ese.rotateLeft();*/
                        
                        repaint();
                        sleep(500);
                    }catch(Exception e){}
                }
            }
        };
        h.start();
    }
    
    private class Manager implements KeyListener{
        public void keyPressed(KeyEvent e){
            if(e.getKeyCode() == KeyEvent.VK_UP)
                zeta.runTop();
            else if(e.getKeyCode() == KeyEvent.VK_DOWN)
                zeta.runBottom();
            else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
                zeta.runRight();
            else if(e.getKeyCode() == KeyEvent.VK_LEFT)
                zeta.runLeft();
            else if(e.getKeyCode() == KeyEvent.VK_A)
                zeta.rotateLeft();
            else if(e.getKeyCode() == KeyEvent.VK_S)
                zeta.rotateRight();
            if(e.getKeyCode() == KeyEvent.VK_D) rect.clearBlocksInRow(3);
            repaint();
        }
        public void keyReleased(KeyEvent e){}
        public void keyTyped(KeyEvent e){}
    }
}
