package Elements;

import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Color;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Wall{
    protected ArrayList<Block> blocks;
    protected Color color;
    protected int length;
    protected Position center_position;
    private final ReentrantLock lock = new ReentrantLock();
    
    public Wall(int length, int row_center, int column_center, Color color){
        this.length     = length;
        this.color      = color;
        blocks          = new ArrayList();
        center_position = new Position(row_center, column_center);
    }
    
    public Wall(int length, Position center, Color color){
        this.length     = length;
        this.color      = color;
        blocks          = new ArrayList();
        center_position = center;
    }
    
    protected abstract void createWall(int row_center, int column_center);
    public abstract Wall clone();
    
    public void rotateLeft(){
        for(Block block: blocks){
            Position position_rotate = computeRotatePosition(block.getPosition(), false);
            int new_row    = position_rotate.getRow() + center_position.getRow();
            int new_column = position_rotate.getColumn() + center_position.getColumn();
            block.setPosition(new Position(new_row, new_column));
        }
    }
    
    public void rotateRight(){
        for(Block block: blocks){
            Position position_rotate = computeRotatePosition(block.getPosition(), true);
            int new_row    = position_rotate.getRow() + center_position.getRow();
            int new_column = position_rotate.getColumn() + center_position.getColumn();
            block.setPosition(new Position(new_row, new_column));
        }
    }
    
    private Position computeRotatePosition(Position p, boolean horario){
        int row_origen    = p.getRow() - center_position.getRow();
        int column_origen = p.getColumn() - center_position.getColumn();
        if(horario) return new Position(column_origen, -row_origen);
        else return new Position(-column_origen, row_origen);
    }
    
    public void runTop(){
        runTopBottom(-1);
    }
    
    public void runBottom(){
        runTopBottom(1);
    }
    
    public void runLeft(){
        runLeftRight(-1);
    }
    
    public void runRight(){
        runLeftRight(1);
    }
    
    private void runTopBottom(int step){
        lock.lock();
        setCenter(new Position(center_position.getRow()+step, center_position.getColumn()));
        for(Block block: blocks){
            block.setPositionInRow(block.getPositionInRow()+step);
        }
        lock.unlock();
    }
    
    private void runLeftRight(int step){
        setCenter(new Position(center_position.getRow(), center_position.getColumn()+step));
        for(Block block: blocks){
            block.setPositionInColumn(block.getPositionInColumn()+step);
        }
    }
    
    public void rotateLeft(int cant_rotate){
        for(int i=1; i<=cant_rotate; i++) rotateLeft();
    }
    
    public void rotateRifht(int cant_rotate){
        for(int i=1; i<=cant_rotate; i++) rotateRight();
    }
    
    public void runTop(int cant_run){
        for(int i=1; i<=cant_run; i++) runTop();
    }
    
    public void paint(Graphics g){
        lock.lock();
        for(Block block: blocks) block.paint(g);
        lock.unlock();
    }
    
    public void paintBorder(Graphics g){
        lock.lock();
        for(Block block: blocks) block.paintBorder(g);
        lock.unlock();
    }
    
    public Position center(){
        return center_position;
    }
    
    public void setCenter(Position new_position){
        center_position = new_position;
    }
    
    public void recalculateWall(){
        blocks.clear();
        createWall(center_position.getRow(), center_position.getColumn());
    }
    
    public boolean shockPosition(Position limit_position){
        for(Block block: blocks){
            if(block.intersects(new Block(limit_position, null)))
                return true;
        }
        return false;
    }
    
    public boolean shockRow(int row){
        for(Block block: blocks){
            if(block.getPositionInRow() == row)
                return true;
        }
        return false;
    }
    
    public boolean shockColumn(int column){
        for(Block block: blocks){
            if(block.getPositionInColumn() == column)
                return true;
        }
        return false;
    }
    
    public ArrayList<Block> cloneBlocks(){
        ArrayList<Block> other_blocks = new ArrayList<Block>();
        for(Block block: blocks){
            other_blocks.add(block.clone());
        }
        return other_blocks;
    }
    
    public void clearBlocksInRow(int row){
        lock.lock();
        boolean center = false;
        ArrayList<Integer> positionsClear = new ArrayList<Integer>();
        for(Block block: blocks){
            if(block.getPositionInRow() == row) 
                positionsClear.add(blocks.indexOf(block));
            if(block.getPosition().equals(center_position)) 
                center = true;
        }
        ArrayList<Block> blocksForClear = new ArrayList<Block>();
        for(Integer i: positionsClear){
            blocksForClear.add(blocks.get(i));
        }
        for(Block b: blocksForClear){
            blocks.remove(b);
        }
        if(center) updateCenter();
        lock.unlock();
    }
    
    private void updateCenter(){
        int index = 0;
        int row_index = 0;
        if(blocks.size() > 0)
            row_index = blocks.get(0).getPositionInRow();
        else return;
        for(int i=0; i<blocks.size(); i++){
            if(blocks.get(i).getPositionInRow() > row_index){
                index = i;
                row_index = blocks.get(i).getPositionInRow();
            }
        }
        setCenter(new Position(blocks.get(index).getPositionInRow(), blocks.get(index).getPositionInColumn()));
    }
    
    public void setBlocks(ArrayList<Block> other_blocks){
        blocks = other_blocks;
    }
    
    public ArrayList<Block> getBlocks(){
        return blocks;
    }
    
    //runBottom especial
    public void runBottom(int row_limit, int cant){
        while(cant > 0){
            if(center_position.getRow() < row_limit)
                 setCenter(new Position(center_position.getRow()+1, center_position.getColumn()));
            for(Block block: blocks){
                if(block.getPositionInRow() < row_limit)
                    block.setPositionInRow(block.getPositionInRow()+1);
            }
            cant--;
        }
    }
}
