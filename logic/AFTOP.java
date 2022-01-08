package logic;
import java.util.ArrayList;
import Elements.*;

/**
 * Write a description of class AFTop here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class AFTOP implements AnimationFactory {
    public ArrayList<Wall> build() {
        Wall w1 = factory.createWall(-3, 2);
        Wall w2 = factory.createWall(-1, 7);
        Wall w3 = factory.createWall(-3, 12);
        
        ArrayList<Wall> w = new ArrayList<Wall>();
        w.add(w1);
        w.add(w2);
        w.add(w3);
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
