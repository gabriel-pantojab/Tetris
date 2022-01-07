package visual;
import javax.swing.JLabel;
import logic.Chronometer;

/**
 * Write a description of class JLabelChronometer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class JLabelChronometer extends JLabel{
    private Chronometer cronometro;
    
    public JLabelChronometer() {
        super("00:00:00");
        cronometro = new Chronometer(this);
    }
    
    public void setInfo(){
        String m = "";
        String s = "";
        String h = "";
        if(cronometro.getMinutes() < 10) {
            m += "0"+cronometro.getMinutes()+":";
        }else{
            m = cronometro.getMinutes() + ":";
        }
        if(cronometro.getSeconds() < 10) {
            s += "0"+cronometro.getSeconds();
        }else{
            s = ""+cronometro.getSeconds();
        }
        if(cronometro.getHours() < 10) {
            h += "0"+cronometro.getHours()+":";
        }else{
            h = ""+cronometro.getHours();
        }
        setText(h+m+s);
    }
    
    public void stop() {
        setText("00:00:00");
        cronometro.stop();
    }
    
    public void start(){
        cronometro.start();
    }
    
    public void pause() {
        cronometro.pause();
    }
    
    public void resume() {
        cronometro.resume();
    }
    
    public Chronometer getChronometer() {
        return cronometro;
    }
}
