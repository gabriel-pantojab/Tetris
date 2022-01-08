package logic;
import Elements.*;
import java.util.Random;
import java.awt.Color;

/**
 * Write a description of interface WallFactory here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public interface WallFactory {
    public final Random random = new Random();
    public final Color[] colors   = {new Color(0, 0, 200), new Color(0, 200, 0), new Color(128, 0, 255), new Color(255, 127, 39), 
                                       new Color(200, 0, 0), Color.ORANGE, new Color(185, 122, 87), new Color(64, 128, 128),
                                        new Color(251, 49, 201)};
    public abstract Wall createWall(int row, int column);
}
