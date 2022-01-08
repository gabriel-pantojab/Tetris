package logic;
import java.util.ArrayList;
import Elements.*;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Write a description of class Animation here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Animation {
    private ArrayList<Wall> walls;
    private Thread hilo;
    private AnimationFactory factory;
    private int limite;
    private JPanel lamina;
    
    public Animation(AnimationFactory factory, int limite, JPanel lamina) {
        walls = new ArrayList<Wall>();
        this.factory = factory;
        walls = factory.build();
        this.limite = limite;
        this.lamina = lamina;
    }
    
    public boolean end() {
        for(Block block : walls.get(0).getBlocks()) {
            if(block.getPositionInRow() < limite) return false;
        }
        return true;
    }
    
    public synchronized void run() {
        hilo = new Thread(){
            public void run() {
                while(!end()) {
                    try{
                        caer();
                        controlCaida();
                        lamina.repaint();
                        Thread.sleep(30);
                    }catch(Exception e){}
                }
            }
        };
        hilo.start();
    }
    
    private synchronized void caer() {
        for(int i = 0; i < walls.size(); i++) {
            if(i == 1) {
                walls.get(i).runBottom();
                walls.get(i).runBottom();
            }else if(i == 2) {
                walls.get(i).runBottom();
                walls.get(i).runBottom();
                walls.get(i).runBottom();
            }
            else walls.get(i).runBottom();
        }
    }
    
    private synchronized void controlCaida() {
        for(Wall w : walls) {
            for(Block b : w.getBlocks()) {
                if(b.getPositionInRow() >= 0) {
                    b.setVisible(true);
                }
                if(b.getPositionInRow() >= limite) {
                    b.setVisible(false);
                }
            }
        }
    }
    
    public synchronized void paint(Graphics g) {
        for(Wall w : walls) {
            w.paint(g);
        }
    }
}
