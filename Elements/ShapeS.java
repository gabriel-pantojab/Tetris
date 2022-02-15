package Elements;
import java.awt.Color;

public class ShapeS extends Wall{
    public ShapeS() {
        super();
        type = TypeWall.ESE;
    }
    
    public ShapeS(int row_center, int column_center, Color color){
        super(4, row_center, column_center, color);
        createWall(row_center, column_center);
        type = TypeWall.ESE;
    }
    
    public ShapeS(Position center, Color color){
        super(4, center, color);
        createWall(center.getRow(), center.getColumn());
    }
    
    protected void createWall(int row_center, int column_center){
        blocks.add(new Block(row_center-1, column_center+1 , color));
        blocks.add(new Block(row_center-1, column_center , color));
        blocks.add(new Block(row_center, column_center , color));
        blocks.add(new Block(row_center, column_center-1 , color));
    }
    
    public Wall clone(){
        Position new_center_position = new Position(center_position.getRow(), center_position.getColumn());
        ShapeS clone = new ShapeS(new_center_position, new Color(color.getRGB()));
        clone.setBlocks(this.cloneBlocks());
        return clone;
    }
}
