package visual;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Elements.*;
import logic.*;

public class LaminaGame extends JPanel{
    private Game game;
    public static int line, score;
    private Manager manager;
    
    public LaminaGame(Game game){
        line = 0;
        score = 0;
        setBackground(new Color(153, 217, 234));
        manager = new Manager();
        this.game = game;
        addKeyListener(manager);
    }
    
    public void init(){
        setFocusable(true);
        requestFocusInWindow();
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        g.fillRoundRect(16, 16, 329, 569, 7,7);
        g.setColor(new Color(160, 217, 240));
        g.fillRoundRect(20, 20, 320, 560, 7,7 );
        g.setColor(Color.WHITE);
        g.drawRoundRect(20, 20, 320, 560, 7,7 );
        
        g.setColor(Color.GRAY);
        for(int i=1; i<29; i++){
            g.drawLine(20, i*20, 340, i*20);
            g.drawLine(i*20, 20, i*20, 580);
        }
        
        
        if(game.getCurrentWall() != null) {
            for (Block b : game.getCurrentWall().getBlocks()) {
                if (b.getPositionInRow() >= 0) {
                    b.paint(g);
                }else {
                    b.paintBorder(g);
                }
            }
            if (game.conSombra()) {
                game.getshadowCurrentWall().paintBorder(g);
            }
        }
        game.paintBoard(g);
        
        if (game.isPause()) {
            g.setFont(new Font("Arial", Font.BOLD, 27));
            g.setColor(Color.BLACK);
            g.drawString("ENTER para continuar", 33, 300);
        }
    }
    
    private class Manager extends MouseAdapter implements KeyListener{
        public void keyPressed(KeyEvent e){
            if (!game.isPause()) { 
                if(e.getKeyCode() == KeyEvent.VK_RIGHT)
                    game.runRightCurrentWall();
                else if(e.getKeyCode() == KeyEvent.VK_LEFT)
                    game.runLeftCurrentWall();
                else if(e.getKeyCode() == KeyEvent.VK_A){
                    game.rotateLeftCurrentWall();
                }else if(e.getKeyCode() == KeyEvent.VK_S){
                    game.rotateRightCurrentWall();
                }else if(e.getKeyCode() == KeyEvent.VK_DOWN)
                    game.runBottomCurrentWall();
                else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    while(game.runBottomCurrentWall());
                }
                game.runShadowCurrentWall();
            }
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if(game.isPause()) {
                    game.resume();
                }
            }
            repaint();
        }
        public void keyReleased(KeyEvent e){}
        public void keyTyped(KeyEvent e){}
    }
}