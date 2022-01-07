package visual;
import javax.swing.*;
import java.awt.*;
import Elements.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;
import logic.*;

public class TetrisFrame extends JFrame{
    private Game game;
    private LaminaGame lamina_game;
    private LaminaCola cola;
    private JButton start, pause, restart, help, actDesSombra;
    public static JLabel line, score, scoreMax, hightScore;
    public static JLabelChronometer time;
    
    private static BufferedReader lector;
    private static BufferedWriter escritor;
    
    private static int minutes;
    
    private java.util.Timer timer;
    private java.util.TimerTask task;
    
    public TetrisFrame(){
        setLayout(new BorderLayout());
        setBounds(0, 0, 640, 650);

        minutes = 0;
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
    
    private void play() {
        timer = new java.util.Timer();
        task = new java.util.TimerTask(){
            public void run() {
                boolean gameOver = game.gameOver();
                if(game.isGame() && !game.isPause() && !gameOver){
                    boolean run = game.runBottomCurrentWall();
                    if(!run && !game.gameOver()){
                        game.addWallsInBoard(game.getCurrentWall());
                        if(!gameOver) {
                            throwWall();
                        }
                        cola.repaint();
                    }
                    lamina_game.repaint();
                }else{
                    if(gameOver) {
                        timer.cancel();
                        time.pause();
                        gameOver();
                    }
                }
                minutes = time.getChronometer().getMinutes();
            }
        };
        timer.schedule(task, 0, 500);
    }
    
    private void throwWall() {
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
    
    private void gameOver() {
        TetrisFrame.writeHightScore();
        LaminaGame.line = 0;
        LaminaGame.score = 0;
        JOptionPane.showMessageDialog(TetrisFrame.this, "GAME OVER!!!");
        time.stop();
        TetrisFrame.setLine();
        TetrisFrame.setScore();
        game.end();
        game.restart();
        game.init();
        cola.repaint();   
        lamina_game.repaint();
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
        ManagerButtons managerButtons = new ManagerButtons();
        
        start   = new JButton("Start");
        pause   = new JButton("Pause");
        restart = new JButton("Restart");
        help    = new JButton("Help");
        actDesSombra = new JButton("Activar");
        line    = new JLabel("Line: "+LaminaGame.line);
        score   = new JLabel("Score: "+LaminaGame.score);
        time    = new JLabelChronometer();
        
        scoreMax   = new JLabel("Hight Score:");
        try{
            lector = new BufferedReader(new FileReader("./storage/scoreMax.txt"));
            hightScore = new JLabel(lector.readLine());
            lector.close();
        }catch(IOException io) {
            hightScore = new JLabel("Not Found");
            //System.out.println("Error en la lectura");
        }
        
        
        start.setBounds(30, 100, 70, 30);
        pause.setBounds(30, 150, 70, 30);
        restart.setBounds(20, 200, 90, 30);
        help.setBounds(30, 250, 70, 30);
        actDesSombra.setBounds(15, 300, 100, 30);
        line.setBounds(10, 350, 200, 30);
        score.setBounds(10, 380, 200, 30);
        
        time.setBounds(30, 50, 300, 30);
        
        scoreMax.setBounds(10, 410, 300, 30);
        hightScore.setBounds(30, 440, 200, 30);
        
        start.addActionListener(managerButtons);
        pause.addActionListener(managerButtons);
        restart.addActionListener(managerButtons);
        help.addActionListener(managerButtons);
        actDesSombra.addActionListener(managerButtons);
        
        time.setFont(new Font("Arial", Font.BOLD, 15));
        time.setForeground(Color.WHITE);
        
        line.setFont(new Font("Arial", Font.BOLD, 15));
        line.setForeground(Color.WHITE);
        
        score.setFont(new Font("Arial", Font.BOLD, 15));
        score.setForeground(Color.WHITE);
        
        scoreMax.setFont(new Font("Arial", Font.BOLD, 15));
        scoreMax.setForeground(Color.WHITE);
        hightScore.setFont(new Font("Arial", Font.BOLD, 15));
        hightScore.setForeground(Color.WHITE);
        
        JPanel panel_buttons = new JPanel();
        panel_buttons.setLayout(null);
        panel_buttons.setBackground(Color.BLACK);
        panel_buttons.setPreferredSize(new Dimension(130, 600));
        panel_buttons.add(time);
        panel_buttons.add(start);
        panel_buttons.add(pause);
        panel_buttons.add(restart);
        panel_buttons.add(line);
        panel_buttons.add(score);
        panel_buttons.add(help);
        panel_buttons.add(actDesSombra);
        
        panel_buttons.add(scoreMax);
        panel_buttons.add(hightScore);
        
        
        add(panel_buttons, BorderLayout.WEST);
    }
    
        public static void setHightScore(){
        try{
            lector = new BufferedReader(new FileReader("./storage/scoreMax.txt"));
            hightScore.setText(lector.readLine());
            lector.close();
        }catch (IOException io){
        }
    }
    
    public static void writeHightScore() {
        try{
            lector = new BufferedReader(new FileReader("./storage/scoreMax.txt"));
            int puntuacionA = Integer.parseInt(lector.readLine());
            int puntuacionB = LaminaGame.score;
            lector.close();
            if (puntuacionB > puntuacionA) {
                escritor = new BufferedWriter(new FileWriter("./storage/scoreMax.txt"));
                escritor.write(""+puntuacionB);
                escritor.close();
                setHightScore();
            }
        }catch (IOException io){
        }
    }
    
    private class ManagerButtons implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Object boton = e.getSource();
            if (boton.equals(start)) {
                if(!game.isGame()){
                    game.setCurrentWall(game.throwWall());
                    game.initShadowCurrentWall();
                    game.addWallQueue();
                    cola.repaint();
                    game.begin();
                    lamina_game.init();
                    //cola.init();
                    play();
                    time.start();
                }            
            }else if (boton.equals(pause)) {
                if(game.isGame() && !game.isPause()){
                    game.pause();
                    time.pause();
                    lamina_game.requestFocusInWindow();
                }
            }else if (boton.equals(restart)) {
                if (game.isGame()) {
                    game.pause();
                    time.pause();
                    timer.cancel();
                }
                int opcion = JOptionPane.showConfirmDialog(TetrisFrame.this, "¿Esta seguro de reiniciar el juego?", "Warning", JOptionPane.OK_CANCEL_OPTION);
                if(opcion == JOptionPane.YES_OPTION) {
                    if (game.isGame()) {
                        cola.restart();
                        time.stop();
                        //lamina_game.repaint();
                    }
                }
                if(time.getChronometer().running()) {
                    time.resume();
                }
                lamina_game.requestFocusInWindow();
            }else if (boton.equals(help)) {
                if (game.isGame()) {
                    game.pause();
                    time.pause();
                }
                new Instruccion(TetrisFrame.this);
                lamina_game.requestFocusInWindow();
            }else if (boton.equals(actDesSombra)) {
                if (game.isGame()) {
                    game.pause();
                    time.pause();
                }
                String msg = "¿Activar sombra?";
                if (game.conSombra()) {
                    msg = "¿Desactivar sombra?";
                }
                int opcion = JOptionPane.showConfirmDialog(TetrisFrame.this, msg, "Sombra", JOptionPane.OK_CANCEL_OPTION);
                lamina_game.requestFocusInWindow();
                if (opcion == JOptionPane.YES_OPTION) {
                    if (msg.equals("¿Activar sombra?")) {
                        actDesSombra.setText("Desactivar");
                        game.activarSombra();
                    }else{
                        actDesSombra.setText("Activar");
                        game.desactivarSombra();
                    }
                }
            }
            lamina_game.repaint();
        }
    }
}




