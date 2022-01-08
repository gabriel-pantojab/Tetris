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
        walls[0] = new ShapeRect(0, 2, new Color(77, 77, 77));
        walls[1] = new ShapeRect(0, 6, new Color(77, 77, 77));
        walls[2] = new ShapeRect(0, 10, new Color(77, 77, 77));
        walls[3] = new ShapeRect(0, 14, new Color(77, 77, 77));
    }
    
    public Wall createWall(int row, int column) {
        return new ShapeRect(row, column, Color.GRAY);
    }
    
    public Wall[] walls() {
        Wall[] w = new Wall[4];
        for(int i = 0; i < 4; i++) {
            w[i] = walls[i].clone();
        }
        return w;
    }
}
