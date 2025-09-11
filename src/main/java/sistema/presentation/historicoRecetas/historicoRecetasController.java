package sistema.presentation.historicoRecetas;

import sistema.logic.Service;
import sistema.logic.entities.Receta;

import java.util.ArrayList;
import java.util.List;

public class historicoRecetasController {
    private historicoRecetasModel model;

    public historicoRecetasController(historicoRecetasModel model) {
        this.model = model;
    }

    public void refresh() {
        List<Receta> recetas = Service.instance().findAllRecetas();
        model.setList(recetas);
        model.setCurrent(null);
    }

    public void buscarPorId(String id) {
        if (id == null || id.trim().isEmpty()) {
            refresh();
            return;
        }

        List<Receta> recetasPorId = Service.instance().searchRecetaById(id);
        List<Receta> recetasPorPaciente = Service.instance().searchRecetaByIdPaciente(id);

        List<Receta> resultado = new ArrayList<>(recetasPorId);
        for (Receta r : recetasPorPaciente) {
            if (!resultado.contains(r)) {
                resultado.add(r);
            }
        }

        model.setList(resultado);
        model.setCurrent(null);
    }

    public String generarDetallesDe(Receta receta) {
        return Service.instance().generarDetallesReceta(receta);
    }

}
