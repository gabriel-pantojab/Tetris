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
    public static JLabel line, score, scoreMax, hightScore, level;
    public static JLabelChronometer time;
    
    private static BufferedReader lector;
    private static BufferedWriter escritor;
    
    private java.util.Timer timer;
    private java.util.TimerTask task;
    
    private boolean winner = false;
    
    JPanel panel_buttons = new JPanel();
    
    public TetrisFrame(){
        panel_buttons.setLayout(null);
        panel_buttons.setBackground(Color.BLACK);
        panel_buttons.setPreferredSize(new Dimension(130, 600));
        
        setLayout(new BorderLayout());
        setBounds(0, 0, 640, 650);

        game = new Game();
        lamina_game = new LaminaGame(game){
            public void des(){
                TetrisFrame.this.deshabilitarButtons();
            }
            public void act(){
                TetrisFrame.this.habilitarButtons();
            }
        };
        cola = new LaminaCola(game);
        
        add(lamina_game, BorderLayout.CENTER);
        add(cola, BorderLayout.EAST);
        createButtons();
        crearLabels();
        add(panel_buttons, BorderLayout.WEST);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        init();
    }
    
    private void crearLabels() {
        line    = new JLabel("Line: "+LaminaGame.line);
        score   = new JLabel("Score: "+LaminaGame.score);
        level   = new JLabel("Level: "+game.getLevel().getLevel());
        
        time    = new JLabelChronometer("Time: "){
            public void run() {
                int t = this.getChronometer().getSeconds();
                if(this.getChronometer().getMinutes() >= game.getLevel().timeInitTope()) {
                    //mejorar
                    if((t == 30 || t == 59) && (this.getChronometer().getMilliseconds() == 0)) {    
                        game.throwTope();
                    }
                }
            }
        };
        
        scoreMax   = new JLabel("Hight Score:");
        try{
            lector = new BufferedReader(new FileReader("./storage/scoreMax.txt"));
            hightScore = new JLabel(lector.readLine());
            lector.close();
        }catch(IOException io) {
            hightScore = new JLabel("Not Found");
            //System.out.println("Error en la lectura");
        }
        
        //labels
        time.setBounds(10, 50, 300, 30);
        level.setBounds(10, 80, 200, 30);
        line.setBounds(10, 110, 200, 30);
        score.setBounds(10, 140, 200, 30);
        scoreMax.setBounds(10, 170, 300, 30);
        hightScore.setBounds(30, 200, 200, 30);
        
        level.setFont(new Font("Arial", Font.BOLD, 15));
        level.setForeground(Color.WHITE);
        
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
        
        panel_buttons.add(time);
        panel_buttons.add(level);
        panel_buttons.add(line);
        panel_buttons.add(score);
        panel_buttons.add(scoreMax);
        panel_buttons.add(hightScore);
    }
    
    private void createButtons(){
        ManagerButtons managerButtons = new ManagerButtons();
        
        start   = new JButton("Start");
        pause   = new JButton("Pause");
        restart = new JButton("Restart");
        help    = new JButton("Help");
        actDesSombra = new JButton("Activar");
        
        //buttons
        start.setBounds(15, 290, 100, 30);
        pause.setBounds(15, 340, 100, 30);
        help.setBounds(15, 390, 100, 30);
        restart.setBounds(15, 440, 100, 30);
        actDesSombra.setBounds(15, 490, 100, 30);
        
        start.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pause.setCursor(new Cursor(Cursor.HAND_CURSOR));
        restart.setCursor(new Cursor(Cursor.HAND_CURSOR));
        help.setCursor(new Cursor(Cursor.HAND_CURSOR));
        actDesSombra.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        start.addActionListener(managerButtons);
        pause.addActionListener(managerButtons);
        restart.addActionListener(managerButtons);
        help.addActionListener(managerButtons);
        actDesSombra.addActionListener(managerButtons);
        
        panel_buttons.add(start);
        panel_buttons.add(pause);
        panel_buttons.add(restart);
        panel_buttons.add(actDesSombra);
        panel_buttons.add(help);
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
                            controlNextLevel();
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
                try{
                    Thread.sleep(game.getLevel().getDelay());
                }catch(Exception e){}
            }
        };
        timer.schedule(task, 0, 1);
    }
    
    private void controlNextLevel() {
        if (LaminaGame.score >= game.getLevel().getScore()) {
            if(game.nextLevel()) {
                time.restart();
                level.setText("Level: "+game.getLevel().getLevel());
            }else {
                timer.cancel();
                time.pause();
                winner = true;
                JOptionPane.showMessageDialog(TetrisFrame.this, "Winner!!!");
                //Animacion de bloques cayendo :)
                gameOver();
            }
        }
    }
    
    private void throwWall() {
        LaminaGame.score += 10;
        game.setCurrentWall(game.throwWall());
        game.addWallQueue();
        int cant_clear = game.clearBlocks();
        if(cant_clear != 0){
            LaminaGame.score += (100 * cant_clear);
            LaminaGame.line += cant_clear;
            line.setText("Line: "+LaminaGame.line);
            game.runBottomAllWallsInBoard(cant_clear);
        }
        score.setText("Score: "+LaminaGame.score);
        game.runShadowCurrentWall();
    }
    
    private void gameOver() {
        TetrisFrame.writeHightScore();
        LaminaGame.line = 0;
        LaminaGame.score = 0;
        if(!winner) {
            JOptionPane.showMessageDialog(TetrisFrame.this, "GAME OVER!!!");
        }
        time.stop();
        line.setText("Line: "+LaminaGame.line);
        score.setText("Score: "+LaminaGame.score);
        game.end();
        game.restart();
        level.setText("Level: "+game.getLevel().getLevel());
        cola.repaint();   
        lamina_game.repaint();
        if(winner) lamina_game.winnerAnimation();
        else winner = false;
    }
    
    public void init(){
        game.init();
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
    
    public void deshabilitarButtons() {
        start.setEnabled(false);
        pause.setEnabled(false);
        restart.setEnabled(false);
        help.setEnabled(false);
        actDesSombra.setEnabled(false);
    }
    
    public void habilitarButtons() {
        start.setEnabled(true);
        pause.setEnabled(true);
        restart.setEnabled(true);
        help.setEnabled(true);
        actDesSombra.setEnabled(true);
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




