package sistema.presentation.UsuariosLogeados;

import logic.entities.Usuario;
import sistema.logic.Proxy;
import sistema.logic.SocketListener;
import sistema.presentation.ThreadListener;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class UsuariosLogeadosController implements ThreadListener {

    private UsuariosLogeadosModel model;
    private SocketListener socketListener;
    private UsuariosLogeadosForm view;


    private final AtomicBoolean cargando = new AtomicBoolean(false);

    public UsuariosLogeadosController(UsuariosLogeadosModel model, UsuariosLogeadosForm view) {
        this.model = model;
        this.view = view;
        this.model.init();


        cargarUsuariosActivos();


        iniciarSocketListener();
    }

    private void iniciarSocketListener() {
        try {
            socketListener = new SocketListener(this, Proxy.instance().getSid());
            socketListener.start();
            System.out.println("SocketListener iniciado para usuarios activos");
        } catch (Exception e) {
            System.err.println("Error iniciando SocketListener: " + e.getMessage());
        }
    }

    private void cargarUsuariosActivos() {

        if (!cargando.compareAndSet(false, true)) {
            System.out.println("Ya hay una carga en progreso, omitiendo...");
            return;
        }

        new Thread(() -> {
            try {
                System.out.println(" Cargando usuarios activos...");


                Thread.sleep(100);

                List<Usuario> usuarios = Proxy.instance().getUsuariosActivos();
                System.out.println("Usuarios activos cargados: " + usuarios.size());

                SwingUtilities.invokeLater(() -> {
                    model.setList(usuarios);
                });
            } catch (Exception e) {
                System.err.println("Error cargando usuarios activos: " + e.getMessage());
                e.printStackTrace();


                SwingUtilities.invokeLater(() -> {
                    model.setList(new java.util.ArrayList<>());
                });
            } finally {
                cargando.set(false);
            }
        }).start();
    }

    public void enviarMensaje(String destinatario, String mensaje) {
        try {
            Proxy.instance().sendUserMessage(destinatario, mensaje);
            System.out.println("Mensaje enviado a: " + destinatario);
        } catch (Exception e) {
            System.err.println("Error enviando mensaje: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void recibirMensajes() {
        if (socketListener == null) {
            iniciarSocketListener();
        }
    }

    @Override
    public void deliver_message(String message) {
        System.out.println("Notificación recibida: " + message);


        if (message.startsWith("USER_ONLINE:") || message.startsWith("USER_OFFLINE:")) {

            String[] partes = message.split(":");
            if (partes.length > 1) {
                String userId = partes[1];
                String accion = message.startsWith("USER_ONLINE:") ? "conectó" : "desconectó";


//                if (view != null) {
//                    SwingUtilities.invokeLater(() -> {
//                        view.mostrarMensaje("Usuario " + userId + " se " + accion);
//                    });
//                }
            }


            new Thread(() -> {
                try {
                    Thread.sleep(200); // Delay de 200ms
                    cargarUsuariosActivos();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();

        } else if (message.startsWith("De ")) {

            if (view != null) {
                SwingUtilities.invokeLater(() -> {
                    view.mostrarMensaje(message);
                });
            }
        } else {

            if (view != null) {

            }
        }
    }

    public void stop() {
        if (socketListener != null) {
            socketListener.stop();
        }
    }
}