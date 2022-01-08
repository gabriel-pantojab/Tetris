package Elements;
import java.awt.Color;

public class ShapeSquare extends Wall{
    public ShapeSquare() {
        super();
        type = TypeWall.SQUARE;
    }
    
    public ShapeSquare(int row_center, int column_center, Color color){
        super(4, row_center, column_center, color);
        createWall(row_center, column_center);
        type = TypeWall.SQUARE;
    }
    
    public ShapeSquare(Position center, Color color){
        super(4, center, color);
        createWall(center.getRow(), center.getColumn());
    }
    
    public void createWall(int row_center, int column_center){
        blocks.add(new Block(row_center-1, column_center, color));
        blocks.add(new Block(row_center, column_center, color));
        blocks.add(new Block(row_center, column_center+1, color));
        blocks.add(new Block(row_center-1, column_center+1, color));
    }
    
    public Wall clone(){
        ShapeSquare clone = new ShapeSquare(center_position, color);
        clone.setBlocks(this.cloneBlocks());
        return clone;
    }
    
    public void rotateLeft(){}
    public void rotateRight(){}
}
