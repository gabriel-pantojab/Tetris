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
    private TypeWall excluido;
    
    public RandomWallFactory() {
        excluido = null;
    }
    
    public RandomWallFactory(TypeWall wall) {
        excluido = wall;
    }
    
    public Wall createWall(int row, int column) {
        int type;
        Color color = colors[random.nextInt(colors.length)];
        boolean condition = false;
        
        Wall wall;
        
        do{
            type    = random.nextInt(8)+1;
            if(type == 1) wall = new ShapeEle(row, column, color);
            else if(type == 2) wall = new ShapeJ(row, column, color);
            else if(type == 3) wall = new ShapeT(row, column, color);
            else if(type == 4) wall = new ShapeZeta(row, column, color);
            else if(type == 5) wall = new ShapeS(row, column, color);
            else if(type == 6) wall = new ShapeRect(row, column, color);
            else if(type == 7) wall = new ShapeSquare(row, column, color);
            else wall = new ShapeCross(row, column, color);
            if (excluido != null) condition = wall.getType().equals(excluido);
        }while(condition);
        return wall;
    }
}
