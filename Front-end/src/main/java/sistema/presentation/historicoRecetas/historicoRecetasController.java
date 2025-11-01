package sistema.presentation.historicoRecetas;

import logic.entities.Receta;
import sistema.logic.Proxy;
import sistema.presentation.ThhreadListener;
import sistema.presentation.Refresher;

import java.util.ArrayList;
import java.util.List;

public class historicoRecetasController implements ThhreadListener {

    private historicoRecetasModel model;
    private Refresher refresher;

    public historicoRecetasController(historicoRecetasModel model) {
        this.model = model;


        refresher = new Refresher(this);
        refresher.start();


        actualizar();
    }

    public void actualizar() {
        new Thread(() -> {
            try {
                List<Receta> recetas = Proxy.instance().search(new Receta());
                model.setList(recetas);
                model.setCurrent(null);
            } catch (Exception e) {
                System.err.println("Error cargando recetas: " + e.getMessage());
            }
        }).start();
    }

    public void buscarPorId(String id) {
        new Thread(() -> {
            try {
                if (id == null || id.trim().isEmpty()) {
                    refresh();
                    return;
                }

                List<Receta> recetasPorId = Proxy.instance().searchRecetaListById(id);
                List<Receta> recetasPorPaciente = Proxy.instance().searchRecetaByIdPaciente(id);

                List<Receta> resultado = new ArrayList<>(recetasPorId);
                for (Receta r : recetasPorPaciente) {
                    if (!resultado.contains(r)) {
                        resultado.add(r);
                    }
                }

                model.setList(resultado);
                model.setCurrent(null);
            } catch (Exception e) {
                System.err.println("Error buscando recetas: " + e.getMessage());
            }
        }).start();
    }

    public String generarDetallesDe(Receta receta) {
        return Proxy.instance().generarDetallesReceta(receta);
    }

    @Override
    public void refresh() {
        actualizar();
    }
}