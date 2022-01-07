package logic;

import Elements.*;
import java.awt.Color;

/**
 * Write a description of class RandomWallFactory here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class RandomWallFactory implements WallFactory{
    public Wall createWall() {
        int type    = random.nextInt(7)+1;
        int column  = 2;
        int row     = 27;
        Color color = colors[random.nextInt(colors.length)];
        
        Wall wall;
        if(type == 1) wall = new ShapeEle(row, column, color);
        else if(type == 2) wall = new ShapeJ(row, column, color);
        else if(type == 3) wall = new ShapeT(row, column, color);
        else if(type == 4) wall = new ShapeZeta(row, column, color);
        else if(type == 5) wall = new ShapeS(row, column, color);
        else if(type == 6) wall = new ShapeRect(row, column, color);
        else wall = new ShapeSquare(row, column, color);
        
        return wall;
    }
}
