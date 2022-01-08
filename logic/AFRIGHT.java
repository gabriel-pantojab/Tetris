package logic;
import java.util.ArrayList;
import Elements.*;

/**
 * Write a description of class AFRIGHT here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class AFRIGHT implements AnimationFactory{
    public ArrayList<Wall> build() {
        Wall w1 = factory.createWall(-3, 2);
        Wall w2 = factory.createWall(-2, 7);
        Wall w3 = factory.createWall(-1, 12);
        
        ArrayList<Wall> w = new ArrayList<Wall>();
        w.add(w2);
        w.add(w3);
        w.add(w1);
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
