package Elements;
import java.awt.Color;
import java.util.ArrayList;

public class ShapeZeta extends Wall{
    public ShapeZeta() {
        super();
        type = TypeWall.ZETA;
    }
    
    public ShapeZeta(int row_center, int column_center, Color color){
        super(4, row_center, column_center, color);
        createWall(row_center, column_center);
        type = TypeWall.ZETA;
    }
    
    public ShapeZeta(Position center, Color color){
        super(4, center, color);
        createWall(center.getRow(), center.getColumn());
    }
    
    protected void createWall(int row_center, int column_center){
        blocks.add(new Block(row_center-1, column_center-1 , color));
        blocks.add(new Block(row_center-1, column_center , color));
        blocks.add(new Block(row_center, column_center , color));
        blocks.add(new Block(row_center, column_center+1 , color));
    }
    
    public Wall clone(){
        ShapeZeta clone = new ShapeZeta(center_position, color);
        clone.setBlocks(cloneBlocks());
        return clone;
    }
}
