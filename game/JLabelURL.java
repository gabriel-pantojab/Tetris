package game;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import javax.swing.JFrame;
import java.util.Map;
import java.util.HashMap;
import java.awt.font.*;
import javax.swing.JPanel;

/**
 * Write a description of class JLabelURL here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class JLabelURL extends JPanel{
    private Desktop defaultDesktop;
    private URI url;
    private JLabel labelUrl;
    private JLabel tag;
    
    public JLabelURL(String url) {
        setLayout(new FlowLayout());
        setBackground(Color.WHITE);
        labelUrl = new JLabel(url);
        labelUrl.setForeground(Color.BLUE);
        labelUrl.setCursor(new Cursor(Cursor.HAND_CURSOR));
        labelUrl.addMouseListener(new Manager());
        try{
            this.url = new URI(url);
            defaultDesktop = Desktop.getDesktop();
        }catch(Exception ex) {}
        paintLabel();
        add(labelUrl);
    }
    
    public JLabelURL(String tag, String url) {
        this(url);
        this.tag = new JLabel(tag);
        remove(labelUrl);
        add(this.tag);
        add(labelUrl);
    }
    
    private void paintLabel() {
        Font font = labelUrl.getFont();
        Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>(font.getAttributes());
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        labelUrl.setFont(font.deriveFont(attributes));
    }
    
    private class Manager extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            try{
                defaultDesktop.browse(url);
            }catch(Exception ex) {}
        }
        
        public void mouseEntered(MouseEvent e) {
            labelUrl.setForeground(new Color(163, 73, 164));
        }
        
        public void mouseExited(MouseEvent e) {
            labelUrl.setForeground(Color.BLUE);
        }
    }
    
    public static void main() {
        JFrame f = new JFrame();
        f.setLayout(new FlowLayout());
        f.setBounds(0, 0, 200, 200);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new JLabelURL("Git: ", "https://docs.oracle.com/javase/8/docs/api/"));
        f.setVisible(true);
    }
}
