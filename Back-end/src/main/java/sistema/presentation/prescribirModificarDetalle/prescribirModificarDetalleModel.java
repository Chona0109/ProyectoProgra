package sistema.presentation.prescribirModificarDetalle;

import sistema.presentation.AbstractModel;
import sistema.logic.entities.MedicamentoDetalle;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class prescribirModificarDetalleModel extends AbstractModel {

    private MedicamentoDetalle current = new MedicamentoDetalle();
    private List<MedicamentoDetalle> list = new ArrayList<>();

    public static final String CURRENT = "current";
    public static final String LIST = "list";

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
    }

    public MedicamentoDetalle getCurrent() {
        return current;
    }

    public void setCurrent(MedicamentoDetalle current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public List<MedicamentoDetalle> getList() {
        return list;
    }

    public void setList(List<MedicamentoDetalle> list) {
        this.list = list != null ? list : new ArrayList<>();
        firePropertyChange(LIST);
    }

    public void setCantidad(int cantidad) {
        current.setCantidad(cantidad);
        firePropertyChange("cantidad");
    }

    public int getCantidad() {
        return current.getCantidad();
    }

    public void setDias(int dias) {
        current.setDias(dias);
        firePropertyChange("dias");
    }

    public int getDias() {
        return current.getDias();
    }

    public void setIndicaciones(String indicaciones) {
        current.setIndicaciones(indicaciones);
        firePropertyChange("indicaciones");
    }

    public String getIndicaciones() {
        return current.getIndicaciones();
    }
}
