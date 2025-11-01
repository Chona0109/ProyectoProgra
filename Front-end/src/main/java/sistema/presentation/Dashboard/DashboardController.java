package sistema.presentation.Dashboard;

import sistema.logic.Proxy;
import logic.entities.Receta;
import logic.entities.Medicamento;
import java.time.Month;
import java.util.List;
import sistema.presentation.ThhreadListener;
import sistema.presentation.Refresher;

import javax.swing.*;

public class DashboardController implements ThhreadListener {

    private DashboardModel model;
    private Refresher refresher;

    public DashboardController(DashboardModel model) {
        this.model = model;


        refresher = new Refresher(this);
        refresher.start();


        cargarDatos();
    }

    public void cargarDatos() {
        new Thread(() -> {
            try {
                List<Receta> recetas = Proxy.instance().search(new Receta());
                SwingUtilities.invokeLater(() -> model.setRecetas(recetas));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
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

        cargarDatos();
    }
}