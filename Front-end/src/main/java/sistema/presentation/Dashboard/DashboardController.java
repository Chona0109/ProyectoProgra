package sistema.presentation.Dashboard;

import sistema.logic.Proxy;
import logic.entities.Receta;
import logic.entities.Medicamento;

import java.time.Month;
import java.util.List;
import sistema.presentation.ThhreadListener;

public class DashboardController implements ThhreadListener {

    private DashboardModel model;

    public DashboardController(DashboardModel model) {
        this.model = model;
        cargarDatos();

    }

    public void cargarDatos() {
        try {
            List<Receta> recetas = Proxy.instance().search(new Receta());
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
            return Proxy.instance().search(new Medicamento());
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
    @Override
    public void refresh() {
        try {
            model.setRecetas(Proxy.instance().search(new Receta()));

        } catch (Exception e) {}
    }
}