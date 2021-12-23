package game;
import Elements.*;
import java.util.LinkedList;
import java.util.Random;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.*;

public class Game{
    private LinkedList<Wall> walls;
    private Element board[][];
    private ArrayList<Wall> wallsInBoard;
    private Wall currentWall;
    private boolean en_juego, pause, game_over;
    private final Random random = new Random();
    
    private int row_limit;

    private final int LIMIT_ROW    = 28;
    private final int LIMIT_COLUMN = 16;
    private final Color[] colors   = {new Color(0, 0, 200), new Color(0, 200, 0), new Color(128, 0, 255)};
    
    public Game(){
        walls = new LinkedList<Wall>();   
        board = new Element[28][16];
        wallsInBoard = new ArrayList<Wall>();
        row_limit = 0;
        game_over = false;
    }
    
    public void restart(){
        walls.clear();   
        clearBoard();
        wallsInBoard.clear();
        currentWall = null;
        row_limit = 0;
        game_over = false;
    }
    
    private void clearBoard(){
        for(int f=0; f<board.length; f++){
            for(int c=0; c<board[f].length; c++){
                board[f][c] = null;
            }
        }
    }
    
    public void init(){
        for(int i=0; i<4; i++)
            addWallQueue();
    }
    
    public void end(){
        en_juego = false;
        pause    = false;
    }
    
    public void begin(){
        en_juego = true;
    }
    
    public void pause(){
        pause = true;
    }
    
    public void resume(){
        pause = false;
    }
    
    public boolean isGame(){
        return en_juego;
    }
    
    public boolean isPause(){
        return pause;
    }
    
    public LinkedList<Wall> getWalls(){
        return walls;
    }
    
    private synchronized  boolean shocks(){
        for(Wall wall: wallsInBoard){
            for(Block block: wall.getBlocks()){
                if(shockWithCurrentWall(block)) return true;
            }
        }
        return false;
    }
    
    private synchronized boolean shockWithCurrentWall(Block block){
        for(Block b: currentWall.getBlocks()){
            if ((Math.abs(b.getPositionInRow()-block.getPositionInRow()) == 1) && 
            (b.getPositionInColumn() == block.getPositionInColumn()) && (b.getPositionInRow() < block.getPositionInRow()))
            {
                return true;
            }
        }
        return false;
    }
    
