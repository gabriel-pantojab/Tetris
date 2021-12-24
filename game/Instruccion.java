package game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Write a description of class Instruccion here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Instruccion extends JDialog{
    private JLabel up, down, right, left, pause, resume, title;
    private JLabelURL urlGit;
    
    private Container panel;
    public Instruccion(TetrisFrame f) {
        super(f, "Help", true);
        setLayout(new BorderLayout());
        setBounds(0, 0, 350, 260);
        setLocationRelativeTo(f);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        init();
        setVisible(true);
    }
    
    private void init() {
        urlGit = new JLabelURL("Respositorio: ", "https://github.com/GabrielPB96/Tetris.git");
        
        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(new Panel());
        
        add(scroll, BorderLayout.CENTER);
        add(urlGit, BorderLayout.SOUTH);
    }
    
    public static void main() {
        new Instruccion(null);
    }
    
    private class Panel extends JPanel {
        public Panel() {
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(300, 180));
        }
        
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("CONTROLES", 108, 20);
            
            //down
            int x2[] = {40, 45, 50};
            int y2[] = {50, 60, 50};
            g.fillPolygon(x2, y2, 3);
            g.fillRect(43, 40, 5, 10);
            g.setFont(new Font("Arial", Font.PLAIN, 15));
            g.drawString("Acelera el descenso.", 70, 55);
            
            //right
            int x3[] = {45, 55, 45};
            int y3[] = {67, 72, 77};
            g.fillPolygon(x3, y3, 3);
            g.fillRect(35, 70, 10, 5);
            g.setFont(new Font("Arial", Font.PLAIN, 15));
            g.drawString("Mueve el bloque a la derecha.", 70, 80);
            
            //left
            int x4[] = {44, 34, 44};
            int y4[] = {92, 97, 102};
            g.fillPolygon(x4, y4, 3);
            g.fillRect(44, 95, 10, 5);
            g.setFont(new Font("Arial", Font.PLAIN, 15));
            g.drawString("Mueve el bloque a la izquierda.", 70, 105);
            
            //rotar a la izquierda
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("A", 40, 130);
            g.setFont(new Font("Arial", Font.PLAIN, 15));
            g.drawString("Rota el bloque a la izquierda.", 70, 130);
            
            //rotar a la derecha
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("S", 40, 157);
            g.setFont(new Font("Arial", Font.PLAIN, 15));
            g.drawString("Rota el bloque a la derecha.", 70, 155);
            
            //run
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("SPACE", 10, 183);
            g.setFont(new Font("Arial", Font.PLAIN, 15));
            g.drawString("Coloca el bloque.", 90, 180);
        }
    }
}
