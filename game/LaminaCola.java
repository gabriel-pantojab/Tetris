package game;
import javax.swing.*;
import java.awt.*;
import Elements.*;

public class LaminaCola extends JPanel{
    private Game game;
    java.util.Timer timer;
    
    public LaminaCola(Game game){
        //setBackground(new Color(195, 195, 195));
        setBackground(new Color(153, 217, 234));
        setPreferredSize(new Dimension(140, 600));
        this.game = game;
    }
    
    public void init(){
        timer = new java.util.Timer();
        java.util.TimerTask task = new java.util.TimerTask(){
            public void run(){
                boolean gameOver = game.gameOver();
                if(game.isGame() && !game.isPause() && !gameOver){
                    if(!game.runBottomCurrentWall()){
                        game.addWallsInBoard(game.getCurrentWall());
                        if(!game.gameOver()) {
                            LaminaGame.score += 10;
                            game.setCurrentWall(game.throwWall());
                            game.addWallQueue();
                            int cant_clear = game.clearBlocks();
                            if(cant_clear != 0){
                                LaminaGame.score += (100 * cant_clear);
                                LaminaGame.line += cant_clear;
                                TetrisFrame.setLine();
                                game.runBottomAllWallsInBoard(cant_clear);
                            }
                            TetrisFrame.setScore();
                            game.runShadowCurrentWall();
                        }
                        repaint();
                    }
                }else{                
                    if(gameOver) {
                        TetrisFrame.writeHightScore();
                        timer.cancel();
                        LaminaGame.line = 0;
                        LaminaGame.score = 0;
                        JOptionPane.showMessageDialog(null, "GAME OVER!!!");
                        TetrisFrame.setLine();
                        TetrisFrame.setScore();
                        game.end();
                        game.restart();
                        game.init();
                        repaint();
                    }
                }
            }
        };
        timer.schedule(task, 0, 500);
    }
    
    public void restart() {
        timer.cancel();
        LaminaGame.line = 0;
        LaminaGame.score = 0;
        TetrisFrame.setLine();
        TetrisFrame.setScore();
        game.end();
        game.restart();
        game.init();
        repaint();
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        g.drawRoundRect(8, 98, 124, 105, 5, 5);
        
        g.setColor(new Color(200, 191, 231));
        g.fillRoundRect(10, 100, 120, 100, 5, 5);
        
        g.setColor(Color.BLACK);
        g.drawRoundRect(10, 100, 120, 100, 5, 5);
        
        g.setColor(Color.GRAY);
        g.drawRoundRect(9, 100, 122, 101, 5, 5);
        
        //*****************************
        g.setColor(Color.BLACK);
        g.drawRoundRect(8, 227, 124, 356, 5, 5);
        
        g.setColor(new Color(200, 191, 231));
        g.fillRoundRect(10, 230, 120, 351, 5, 5);
        
        g.setColor(Color.BLACK);
        g.drawRoundRect(10, 230, 120, 351, 5, 5);
        
        g.setColor(Color.GRAY);
        g.drawRoundRect(9, 228, 122, 353, 5, 5);
        
        for(Wall w: game.getWalls())
            w.paint(g);
            
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.BLACK);
        g.drawString("NEXT", 45, 125);
    }
}