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
        this.view = view;  // Guardamos la referencia
        this.model.init();
        cargarUsuariosActivos();
    }

    private void cargarUsuariosActivos() {
        new Thread(() -> {
            try {
                // Llamada al Proxy para obtener la lista de usuarios activos
                List<Usuario> usuarios = Proxy.instance().getUsuariosActivos();

                // Actualizar el TableModel en el hilo de la GUI
                SwingUtilities.invokeLater(() -> {
                    model.setList(usuarios); // model es tu TableModel
                });

            } catch (Exception e) {
                System.err.println("Error cargando usuarios activos: " + e.getMessage());
            }
        }).start();
    }

    public void enviarMensaje(String destinatario, String mensaje) {
        try {
            Proxy.instance().sendUserMessage(destinatario, mensaje);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void recibirMensajes() {
        try {
            if (socketListener == null) {
                socketListener = new SocketListener(this, Proxy.instance().getSid());
                socketListener.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deliver_message(String message) {
            cargarUsuariosActivos();
        System.out.println("Notificación recibida  " + message);
    }
}