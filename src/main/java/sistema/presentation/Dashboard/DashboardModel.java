package sistema.presentation.Dashboard;

import sistema.logic.entities.Receta;
import sistema.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DashboardModel extends AbstractModel {

    private List<Receta> recetas;
    private Map<Month, Integer> medicamentosPorMes;
    private Map<String, Long> recetasPorEstado;

    public static final String RECETAS_POR_MES = "recetasPorMes";
    public static final String RECETAS_POR_ESTADO = "recetasPorEstado";

    public DashboardModel() {}

    public void setRecetas(List<Receta> recetas) {
        this.recetas = recetas;
        actualizarEstadisticas();
    }

    public List<Receta> getRecetas() {
        return recetas;
    }

    private void actualizarEstadisticas() {
        if (recetas == null) return;

        // Medicamentos por mes
        medicamentosPorMes = recetas.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getFechaConfeccion().getMonth(),
                        Collectors.summingInt(r -> r.getMedicamentos().size())
                ));

        // Recetas por estado
        recetasPorEstado = recetas.stream()
                .collect(Collectors.groupingBy(
                        Receta::getEstado,
                        Collectors.counting()
                ));

        firePropertyChange(RECETAS_POR_MES);
        firePropertyChange(RECETAS_POR_ESTADO);
    }

    public Map<Month, Integer> getMedicamentosPorMes() {
        return medicamentosPorMes != null ? medicamentosPorMes : new HashMap<>();
    }

    public Map<String, Long> getRecetasPorEstado() {
        return recetasPorEstado != null ? recetasPorEstado : new HashMap<>();
    }
}