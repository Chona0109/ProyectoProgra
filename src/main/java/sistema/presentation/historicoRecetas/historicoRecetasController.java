package sistema.presentation.historicoRecetas;

import sistema.logic.Service;
import sistema.logic.entities.Receta;
import java.util.List;

public class historicoRecetasController {
    private historicoRecetasModel model;

    public historicoRecetasController(historicoRecetasModel model) {
        this.model = model;
    }

    public void refresh() throws Exception {
        List<Receta> recetas = Service.instance().findAllRecetas();
        model.setCurrentList(recetas);
    }

    public void searchById(String id) throws Exception {
        Receta receta = Service.instance().findRecetaById(id);
        model.setCurrent(receta);
        // También actualizar la lista para mostrar solo esa receta
        List<Receta> singleReceta = List.of(receta);
        model.setCurrentList(singleReceta);
    }

    public void buscarPorId(String id) {
        // Usar el nuevo método del Service
        List<Receta> recetas = Service.instance().searchRecetaById(id);
        model.setCurrentList(recetas);

        // Limpiar current cuando se hace una búsqueda nueva
        model.setCurrent(new Receta());
    }
}