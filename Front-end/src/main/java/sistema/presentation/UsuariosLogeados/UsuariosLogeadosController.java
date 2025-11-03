package sistema.presentation.UsuariosLogeados;

import logic.entities.Usuario;
import sistema.logic.Proxy;
import sistema.logic.SocketListener;
import sistema.presentation.ThreadListener;

import javax.swing.*;
import java.util.List;

public class UsuariosLogeadosController implements ThreadListener {

    private UsuariosLogeadosModel model;
    private SocketListener socketListener;
    private UsuariosLogeadosForm view;

    public UsuariosLogeadosController(UsuariosLogeadosModel model, UsuariosLogeadosForm view) {
        this.model = model;
        this.view = view;
        this.model.init();

        // Cargar usuarios activos inmediatamente
        cargarUsuariosActivos();

        // Iniciar listener para recibir notificaciones
        iniciarSocketListener();
    }

    private void iniciarSocketListener() {
        try {
            socketListener = new SocketListener(this, Proxy.instance().getSid());
            socketListener.start();
            System.out.println("✓ SocketListener iniciado para usuarios activos");
        } catch (Exception e) {
            System.err.println("Error iniciando SocketListener: " + e.getMessage());
        }
    }

    private void cargarUsuariosActivos() {
        new Thread(() -> {
            try {
                System.out.println("→ Cargando usuarios activos...");
                List<Usuario> usuarios = Proxy.instance().getUsuariosActivos();
                System.out.println("✓ Usuarios activos cargados: " + usuarios.size());

                SwingUtilities.invokeLater(() -> {
                    model.setList(usuarios);
                });
            } catch (Exception e) {
                System.err.println("Error cargando usuarios activos: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }

    public void enviarMensaje(String destinatario, String mensaje) {
        try {
            Proxy.instance().sendUserMessage(destinatario, mensaje);
            System.out.println("✓ Mensaje enviado a: " + destinatario);
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
        System.out.println("→ Notificación recibida: " + message);

        // Recargar lista de usuarios activos
        cargarUsuariosActivos();

        // Si es un mensaje de usuario, mostrarlo
        if (view != null && !message.startsWith("USER_ONLINE") && !message.startsWith("USER_OFFLINE")) {
            SwingUtilities.invokeLater(() -> {
                view.mostrarMensaje(message);
            });
        }
    }

    public void stop() {
        if (socketListener != null) {
            socketListener.stop();
        }
    }
}