package sistema.presentation.historicoRecetas;

import sistema.logic.Service;
import sistema.logic.entities.Receta;

import java.util.List;

public class historicoRecetasController {

    private historicoRecetasModel model;

    public historicoRecetasController(historicoRecetasModel model) {
        this.model = model;
        refresh();
    }

    public void refresh() {
        List<Receta> recetas = Service.instance().findAllRecetas();
        model.setList(recetas);
    }

    public void loadAllRecetas() {
        refresh();
    }

    public void searchById(String id) throws Exception {
        Receta receta = Service.instance().findRecetaById(id);
        model.setList(List.of(receta));
        model.setCurrent(receta);
    }

    public void select(Receta receta) {
        model.setCurrent(receta);
    }

    public historicoRecetasModel getModel() {
        return model;
    }
}
