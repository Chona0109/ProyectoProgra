package sistema.presentation;

import javax.swing.*;
import sistema.presentation.ThhreadListener;

public class Refresher {
    ThhreadListener listener;

    public Refresher(ThhreadListener listener) {
        this.listener = listener;
    }

    private Thread hilo;
    private boolean condition=false;
    public void start(){
        Runnable task = new Runnable(){
            public void run(){
                while(condition){
                    try { Thread.sleep(2000);}
                    catch (InterruptedException ex) {}
                    refresh();
                }
            }
        };
        hilo = new Thread(task);
        condition=true;
        hilo.start();
    }
    public void stop(){
        condition=false;
    }
    long c=0;
    private void refresh(){
        System.out.println(c++);
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        listener.refresh();
                    }
                }
        );
    }
}