    private synchronized  boolean sideCrash(boolean type){
        for(Wall wall: wallsInBoard){
            for(Block block: wall.getBlocks()){
                if (type) {
                    if (sideCrashRight(block)) {
                        return true;
                    }
                }else {
                    if (sideCrashLeft(block)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean sideCrashLeft(Block block) {
        for(Block b: currentWall.getBlocks()){
            if (((Math.abs(b.getPositionInColumn()-block.getPositionInColumn()) == 1) && 
            (b.getPositionInRow() == block.getPositionInRow())) && (block.getPositionInColumn() < b.getPositionInColumn()))
            {
                return true;
            }
        }
        return false;
    }
    
    private boolean sideCrashRight(Block block) {
        for(Block b: currentWall.getBlocks()){
            if (((Math.abs(b.getPositionInColumn()-block.getPositionInColumn()) == 1) && 
            (b.getPositionInRow() == block.getPositionInRow())) && (block.getPositionInColumn() > b.getPositionInColumn()))
            {
                return true;
            }
        }
        return false;
    }
    
    private Wall generateWall(){
        int type    = random.nextInt(7)+1;
        int column  = 2;
        int row     = 27;
        Color color = colors[random.nextInt(colors.length)];
        
        if(type == 1) return new ShapeEle(row, column, color);
        if(type == 2) return new ShapeJ(row, column, color);
        if(type == 3) return new ShapeT(row, column, color);
        if(type == 4) return new ShapeZeta(row, column, color);
        if(type == 5) return new ShapeS(row, column, color);
        if(type == 6) return new ShapeRect(row, column, color);
        else return new ShapeSquare(row, column, color);
    }
    
    public void addWallQueue(){
        if(walls.size() < 4){
            Wall new_wall = generateWall();
            if(!walls.isEmpty()){
                for(Wall w: walls)
                    w.runTop(5);
            }
            walls.addLast(new_wall);
        }
    }
    
    public Wall throwWall(){
        Wall w = walls.pollFirst();
        int column = (int)(Math.random()*12+3);  
        w.setCenter(new Position(0, column));
        w.recalculateWall();
        return w;
    }
    
    public boolean runBottomCurrentWall(){
        if(!currentWall.shockRow(LIMIT_ROW-1) && !shocks()){
            currentWall.runBottom();
            return true;
        }
        verifyGameOver();
        return false;
    }
    
    public void runLeftCurrentWall(){
        if((!currentWall.shockColumn(0) && !sideCrash(false)))
            currentWall.runLeft();
    }
    
    public void runRightCurrentWall(){
        if(!currentWall.shockColumn(LIMIT_COLUMN-1) && !sideCrash(true))
            currentWall.runRight();
    }
    
    public void rotateLeftCurrentWall(){
        Wall clone_currentWall = currentWall.clone();
        clone_currentWall.rotateLeft();
        if(!clone_currentWall.shockColumn(LIMIT_COLUMN-1) && !clone_currentWall.shockColumn(0) && !clone_currentWall.shockRow(LIMIT_ROW-1))
            currentWall.rotateLeft();
    }
    
    public void rotateRightCurrentWall(){
        Wall clone_currentWall = currentWall.clone();
        clone_currentWall.rotateRight();
        if(!clone_currentWall.shockColumn(LIMIT_COLUMN-1) && !clone_currentWall.shockColumn(0) && !clone_currentWall.shockRow(LIMIT_ROW-1))
            currentWall.rotateRight();
    }
    
    public void setCurrentWall(Wall new_wall){
        currentWall = new_wall;
    }
    
    public void addWallsInBoard(Wall w){
        wallsInBoard.add(w);
        try{
            updateBoard();
        }catch(Exception e){
            System.out.println("Failed in addWallsInBoard()");
        }
    }
    
    private synchronized void updateBoard(){
        board = new Element[LIMIT_ROW][LIMIT_COLUMN];
        for(Wall w: wallsInBoard){
            for(Block b: w.getBlocks()){
                board[b.getPositionInRow()][b.getPositionInColumn()] = b;
            }
        }
    }
    
    private synchronized ArrayList<Integer> getRowsClear(){
        ArrayList<Integer> rows = new ArrayList<Integer>();
        for(int f=0; f<board.length; f++){
            boolean verify = false;
            for(int c=0; c<board[f].length; c++){
                if(board[f][c] != null) verify = true;
                else{
                    verify = false;
                    break;
                }
            }
            if(verify){ 
                rows.add(f);
            }
        }
        return rows;
    }
    
    public synchronized int clearBlocks(){
        int clear = 0;
        ArrayList<Integer> rowsForClear = getRowsClear();
        ArrayList<Wall> clone = new ArrayList<Wall>();
        for(Wall w: wallsInBoard){
            clone.add(w.clone());
        }
        if(!rowsForClear.isEmpty()){
            row_limit = rowsForClear.get(rowsForClear.size()-1);
            clear = rowsForClear.size();
            for(Integer row : rowsForClear){
                for(Wall w: clone){
                    w.clearBlocksInRow(row);
                }
            }
        }
        wallsInBoard = clone;
        updateBoard();
        return clear;
    }
    
    public ArrayList<Wall> getWallsInBoard(){
        return wallsInBoard;
    }
    
    public Wall getCurrentWall(){
        return currentWall;
    }
    
    public void runBottomAllWallsInBoard(int cant){
        for(Wall w: wallsInBoard){
            w.runBottom(row_limit, cant); 
        }
    }
    
    private synchronized void verifyGameOver() {
        for(Block block: currentWall.getBlocks()){
            if (block.getPositionInRow() <= 1) game_over =  true; 
        }
    }
    //No funciona del todo bien
    public boolean gameOver(){
        return game_over;
    }
    
    public synchronized void paintBoard(Graphics g) {
        for(Wall w: wallsInBoard){
            w.paint(g);
        }
    }
}
