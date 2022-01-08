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
    private String text;
    
    public JLabelChronometer(String text) {
        super(text+"00:00:00");
        this.text = text;
        cronometro = new Chronometer();
        cronometro.setLabel(this);
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
        setText(text+h+m+s);
    }
    
    public void stop() {
        setText(text+"00:00:00");
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
    
    public void restart(){
        cronometro.restart();
    }
    
    public Chronometer getChronometer() {
        return cronometro;
    }
    
    public void run(){}
}
