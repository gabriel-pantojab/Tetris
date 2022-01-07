package logic;


/**
 * Write a description of class StaticWallFactory here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import Elements.*;
import java.awt.Color;

public class StaticWallFactory implements WallFactory {
    private Wall[] walls;
    
    public StaticWallFactory() {
        walls = new Wall[4];
        walls[0] = new ShapeRect(0, 2, Color.GRAY);
        walls[1] = new ShapeRect(0, 6, Color.GRAY);
        walls[2] = new ShapeRect(0, 10, Color.GRAY);
        walls[3] = new ShapeRect(0, 14, Color.GRAY);
    }
    
    public Wall createWall() {
        return new ShapeRect(0, 0, Color.GRAY);
    }
    
    public Wall[] walls() {
        Wall[] w = new Wall[4];
        for(int i = 0; i < 4; i++) {
            w[i] = walls[i].clone();
        }
        return w;
    }
}
