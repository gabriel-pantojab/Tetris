package Elements;
import java.awt.Color;

public class ShapeJ extends Wall{
    public ShapeJ() {
        super();
        type = TypeWall.JOTA;
    }
    
    public ShapeJ(int row_center, int column_center, Color color){
        super(4, row_center, column_center, color);
        createWall(row_center, column_center);
        type = TypeWall.JOTA;
    }
    
    public ShapeJ(Position center, Color color){
        super(4, center, color);
        createWall(center.getRow(), center.getColumn());
    }
    
    protected void createWall(int row_center, int column_center){
        blocks.add(new Block(row_center-1, column_center-1, color));
        blocks.add(new Block(row_center, column_center-1, color));
        blocks.add(new Block(row_center, column_center, color));
        blocks.add(new Block(row_center, column_center+1, color));
    }
    
    public Wall clone(){
        Position new_center_position = new Position(center_position.getRow(), center_position.getColumn());
        ShapeJ clone = new ShapeJ(new_center_position, new Color(color.getRGB()));
        clone.setBlocks(this.cloneBlocks());
        return clone;
    }
}
