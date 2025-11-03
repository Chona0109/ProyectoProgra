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

    // ✅ Control de solicitudes concurrentes
    private final AtomicBoolean cargando = new AtomicBoolean(false);

    // ✅ NUEVO: Cola de mensajes por usuario
    // Map<UsuarioID, Lista de mensajes>
    private final Map<String, List<String>> mensajesPendientes = new ConcurrentHashMap<>();

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
        // Evitar múltiples solicitudes simultáneas
        if (!cargando.compareAndSet(false, true)) {
            System.out.println("⚠️ Ya hay una carga en progreso, omitiendo...");
            return;
        }

        new Thread(() -> {
            try {
                System.out.println("→ Cargando usuarios activos...");

                // Pequeño delay para evitar colisiones
                Thread.sleep(100);

                List<Usuario> usuarios = Proxy.instance().getUsuariosActivos();
                System.out.println("✓ Usuarios activos cargados: " + usuarios.size());

                SwingUtilities.invokeLater(() -> {
                    model.setList(usuarios);
                });
            } catch (Exception e) {
                System.err.println("Error cargando usuarios activos: " + e.getMessage());
                e.printStackTrace();

                // En caso de error, establecer lista vacía
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
            System.err.println("Error enviando mensaje: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ✅ NUEVO: Obtener mensajes de un usuario específico
    public List<String> obtenerMensajesDe(String usuarioId) {
        List<String> mensajes = mensajesPendientes.get(usuarioId);

        if (mensajes == null || mensajes.isEmpty()) {
            return new ArrayList<>();
        }

        // Retornar copia y limpiar
        List<String> copia = new ArrayList<>(mensajes);
        mensajes.clear();

        return copia;
    }

    // ✅ NUEVO: Verificar si hay mensajes pendientes de un usuario
    public int contarMensajesDe(String usuarioId) {
        List<String> mensajes = mensajesPendientes.get(usuarioId);
        return mensajes == null ? 0 : mensajes.size();
    }

    // ✅ NUEVO: Obtener todos los usuarios con mensajes pendientes
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
        System.out.println("→ Notificación recibida: " + message);

        // Distinguir entre notificaciones de sistema y mensajes de usuario
        if (message.startsWith("USER_ONLINE:") || message.startsWith("USER_OFFLINE:")) {
            // Es una notificación de conexión/desconexión
            String[] partes = message.split(":");
            if (partes.length > 1) {
                String userId = partes[1];
                String accion = message.startsWith("USER_ONLINE:") ? "conectó" : "desconectó";

                System.out.println("🔔 Usuario " + userId + " se " + accion);

                // ✅ Si se desconectó, limpiar sus mensajes pendientes
                if (message.startsWith("USER_OFFLINE:")) {
                    mensajesPendientes.remove(userId);
                }
            }

            // Esperar un momento para que el servidor termine de procesar
            new Thread(() -> {
                try {
                    Thread.sleep(200);
                    cargarUsuariosActivos();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();

        } else if (message.startsWith("De ")) {
            // ✅ Es un mensaje de usuario (formato: "De USERID: mensaje")
            try {
                // Extraer el ID del usuario emisor
                String[] partes = message.split(":", 2);
                if (partes.length >= 2) {
                    String emisor = partes[0].substring(3).trim(); // Quitar "De "
                    String contenido = partes[1].trim();

                    // ✅ Almacenar en la cola de mensajes pendientes
                    mensajesPendientes.computeIfAbsent(emisor, k -> new ArrayList<>())
                            .add(contenido);

                    System.out.println("📬 Mensaje de " + emisor + " guardado en cola");

                    // ✅ Actualizar vista para mostrar indicador
                    SwingUtilities.invokeLater(() -> {
                        view.actualizarIndicadores();
                    });
                }
            } catch (Exception e) {
                System.err.println("Error procesando mensaje: " + e.getMessage());
            }
        }
    }

    public void stop() {
        if (socketListener != null) {
            socketListener.stop();
        }
    }
}