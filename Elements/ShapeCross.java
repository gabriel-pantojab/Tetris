package Elements;
import java.awt.Color;


public class ShapeCross extends Wall{
    public ShapeCross() {
        super();
        type = TypeWall.CRUZ;
    }
    
    public ShapeCross(int row_center, int column_center, Color color){
        super(5, row_center, column_center, color);
        createWall(row_center, column_center);
        type = TypeWall.CRUZ;
    }
    
    public ShapeCross(Position center, Color color){
        super(5, center, color);
        createWall(center.getRow(), center.getColumn());
    }
    
    public void createWall(int row_center, int column_center){
        blocks.add(new Block(row_center, column_center-1, color));
        blocks.add(new Block(row_center, column_center, color));
        blocks.add(new Block(row_center, column_center+1, color));
        blocks.add(new Block(row_center-1, column_center , color));
        blocks.add(new Block(row_center+1, column_center , color));
    }
    
    public Wall clone(){
        ShapeCross clone = new ShapeCross(center_position, color);
        clone.setBlocks(cloneBlocks());
        return clone;
    }
}
