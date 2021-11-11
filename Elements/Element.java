package Elements;
import java.awt.Graphics;
import java.awt.Rectangle;


public class Element{
    protected Position position;
    private boolean visible;

    public Element(){
        position = new Position(-1, -1);
        visible  = false;
    }
    
    public Element(Position position){
        this.position = position;
        visible       = true;
    }

    public Element(int positionInRow, int positionInColumn){
        position = new Position(positionInRow, positionInColumn);
        visible  = true;
    }

    public boolean isVisible(){
        return visible;
    }
    
    public void setVisible(boolean new_state){
        visible = new_state;
    }
    
    public Position getPosition(){
        return position;
    }
    
    public void setPosition(Position new_position){
        position = new_position;
    }
    
    public boolean isEmpty(){
        return position.getRow() == -1;
    }

    public int getPositionInRow(){
        return position.getRow();
    }

    public int getPositionInColumn(){
        return position.getColumn();
    }

    public void setPositionInRow(int newPosition){
        position.setRow(newPosition);
    }

    public void setPositionInColumn(int newPosition){
        position.setColumn(newPosition);
    }

    public boolean equalsPosition(Element e){
        return position.equals(e.getPosition());
    }
    
    public boolean intersects(Element e){
        int x1 = (e.getPositionInColumn()+1)*20;
        int y1 = (e.getPositionInRow()+1)*20;
        Rectangle rect = new Rectangle(x1, y1, 20, 20);
        
        int x2 = (getPositionInColumn()+1)*20;
        int y2 = (getPositionInRow()+1)*20;
        Rectangle rect_current = new Rectangle(x2, y2, 20, 20);
        
        return rect_current.intersects(rect);
    }

    public String toString(){
        return "("+position.getRow()+","+position.getColumn()+")";
    }
    
    public void paint(Graphics g){}
}