package sistema.presentation.historicoRecetas;

import sistema.logic.Service;
import sistema.logic.entities.Receta;

import java.util.List;

public class historicoRecetasController {

    private historicoRecetasModel model;

    public historicoRecetasController(historicoRecetasModel model) {
        this.model = model;
        refresh(); // cargar todas las recetas al iniciar
    }

    // Refrescar todas las recetas
    public void refresh() {
        List<Receta> recetas = Service.instance().findAllRecetas();
        model.setList(recetas);
    }

    // Cargar todas las recetas (alias de refresh)
    public void loadAllRecetas() {
        refresh();
    }

    // Buscar receta por ID
    public void searchById(String id) throws Exception {
        Receta receta = Service.instance().findRecetaById(id); // llamamos al método del Service
        model.setList(List.of(receta)); // mostrar solo la receta encontrada
        model.setCurrent(receta);
    }

    // Seleccionar receta de la lista (por ejemplo desde la tabla)
    public void select(Receta receta) {
        model.setCurrent(receta);
    }

    public historicoRecetasModel getModel() {
        return model;
    }
}
