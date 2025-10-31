package sistema.presentation.historicoRecetas;

import logic.entities.Farmaceutico;
import sistema.logic.Proxy;
import logic.entities.Receta;
import sistema.presentation.ThhreadListener;
import java.util.ArrayList;
import java.util.List;

public class historicoRecetasController implements ThhreadListener {
    private historicoRecetasModel model;

    public historicoRecetasController(historicoRecetasModel model) {
        this.model = model;
    }

    public void actualizar() {
        List<Receta> recetas = Proxy.instance().search(new Receta());
        model.setList(recetas);
        model.setCurrent(null);
    }

    public void buscarPorId(String id) {
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
    }


    public String generarDetallesDe(Receta receta) {
        return Proxy.instance().generarDetallesReceta(receta);
    }

    @Override
    public void refresh() {
        try {
            model.setList(Proxy.instance().search(new Receta()));
        } catch (Exception e) {}
    }
}



