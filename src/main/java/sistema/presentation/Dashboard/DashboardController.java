package sistema.presentation.Dashboard;

import sistema.logic.Service;
import sistema.logic.entities.Receta;
import sistema.logic.entities.Medicamento;

import java.time.Month;
import java.util.List;

public class DashboardController {

    private DashboardModel model;

    public DashboardController(DashboardModel model) {
        this.model = model;
        cargarDatos();
    }

    public void cargarDatos() {
        try {
            List<Receta> recetas = Service.instance().findAllRecetas();
            model.setRecetas(recetas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void aplicarFiltros(String medicamentoNombre, Month mesInicio, Month mesFin) {
        model.setFiltros(medicamentoNombre, mesInicio, mesFin);
    }

    public List<Medicamento> obtenerMedicamentos() {
        try {
            return Service.instance().findAllMedicamentos();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}