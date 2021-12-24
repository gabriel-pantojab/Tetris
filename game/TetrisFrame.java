package game;
import javax.swing.*;
import java.awt.*;
import Elements.*;
import java.util.ArrayList;
import java.awt.event.*;
import java.io.*;


public class TetrisFrame extends JFrame{
    private Game game;
    private LaminaGame lamina_game;
    private LaminaCola cola;
    private JButton start, pause, restart, help, actDesSombra;
    public static JLabel line, score, scoreMax, hightScore;
    
    private static BufferedReader lector;
    private static BufferedWriter escritor;
    
    public TetrisFrame(){
        setLayout(new BorderLayout());
        setBounds(0, 0, 640, 650);

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
        ManagerButtons managerButtons = new ManagerButtons();
        
        start   = new JButton("Start");
        pause   = new JButton("Pause");
        restart = new JButton("Restart");
        help    = new JButton("Help");
        actDesSombra = new JButton("Activar");
        line    = new JLabel("Line: "+LaminaGame.line);
        score   = new JLabel("Score: "+LaminaGame.score);
        
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
        
        scoreMax.setBounds(10, 410, 300, 30);
        hightScore.setBounds(30, 440, 200, 30);
        
        start.addActionListener(managerButtons);
        pause.addActionListener(managerButtons);
        restart.addActionListener(managerButtons);
        help.addActionListener(managerButtons);
        actDesSombra.addActionListener(managerButtons);
        
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
                    cola.init();
                }            
            }else if (boton.equals(pause)) {
                if(game.isGame() && !game.isPause()){
                    game.pause();
                    lamina_game.requestFocusInWindow();
                }
            }else if (boton.equals(restart)) {
                if (game.isGame()) {
                    game.pause();
                }
                int opcion = JOptionPane.showConfirmDialog(TetrisFrame.this, "¿Esta seguro de reiniciar el juego?", "Warning", JOptionPane.OK_CANCEL_OPTION);
                if(opcion == JOptionPane.YES_OPTION) {
                    if (game.isGame()) {
                        cola.restart();
                    }
                }
                lamina_game.requestFocusInWindow();
            }else if (boton.equals(help)) {
                if (game.isGame()) {
                    game.pause();
                }
                new Instruccion(TetrisFrame.this);
                lamina_game.requestFocusInWindow();
            }else if (boton.equals(actDesSombra)) {
                if (game.isGame()) {
                    game.pause();
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
        }
    }
}




