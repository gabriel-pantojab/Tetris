 

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicButtonUI;

public class ShapedButton {
    private static JFrame frame;

    public Component createComponents() {
        // Tres botones para probar diferentes efectos
        JButton roundButton = new JButton("Redondo");
        JButton squareButton = new JButton("Cuadrado");
        JButton polygonButton = new JButton("Poligonal");
        
        // Establecemos un UI específico
        ShapedButtonUI roundUI = new ShapedButtonUI();
        roundUI.setShape(ButtonShape.ROUND, roundButton);
        roundButton.setUI(roundUI);
        roundButton.setPreferredSize(new Dimension(100, 100));
        ShapedButtonUI squareUI = new ShapedButtonUI();
        squareUI.setShape(ButtonShape.SQUARE, squareButton);
        squareButton.setUI(squareUI);
        squareButton.setPreferredSize(new Dimension(100, 100));
        ShapedButtonUI polygonUI = new ShapedButtonUI();
        polygonUI.setShape(ButtonShape.POLYGON, polygonButton);
        polygonButton.setUI(polygonUI);
        polygonButton.setPreferredSize(new Dimension(100, 100));
        // Añadimos nuestros componentes
        JPanel panel = new JPanel();
        panel.add(roundButton);
        panel.add(squareButton);
        panel.add(polygonButton);
        
        return panel;
    }
    
    /**
     * Definimos tres posibles formas para los botones
     */
    private enum ButtonShape {
        ROUND,
        SQUARE,
        POLYGON
    }

    private class ShapedButtonUI extends BasicButtonUI {
        /** Button shape. */
        private ButtonShape shape;
        
        public ShapedButtonUI() {
            super();
        }
        public void setShape(ButtonShape shape, JButton button){
            // no pintamos el borde
            button.setBorderPainted(false);
            this.shape = shape;
        }
        @Override
        public void paint(Graphics g, JComponent c) {
            Graphics2D g2d = (Graphics2D) g;
            // definamos las formas de nuestros botones
            Shape buttonShape = null;
            switch (shape) {
                case ROUND: buttonShape = new RoundRectangle2D.Double(0, 0, c.getWidth() - 1, c.getHeight() - 1, 50, 50);
                break;
                case SQUARE: buttonShape = new Rectangle(0, 0, c.getWidth(), c.getHeight());
                break;
                case POLYGON:
                int[] xPoints = {0, 0 + c.getWidth() / 3, 0 + 2 * (c.getWidth() / 3), c.getWidth(), 0 + 2 * (c.getWidth() / 3), 0 + c.getWidth() / 3};
                int[] yPoints = {c.getHeight() / 2, 0, 0, c.getHeight() / 2, c.getHeight(), c.getHeight()};
                buttonShape = new Polygon(xPoints, yPoints, 6);
                break;
            }
            // establecemos un gradiente para el fondo del boton
            GradientPaint gp = new GradientPaint(0, 0, Color.GREEN, c.getWidth(), c.getHeight(), Color.BLUE);
            g2d.setPaint(gp);
            g2d.fill(buttonShape);
            super.paint(g2d, c);
        }
        @Override
        protected void paintButtonPressed(Graphics g, AbstractButton b) {
            Graphics2D g2d = (Graphics2D) g;
            //definamos las formas de nuestros botones
            Shape buttonShape = null;
            switch (shape) {
                case ROUND: buttonShape = new RoundRectangle2D.Double(0, 0, b.getWidth() - 1, b.getHeight() - 1, 50, 50);
                break;
                case SQUARE: buttonShape = new Rectangle(0, 0, b.getWidth(), b.getHeight());
                break;
                case POLYGON:
                int[] xPoints = {0, 0 + b.getWidth() / 3, 0 + 2 * (b.getWidth() / 3), b.getWidth(), 0 + 2 * (b.getWidth() / 3), 0 + b.getWidth() / 3};
                int[] yPoints = {b.getHeight() / 2, 0, 0, b.getHeight() / 2, b.getHeight(), b.getHeight()};
                buttonShape = new Polygon(xPoints, yPoints, 6);
                break;
            }
            // establecemos un gradiente para el fondo del boton
            GradientPaint gp = new GradientPaint(0, 0, Color.BLUE, 0, b.getHeight(), Color.GREEN);
            g2d.setPaint(gp);
            g2d.fill(buttonShape);
        }
    }
    /**
    * Create the GUI and show it. For thread safety,
    * this method should be invoked from the
    * event dispatch thread.
    */
   private static void createAndShowGUI() {
       // Create and set up the window.
       frame = new JFrame("Shaped button");
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
       ShapedButton app = new ShapedButton();
       Component contents = app.createComponents();
       frame.getContentPane().add(contents, BorderLayout.CENTER);
       // Display the window.
       frame.pack();
       frame.setLocationByPlatform(true);
       frame.setVisible(true);
    }
    public static void main(String[] args) {
        // Schedule a job for the event dispatch thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}