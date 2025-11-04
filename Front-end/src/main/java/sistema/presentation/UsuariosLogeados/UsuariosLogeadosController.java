package sistema.presentation.UsuariosLogeados;

import logic.entities.Usuario;
import sistema.logic.Proxy;
import sistema.logic.SocketListener;
import sistema.presentation.ThreadListener;

import javax.swing.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class UsuariosLogeadosController implements ThreadListener {

    private UsuariosLogeadosModel model;
    private SocketListener socketListener;
    private UsuariosLogeadosForm view;

    private final AtomicBoolean cargando = new AtomicBoolean(false);
    private final Map<String, List<String>> mensajesPendientes = new ConcurrentHashMap<>();

    public UsuariosLogeadosController(UsuariosLogeadosModel model, UsuariosLogeadosForm view) {
        this.model = model;
        this.view = view;
        this.model.init();

        cargarUsuariosActivos();

        // ✅ Registrar en el SocketListener compartido
        iniciarSocketListener();
    }

    private void iniciarSocketListener() {
        try {
            socketListener = SocketListener.getInstance(this, Proxy.instance().getSid());
            System.out.println("✓ UsuariosLogeadosController registrado en SocketListener");
        } catch (Exception e) {
            System.err.println("❌ Error registrando SocketListener: " + e.getMessage());
        }
    }

    private void cargarUsuariosActivos() {
        if (!cargando.compareAndSet(false, true)) {
            return;
        }

        new Thread(() -> {
            try {
                Thread.sleep(100);
                List<Usuario> usuarios = Proxy.instance().getUsuariosActivos();

                SwingUtilities.invokeLater(() -> {
                    model.setList(usuarios);
                });
            } catch (Exception e) {
                System.err.println("❌ Error cargando usuarios activos: " + e.getMessage());
                SwingUtilities.invokeLater(() -> {
                    model.setList(new ArrayList<>());
                });
            } finally {
                cargando.set(false);
            }
        }).start();
    }

    public void enviarMensaje(String destinatario, String mensaje) {
        try {
            Proxy.instance().sendUserMessage(destinatario, mensaje);
            System.out.println("✓ Mensaje enviado a: " + destinatario);
        } catch (Exception e) {
            System.err.println("❌ Error enviando mensaje: " + e.getMessage());
        }
    }

    public List<String> obtenerMensajesDe(String usuarioId) {
        List<String> mensajes = mensajesPendientes.get(usuarioId);

        if (mensajes == null || mensajes.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> copia = new ArrayList<>(mensajes);
        mensajes.clear();

        return copia;
    }

    public int contarMensajesDe(String usuarioId) {
        List<String> mensajes = mensajesPendientes.get(usuarioId);
        return mensajes == null ? 0 : mensajes.size();
    }

    public Map<String, Integer> obtenerConteoMensajes() {
        Map<String, Integer> conteo = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : mensajesPendientes.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                conteo.put(entry.getKey(), entry.getValue().size());
            }
        }
        return conteo;
    }

    @Override
    public void deliver_message(String message) {
        System.out.println("🔔 [USUARIOS CONTROLLER] Mensaje recibido: " + message);

        if (message.startsWith("USER_ONLINE:") || message.startsWith("USER_OFFLINE:")) {
            String[] partes = message.split(":");
            if (partes.length > 1) {
                String userId = partes[1];
                String accion = message.startsWith("USER_ONLINE:") ? "conectó" : "desconectó";

                System.out.println("👤 Usuario " + userId + " se " + accion);

                if (message.startsWith("USER_OFFLINE:")) {
                    mensajesPendientes.remove(userId);
                }
            }

            new Thread(() -> {
                try {
                    Thread.sleep(200);
                    cargarUsuariosActivos();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();

        } else if (message.startsWith("De ")) {
            try {
                String[] partes = message.split(":", 2);
                if (partes.length >= 2) {
                    String emisor = partes[0].substring(3).trim();
                    String contenido = partes[1].trim();

                    mensajesPendientes.computeIfAbsent(emisor, k -> new ArrayList<>())
                            .add(contenido);

                    System.out.println("📬 Mensaje de " + emisor + " guardado en cola");

                    SwingUtilities.invokeLater(() -> {
                        view.actualizarIndicadores();
                    });
                }
            } catch (Exception e) {
                System.err.println("❌ Error procesando mensaje: " + e.getMessage());
            }
        }
    }

    public void stop() {
        if (socketListener != null) {
            SocketListener.removeListener(this);
            System.out.println("✓ UsuariosLogeadosController desregistrado del SocketListener");
        }
    }
}