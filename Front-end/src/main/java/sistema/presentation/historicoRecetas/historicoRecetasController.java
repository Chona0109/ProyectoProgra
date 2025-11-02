package sistema.presentation.historicoRecetas;

import logic.entities.Receta;
import sistema.logic.Proxy;
import sistema.logic.SocketListener;
import sistema.presentation.ThreadListener;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class historicoRecetasController implements ThreadListener {

    private historicoRecetasModel model;
    private SocketListener socketListener;

    public historicoRecetasController(historicoRecetasModel model) {
        this.model = model;
        model.init();

        try {
            socketListener = new SocketListener(this, Proxy.instance().getSid());
            socketListener.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Carga inicial
        actualizar();
    }

    // ==================== SOCKET LISTENER ====================
    @Override
    public void deliver_message(String message) {
        System.out.println("Mensaje recibido: " + message);
        actualizar();
    }

    // ==================== MÉTODOS ====================
    public void actualizar() {
        new Thread(() -> {
            try {
                List<Receta> recetas = Proxy.instance().search(new Receta());
                model.setList(recetas);
                model.setCurrent(new Receta());
            } catch (Exception e) {
                System.err.println("Error cargando recetas: " + e.getMessage());
            }
        }).start();
    }

    public void buscarPorId(String idPaciente) {
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

    public String generarDetallesDe(Receta receta) {
        return Proxy.instance().generarDetallesReceta(receta);
    }

    public void setCurrent(Receta receta) {
        model.setCurrent(receta);
        model.setMode(historicoRecetasModel.MODE_EDIT);
    }

    public void clear() {
        model.setCurrent(new Receta());
        model.setMode(historicoRecetasModel.MODE_CREATE);
    }
}
