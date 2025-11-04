package sistema.presentation.Despacho;

import sistema.logic.Proxy;
import logic.entities.Receta;
import sistema.logic.SocketListener;
import sistema.presentation.ThreadListener;

import javax.swing.*;
import java.util.List;

public class DespachoController implements ThreadListener {

    private DespachoModel model;
    private SocketListener socketListener;
    public DespachoController(DespachoModel model) {
        this.model = model;
        this.model.init();
        try {
         socketListener = SocketListener.getInstance(this, Proxy.instance().getSid());
        socketListener.start();
        }catch(Exception e){

        }
        cargarRecetas();
    }

    private void cargarRecetas() {
        new Thread(() -> {
            try {
                List<Receta> recetas = Proxy.instance().search(new Receta());
                SwingUtilities.invokeLater(() -> model.setList(recetas));
            } catch (Exception e) {
                System.err.println("Error cargando recetas: " + e.getMessage());
            }
        }).start();
    }

    public void buscarPorIdPaciente(String idPaciente) {
        new Thread(() -> {
            try {
                List<Receta> resultados;
                if (idPaciente == null || idPaciente.isEmpty()) {
                    resultados = Proxy.instance().search(new Receta());
                } else {
                    resultados = Proxy.instance().searchRecetaByIdPaciente(idPaciente);
                }
                SwingUtilities.invokeLater(() -> {
                    model.setList(resultados);
                    model.setCurrent(new Receta());
                });
            } catch (Exception e) {
                System.err.println("Error buscando recetas: " + e.getMessage());
            }
        }).start();
    }

    public void avanzarEstado(Receta receta) {
        new Thread(() -> {
            try {
                Proxy.instance().avanzarEstado(receta);
                List<Receta> recetas = Proxy.instance().search(new Receta());
                SwingUtilities.invokeLater(() -> model.setList(recetas));
            } catch (Exception e) {
                System.err.println("Error avanzando estado: " + e.getMessage());
            }
        }).start();
    }

    public void clear() {
        model.setCurrent(new Receta());
        cargarRecetas();
    }

    public String generarDetallesDe(Receta receta) {
        return Proxy.instance().generarDetallesReceta(receta);
    }

    @Override
    public void deliver_message(String message) {

        cargarRecetas();
        System.out.println("Mensaje recibido: " + message);
    }
    public void stop() {
        if (socketListener != null) socketListener.stop();
    }
}
