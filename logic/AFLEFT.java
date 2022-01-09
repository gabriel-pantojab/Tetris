package logic;
import java.util.ArrayList;
import Elements.*;

/**
 * Write a description of class AFLEFT here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class AFLEFT implements AnimationFactory{
    public ArrayList<Wall> build() {
        Wall w1 = factory.createWall(-1, 1);
        Wall w2 = factory.createWall(-2, 6);
        Wall w3 = factory.createWall(-3, 11);
        Wall w4 = factory.createWall(-4, 3);
        Wall w5 = factory.createWall(-5, 9);
        Wall w6 = factory.createWall(-6, 14);
        
        ArrayList<Wall> w = new ArrayList<Wall>();
        w.add(w6);
        w.add(w1);
        w.add(w2);
        w.add(w3);
        w.add(w4);
        w.add(w5);
        
        ocultar(w);
        
        return w;
    }
    
    private void ocultar(ArrayList<Wall> list){
        for(Wall w : list){
            for(Block b : w.getBlocks()){
                b.setVisible(false);
            }
        }
    }
}
