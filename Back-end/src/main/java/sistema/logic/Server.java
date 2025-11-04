package sistema.logic;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import logic.Protocol;
import logic.entities.Usuario;

public class Server {
    ServerSocket ss;
    List<Worker> workers;
    Service service;

    public Server() {
        try {
            ss = new ServerSocket(Protocol.PORT);
            workers = Collections.synchronizedList(new ArrayList<Worker>());
            service = Service.getInstance();
            System.out.println("===========================================");
            System.out.println("Servidor Hospital iniciado correctamente");
            System.out.println("Puerto: " + Protocol.PORT);
            System.out.println("Esperando conexiones...");
            System.out.println("===========================================");
        } catch (IOException ex) {
            System.err.println("ERROR CRÍTICO: No se pudo iniciar el servidor");
            System.err.println("Causa: " + ex.getMessage());
            System.exit(-1);
        }
    }

    public void run() {
        boolean continuar = true;
        Socket s;
        Worker worker;
        String sid;
        while (continuar) {
            try {
                s = ss.accept();
                System.out.println("\n>>> Nueva conexión establecida desde: " +
                        s.getInetAddress().getHostAddress());
                ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
                ObjectInputStream is = new ObjectInputStream(s.getInputStream());
                int type = is.readInt();

                switch (type) {
                    case Protocol.SYNC:
                        sid=s.getRemoteSocketAddress().toString();
                        System.out.println("SYNCH: "+sid);
                        worker = new Worker(this, s, os, is, sid, Service.getInstance());
                        workers.add(worker);
                        System.out.println("Quedan: " + workers.size());
                        worker.start();
                        os.writeObject(sid);
                        break;

                    case Protocol.ASYNC:
                        sid=(String)is.readObject();
                        System.out.println("ASYNCH: "+sid);
                        join(s,os,is,sid);
                        break;
                }

                os.flush();

            } catch (IOException | ClassNotFoundException ex) {
                System.err.println("Error: " + ex.getMessage());
            }
        }
    }



    public void remove(Worker w) {

        if (w.getUsuarioId() != null && !w.getUsuarioId().isEmpty()) {
            notifyUserOffline(w.getUsuarioId());


            try {
                Usuario u = new Usuario();
                u.setId(w.getUsuarioId());
                service.logout(u);
                System.out.println(" Usuario removido del Service: " + w.getUsuarioId());
            } catch (Exception e) {
                System.err.println("Error al remover usuario del service: " + e.getMessage());
            }
        }

        workers.remove(w);
        System.out.println("<<< Cliente desconectado. Quedan: " + workers.size() + " clientes");
    }

    public void stop() {
        try {
            if (ss != null && !ss.isClosed()) {
                ss.close();
                System.out.println("Servidor detenido correctamente");
            }
        } catch (IOException e) {
            System.err.println("Error cerrando servidor: " + e.getMessage());
        }
    }

    public void join(Socket as, ObjectOutputStream aos, ObjectInputStream ais, String sid) {
        for (Worker w : workers) {
            if (w.sid.equals(sid)) {
                w.setAs(as, aos, ais);
                break;
            }
        }
    }
    public void deliver_message(Worker from, String message) {
        for (Worker w : workers) {
            if (w != from) w.deliver_message(message);
        }
    }


    public void notifyUserOnline(String userId) {
        String message = "USER_ONLINE:" + userId;

        for (Worker w : workers) {

            if (w != null && w.getUsuarioId() != null && !userId.equals(w.getUsuarioId())) {
                w.deliver_message(message);
            }
        }

        System.out.println("→ Notificado: Usuario " + userId + " online");
    }


    public void notifyUserOffline(String userId) {
        if (userId == null || userId.isEmpty()) return;

        String message = "USER_OFFLINE:" + userId;

        for (Worker w : workers) {
            if (w != null && w.getUsuarioId() != null && !userId.equals(w.getUsuarioId())) {
                w.deliver_message(message);
            }
        }

        System.out.println("→ Notificado: Usuario " + userId + " offline");
    }


    public List<String> getOnlineUserIds() {
        List<String> onlineIds = new ArrayList<>();
        for (Worker w : workers) {
            if (w != null && w.getUsuarioId() != null) {
                onlineIds.add(w.getUsuarioId());
            }
        }
        return onlineIds;
    }

    public Worker getWorkerByUsuarioId(String destinatario) {
        synchronized (workers) {
            for (Worker w : workers) {
                if (w != null && destinatario.equals(w.getUsuarioId())) {
                    return w;
                }
            }
        }
        return null;
    }
}