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

    private String medicamentoFiltro;
    private Month mesInicioFiltro;
    private Month mesFinFiltro;

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

    public void setFiltros(String medicamento, Month mesInicio, Month mesFin) {
        this.medicamentoFiltro = medicamento;
        this.mesInicioFiltro = mesInicio;
        this.mesFinFiltro = mesFin;
        actualizarEstadisticas();
    }

    private void actualizarEstadisticas() {
        if (recetas == null) return;

        List<Receta> recetasFiltradas = recetas.stream()
                .filter(r -> r.getMedicamentos() != null &&
                        (medicamentoFiltro == null || medicamentoFiltro.isEmpty() ||
                                r.getMedicamentos().stream()
                                        .anyMatch(m -> m.getMedicamento() != null &&
                                                m.getMedicamento().getNombre().equals(medicamentoFiltro))))
                .filter(r -> {
                    if (r.getFechaConfeccion() == null) return false;
                    int mesValue = r.getFechaConfeccion().getMonth().getValue();
                    if (mesInicioFiltro != null && mesValue < mesInicioFiltro.getValue()) return false;
                    if (mesFinFiltro != null && mesValue > mesFinFiltro.getValue()) return false;
                    return true;
                })
                .collect(Collectors.toList());

        medicamentosPorMes = recetasFiltradas.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getFechaConfeccion().getMonth(),
                        Collectors.summingInt(r -> r.getMedicamentos().size())
                ));

        recetasPorEstado = recetasFiltradas.stream()
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