package sistema.presentation.Dashboard;

import sistema.logic.Proxy;
import logic.entities.Receta;
import logic.entities.Medicamento;
import sistema.logic.SocketListener;
import sistema.presentation.ThreadListener;

import javax.swing.*;
import java.time.Month;
import java.util.List;

public class DashboardController implements ThreadListener {

    private DashboardModel model;
    private SocketListener socketListener;

    public DashboardController(DashboardModel model) {
        this.model = model;
        model.init();

        // Carga inicial de recetas
        cargarDatos();

        // Inicia SocketListener para recibir actualizaciones
        try {
            socketListener = new SocketListener(this, Proxy.instance().getSid());
            socketListener.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void stop() {
        if (socketListener != null) socketListener.stop();
    }

    @Override
    public void deliver_message(String message) {
        // Cuando llega un mensaje desde el servidor, recarga las recetas
        new Thread(() -> {
            try {
                List<Receta> recetas = Proxy.instance().search(new Receta());
                SwingUtilities.invokeLater(() -> model.setRecetas(recetas));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        System.out.println("Mensaje recibido en Dashboard: " + message);
    }
}
