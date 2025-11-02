package sistema.presentation.Despacho;

import sistema.logic.Proxy;
import logic.entities.Receta;
import sistema.presentation.Refresher;
import sistema.presentation.ThhreadListener;

import javax.swing.*;
import java.util.List;

public class DespachoController implements ThhreadListener {
    private DespachoModel model;
    private Refresher refresher;

    public DespachoController(DespachoModel model) {
        this.model = model;
        this.model.setCurrent(new Receta());

        // Inicia refresher
        refresher = new Refresher(this);
        refresher.start();

        // Carga inicial de datos
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
        if (idPaciente == null || idPaciente.isEmpty()) {
            model.setList(Proxy.instance().search(new Receta()));
        } else {
            model.setList(Proxy.instance().searchRecetaByIdPaciente(idPaciente));
        }

        model.setCurrent(new Receta());
    }

    public void avanzarEstado(Receta receta) throws Exception {
        Proxy.instance().avanzarEstado(receta);
        model.setList(Proxy.instance().search(new Receta()));
    }

    public void clear() {
        model.setCurrent(new Receta());
        model.setList(Proxy.instance().search(new Receta()));
    }


    public String generarDetallesDe(Receta receta) {
        return Proxy.instance().generarDetallesReceta(receta);
    }

    @Override
    public void refresh() {
        cargarRecetas();
    }



}