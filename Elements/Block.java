package Elements;

import java.awt.Graphics;
import java.awt.Color;

public class Block extends Element{
    private Color color;
    public Block(int row, int column, Color color){
        super(row, column);
        this.color = color;
    }
    
    public Block(Position position, Color color){
        super(position);
        this.color = color;
    }
    
    public void paint(Graphics g){
        int x = (position.getColumn()+1)*20;
        int y = (position.getRow()+1)*20;
    
        g.setColor(color);
        g.fillRoundRect(x, y, 20, 20, 7, 7);
        
        //border
        g.setColor(Color.BLACK);
        g.drawRoundRect(x, y, 20, 20, 7, 7);
        g.setColor(new Color(192, 192, 192));
        g.drawRoundRect(x+1, y+1, 18, 18, 7, 7);
    }
    
    public void paintBorder(Graphics g){
        int x = (position.getColumn()+1)*20;
        int y = (position.getRow()+1)*20;
    
        g.setColor(color);
        g.drawRoundRect(x+2, y+2, 15, 15, 3, 3);
        g.drawRoundRect(x+3, y+3, 13, 13, 3, 3);
        
        //border
        g.setColor(Color.BLACK);
        g.drawRoundRect(x, y, 20, 20, 7, 7);
        g.setColor(new Color(192, 192, 192));
        g.drawRoundRect(x+1, y+1, 18, 18, 7, 7);
    }
    
    public Block clone(){
        return new Block(new Position(position.getRow(), position.getColumn()), color);
    }
}
