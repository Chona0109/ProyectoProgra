package sistema.presentation.Dashboard;

import sistema.logic.Service;

public class DashboardController {

    private DashboardModel model;

    public DashboardController(DashboardModel model) {
        this.model = model;
        cargarDatos();
    }

    public void cargarDatos() {
        try {
            model.setRecetas(Service.instance().findAllRecetas());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}