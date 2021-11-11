package Elements;
public class Position{
    private int row, column;
    
    public Position(int row, int column){
        this.row    = row;
        this.column = column;
    }
    
    public void setRow(int new_row){
        row = new_row;
    }
    
    public void setColumn(int new_column){
        column = new_column;
    }
    
    public int getRow(){
        return row;
    }
    
    public int getColumn(){
        return column;
    }
    
    public boolean equals(Position other){
        return (row == other.row) && (column == other.column);
    }
    
    public String toString(){
        return "("+row+","+column+")";
    }
}
