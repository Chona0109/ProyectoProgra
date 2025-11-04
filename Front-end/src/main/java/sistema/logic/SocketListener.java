package sistema.logic;

import logic.Protocol;
import sistema.presentation.ThreadListener;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketListener {
    private static List<ThreadListener> listeners = new ArrayList<>();
    private static SocketListener instance;
    private static boolean isRunning = false;

    String sid;
    Socket as;
    ObjectOutputStream aos;
    ObjectInputStream ais;

    private SocketListener(String sid) throws Exception {
        this.sid = sid;

        as = new Socket(Protocol.SERVER, Protocol.PORT);
        aos = new ObjectOutputStream(as.getOutputStream());
        ais = new ObjectInputStream(as.getInputStream());

        aos.writeInt(Protocol.ASYNC);
        aos.writeObject(sid);
        aos.flush();
    }

    public static synchronized SocketListener getInstance(ThreadListener listener, String sid) throws Exception {
        if (instance == null) {
            instance = new SocketListener(sid);
            System.out.println("SocketListener creado con SID: " + sid);
        }


        if (!listeners.contains(listener)) {
            listeners.add(listener);

        }


        if (!isRunning) {
            instance.start();
        }

        return instance;
    }

    boolean condition = true;
    private Thread t;

    public void start() {
        if (isRunning) {
            System.out.println(" SocketListener ya está corriendo");
            return;
        }

        t = new Thread(new Runnable() {
            public void run() {
                listen();
            }
        });
        condition = true;
        isRunning = true;
        t.start();
        System.out.println(" SocketListener thread iniciado");
    }

    public void stop() {
        condition = false;
        isRunning = false;
        listeners.clear();
        System.out.println(" SocketListener detenido");
    }

    public void listen() {
        int method;
        while (condition) {
            try {
                method = ais.readInt();

                switch (method) {
                    case Protocol.DELIVER_MESSAGE:
                        try {
                            String message = (String) ais.readObject();

                            System.out.println("   Notificando a " + listeners.size() + " listener(s)");


                            for (ThreadListener listener : listeners) {
                                SwingUtilities.invokeLater(() -> {
                                    try {
                                        listener.deliver_message(message);
                                    } catch (Exception e) {
                                        System.err.println(" Error entregando mensaje a " +
                                                listener.getClass().getSimpleName() + ": " + e.getMessage());
                                    }
                                });
                            }
                        } catch (ClassNotFoundException ex) {
                            System.err.println(" Error deserializando mensaje: " + ex.getMessage());
                        }
                        break;
                }
            } catch (IOException ex) {
                if (condition) {
                    System.err.println(" Error en SocketListener: " + ex.getMessage());
                }
                condition = false;
            }
        }
        try {
            as.shutdownOutput();
            as.close();
            System.out.println("✓ Socket cerrado correctamente");
        } catch (IOException e) {
            System.err.println(" Error cerrando socket: " + e.getMessage());
        }
    }

    public static void removeListener(ThreadListener listener) {
        listeners.remove(listener);
        System.out.println(" Listener removido: " + listener.getClass().getSimpleName() +
                " (Quedan: " + listeners.size() + ")");
    }
}