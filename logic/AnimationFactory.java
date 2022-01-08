package logic;

import Elements.*;
import java.util.ArrayList;
/**
 * Write a description of interface AnimationFactory here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public interface AnimationFactory {
    public final RandomWallFactory factory = new RandomWallFactory(TypeWall.RECTANGLE);
    
    public ArrayList<Wall> build();
}
