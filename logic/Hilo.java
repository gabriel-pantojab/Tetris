package logic;


public class Hilo extends Thread{
    protected boolean end, suspend;
    
    public Hilo(Runnable object) {
        super(object);
        end = suspend = false;
    }
    
    public Hilo(){
        super();
        end = suspend = false;
    }
    
    public synchronized void newSuspend(){
        suspend = true;
    }
    
    public synchronized void newResume(){
        suspend = false;
        notify();
    }
    
    public synchronized void newStop(){
        suspend = false;
        end     = true;
        notify();
    }
}
