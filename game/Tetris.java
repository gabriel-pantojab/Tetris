package game;
import javax.swing.*;
import java.awt.*;
import Elements.*;
import java.util.ArrayList;
import java.awt.event.*;


public class Tetris extends JFrame{
    private Game game;
    private LaminaGame lamina_game;
    private LaminaCola cola;
    private JButton start, pause, resume, restart, help;
    public static JLabel line, score;
    
    public Tetris(){
        setLayout(new BorderLayout());
        setBounds(0, 0, 610, 650);
        game = new Game();
        lamina_game = new LaminaGame(game);
        cola = new LaminaCola(game);
        add(lamina_game, BorderLayout.CENTER);
        add(cola, BorderLayout.EAST);
        createButtons();
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        init();
    }
    
    public static void setLine(){
        line.setText("Line: "+LaminaGame.line);
    }
    
    public static void setScore(){
        score.setText("Score: "+LaminaGame.score);
    }
    
    public void init(){
        game.init();
    }
    
    private void createButtons(){
        start  = new JButton("Start");
        start.setBounds(15, 100, 70, 30);
        start.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(!game.isGame()){
                    game.setCurrentWall(game.throwWall());
                    game.initShadowCurrentWall();
                    game.addWallQueue();
                    cola.repaint();
                    game.begin();
                    lamina_game.init();
                    cola.init();
                }
            }
        });
        
        pause  = new JButton("Pause");
        pause.setBounds(15, 150, 70, 30);
        pause.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(game.isGame() && !game.isPause()){
                    game.pause();
                    lamina_game.requestFocusInWindow();
                }
            }
        });
        
        resume = new JButton("Resume");
        resume.setBounds(5, 200, 90, 30);
        resume.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(game.isPause()){
                    game.resume();
                    lamina_game.requestFocusInWindow();
                }
            }
        });
        
        restart = new JButton("Restart");
        restart.setBounds(5, 250, 90, 30);
        restart.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if (game.isGame()) {
                    cola.restart();
                }
            }
        });
        
        help = new JButton("Help");
        help.setBounds(15, 300, 70, 30);
        help.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new Instruccion(Tetris.this);
            }
        });
        
        line = new JLabel("Line: "+LaminaGame.line);
        line.setBounds(20, 350, 100, 30);
        line.setFont(new Font("Arial", Font.BOLD, 15));
        line.setForeground(Color.WHITE);
        
        score = new JLabel("Score: "+LaminaGame.score);
        score.setBounds(20, 380, 100, 30);
        score.setFont(new Font("Arial", Font.BOLD, 15));
        score.setForeground(Color.WHITE);
        
        JPanel panel_buttons = new JPanel();
        panel_buttons.setLayout(null);
        panel_buttons.setBackground(Color.BLACK);
        panel_buttons.setPreferredSize(new Dimension(100, 600));
        panel_buttons.add(start);
        panel_buttons.add(pause);
        panel_buttons.add(resume);
        panel_buttons.add(restart);
        panel_buttons.add(line);
        panel_buttons.add(score);
        panel_buttons.add(help);
        
        
        add(panel_buttons, BorderLayout.WEST);
    }
    
    public static void main(String... args){
        new Tetris();
    }
}

class LaminaGame extends JPanel{
    private Game game;
    public static int line, score;
    
    public LaminaGame(Game game){
        line = 0;
        score = 0;
        setBackground(new Color(153, 217, 234));
        this.game = game;
        addKeyListener(new Manager());
    }
    
    public void init(){
        setFocusable(true);
        requestFocusInWindow();
        java.util.Timer timer = new java.util.Timer();
        java.util.TimerTask task = new java.util.TimerTask(){
            public void run(){
                //if(game.isGame() && !game.isPause() && !game.gameOver()) repaint();
                repaint();
                if(!game.isGame()) timer.cancel();
            }
        };
        timer.schedule(task, 0, 10);
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
            game.getshadowCurrentWall().paintBorder(g);
        }
        game.paintBoard(g);
    }
    
    private class Manager implements KeyListener{
        public void keyPressed(KeyEvent e){
            if (!game.isPause()) { 
                if(e.getKeyCode() == KeyEvent.VK_RIGHT)
                    game.runRightCurrentWall();
                else if(e.getKeyCode() == KeyEvent.VK_LEFT)
                    game.runLeftCurrentWall();
                else if(e.getKeyCode() == KeyEvent.VK_A)
                    game.rotateLeftCurrentWall();
                else if(e.getKeyCode() == KeyEvent.VK_S)
                    game.rotateRightCurrentWall();
                else if(e.getKeyCode() == KeyEvent.VK_DOWN)
                    game.runBottomCurrentWall();
                else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    while(game.runBottomCurrentWall());
                }
                game.runShadowCurrentWall();
            }
        }
        public void keyReleased(KeyEvent e){}
        public void keyTyped(KeyEvent e){}
    }
}

class LaminaCola extends JPanel{
    private Game game;
    java.util.Timer timer;
    public LaminaCola(Game game){
        setBackground(new Color(195, 195, 195));
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
                            Tetris.setScore();
                            game.setCurrentWall(game.throwWall());
                            game.addWallQueue();
                            int cant_clear = game.clearBlocks();
                            if(cant_clear != 0){
                                LaminaGame.line += cant_clear;
                                Tetris.setLine();
                                game.runBottomAllWallsInBoard(cant_clear);
                            }
                            game.runShadowCurrentWall();
                        }
                        repaint();
                    }
                }
                
                if(gameOver) {
                    timer.cancel();
                    LaminaGame.line = 0;
                    LaminaGame.score = 0;
                    JOptionPane.showMessageDialog(null, "GAME OVER!!!");
                    Tetris.setLine();
                    Tetris.setScore();
                    game.end();
                    game.restart();
                    game.init();
                    repaint();
                }
            }
        };
        timer.schedule(task, 0, 500);
    }
    
    public void restart() {
        timer.cancel();
        LaminaGame.line = 0;
        LaminaGame.score = 0;
        Tetris.setLine();
        Tetris.setScore();
        game.end();
        game.restart();
        game.init();
        repaint();
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(Wall w: game.getWalls())
            w.paint(g);
    }
}
