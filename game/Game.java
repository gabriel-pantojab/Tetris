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
    
    private boolean en_juego, pause, game_over, activar_sombra;
    
    private Wall currentWall;
    private Wall shadowCurrentWall;
    
    private int row_limit; //guarda la informacion de cuantas filas se debe borrar

    private final Random random = new Random();
    private final int LIMIT_ROW    = 28;
    private final int LIMIT_COLUMN = 16;
    private final Color[] colors   = {new Color(0, 0, 200), new Color(0, 200, 0), new Color(128, 0, 255), new Color(255, 127, 39), 
                                       new Color(200, 0, 0), Color.ORANGE, new Color(185, 122, 87), new Color(64, 128, 128),
                                        new Color(251, 49, 201)};
    
    public Game(){
        walls = new LinkedList<Wall>();   
        board = new Element[28][16];
        wallsInBoard = new ArrayList<Wall>();
        row_limit = 0;
        game_over = false;
        activar_sombra = false;
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
        for(int i=0; i<5; i++)
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
    
    public void activarSombra() {
        activar_sombra = true;
        initShadowCurrentWall();
    }
    
    public void desactivarSombra() {
        activar_sombra = false;
        shadowCurrentWall = null;
    }
    
    public boolean conSombra() {
        return activar_sombra;
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
    
    public ArrayList<Wall> getWallsInBoard(){
        return wallsInBoard;
    }
    
    public Wall getCurrentWall(){
        return currentWall;
    }
    
    public Wall getshadowCurrentWall(){
        return shadowCurrentWall;
    }
    
    public boolean gameOver(){
        return game_over;
    }
    
    public void setCurrentWall(Wall new_wall){
        currentWall = new_wall;
    }
    
    public void initShadowCurrentWall() {
        if (currentWall != null) {
            shadowCurrentWall = currentWall.clone();
            runShadowCurrentWall();
        }
    }
    
    private synchronized  boolean shocks(Wall nWall){
        for(Wall wall: wallsInBoard){
            for(Block block: wall.getBlocks()){
                if(shockWithCurrentWall(block, nWall)) return true;
            }
        }
        return false;
    }
    
    private synchronized boolean shockWithCurrentWall(Block block, Wall nWall){
        for(Block b: nWall.getBlocks()){
            if ((Math.abs(b.getPositionInRow()-block.getPositionInRow()) == 1) && 
            (b.getPositionInColumn() == block.getPositionInColumn()) && (b.getPositionInRow() < block.getPositionInRow()))
            {
                return true;
            }
        }
        return false;
    }
    
    private synchronized  boolean sideCrash(RotateDirection direction){
        for(Wall wall: wallsInBoard){
            for(Block block: wall.getBlocks()){
                if (direction == RotateDirection.RIGHT) {
                    if (sideCrash(block, direction)) {
                        return true;
                    }
                }else {
                    if (sideCrash(block, direction)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean sideCrash(Block block, RotateDirection direction) {
        for(Block b: currentWall.getBlocks()){
            if (((Math.abs(b.getPositionInColumn()-block.getPositionInColumn()) == 1) && 
            (b.getPositionInRow() == block.getPositionInRow())))
            {
                if(direction == RotateDirection.RIGHT) {
                    if(block.getPositionInColumn() > b.getPositionInColumn()) {
                        return true;
                    }
                }else{
                    if(block.getPositionInColumn() < b.getPositionInColumn()) {
                        return true;
                    }
                }
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
        if(walls.size() < 5){
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
        int column = 7;//(int)(Math.random()*12+3);  
        if(w instanceof ShapeRect)
            w.setCenter(new Position(0, column));
        else 
            w.setCenter(new Position(1, column));
        w.recalculateWall();
        //Controlar si hay espacio para añadirlo
        for (Block b : w.getBlocks()) {
            if (board[w.center().getRow()][b.getPositionInColumn()] != null) {
                w.setCenter(new Position(w.center().getRow()-1, column));
                w.recalculateWall();
                break;
            }
        }
        return w;
    }
    
    public void runShadowCurrentWall() {
        shadowCurrentWall = currentWall.clone();
        while(!shadowCurrentWall.shockRow(LIMIT_ROW-1) && !shocks(shadowCurrentWall)) {
            shadowCurrentWall.runBottom();
        }
    }
    
    public boolean runBottomCurrentWall(){
        if(!currentWall.shockRow(LIMIT_ROW-1) && !shocks(currentWall)){
            currentWall.runBottom();
            return true;
        }
        if (currentWall.center().getRow() < LIMIT_ROW / 2) {
            verifyGameOver();
        }
        return false;
    }
    
    public void runLeftCurrentWall(){
        if((!currentWall.shockColumn(0) && !sideCrash(RotateDirection.LEFT)))
            currentWall.runLeft();
    }
    
    public void runRightCurrentWall(){
        if(!currentWall.shockColumn(LIMIT_COLUMN-1) && !sideCrash(RotateDirection.RIGHT))
            currentWall.runRight();
    }
    
    private boolean crashWithWallsInBoard(Wall clone_currentWall) {
        for(Block b : clone_currentWall.getBlocks()) {
            int row, column;
            row = b.getPositionInRow();
            column = b.getPositionInColumn();
            if (validarPosiciones(row, column)){
                if (board[row][column] != null) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean validarPosiciones(int row, int column) {
        return (row >= 0 && column >= 0) && (row < LIMIT_ROW && column < LIMIT_COLUMN);
    }
    
    public void rotateRightCurrentWall(){
        Wall clone_currentWall = currentWall.clone();
        clone_currentWall.rotateRight();
        controlRotacion(clone_currentWall, RotateDirection.RIGHT);
    }
    
    public void rotateLeftCurrentWall(){
        Wall clone_currentWall = currentWall.clone();
        clone_currentWall.rotateLeft();
        controlRotacion(clone_currentWall, RotateDirection.LEFT);
    }
    
    private void controlRotacion(Wall clone_currentWall, RotateDirection direction) {
        if(rotateCondition(clone_currentWall)) {
            if(!crashWithWallsInBoard(clone_currentWall)){
                if (direction == RotateDirection.RIGHT) currentWall.rotateRight();
                else currentWall.rotateLeft();
            }else{
                clone_currentWall.runTop(1);
                if (!crashWithWallsInBoard(clone_currentWall)) {
                    currentWall.runTop(1);
                    if (direction == RotateDirection.RIGHT) currentWall.rotateRight();
                    else currentWall.rotateLeft();
                }else{
                    currentWall.runTop(2);
                    if (direction == RotateDirection.RIGHT) currentWall.rotateRight();
                    else currentWall.rotateLeft();
                }
            }
        }
    }
    
    private boolean rotateCondition(Wall clone_currentWall) {
        return !clone_currentWall.shockColumn(LIMIT_COLUMN-1) && !clone_currentWall.shockColumn(0) 
                            && !clone_currentWall.shockRow(LIMIT_ROW-1);
    }
    
    public synchronized void addWallsInBoard(Wall w){
        wallsInBoard.add(w);
        try{
            updateBoard();
        }catch(Exception e){
            //System.out.println("Failed in addWallsInBoard()");
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
    
    public void runBottomAllWallsInBoard(int cant){
        for(Wall w: wallsInBoard){
            w.runBottom(row_limit, cant); 
        }
    }
    
    private synchronized void verifyGameOver() {
        for(Block block: currentWall.getBlocks()){
            if (block.getPositionInRow() < 1) game_over =  true; 
        }
    }
    
    public synchronized void paintBoard(Graphics g) {
        for(Wall w: wallsInBoard){
            for (Block b : w.getBlocks()) {
                if (b.getPositionInRow() >= 0) b.paint(g);
                else b.paintBorder(g);
            }
        }
    }
}
