package sistema.presentation.prescribirReceta;

import sistema.logic.entities.MedicamentoDetalle;
import sistema.logic.entities.Receta;
import sistema.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class prescribirRecetaModel extends AbstractModel {

    private Receta current;
    private List<Receta> list;
    private List<MedicamentoDetalle> detalleList = new ArrayList<>();

    public static final String CURRENT = "current";
    public static final String LIST = "list";
    public static final String DETALLE_LIST = "detalleList";

    public prescribirRecetaModel() {
        current = new Receta();
        list = new ArrayList<>();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
    }

    public Receta getCurrent() {
        return current;
    }

    public void setCurrent(Receta current) {
        if (current == null) current = new Receta();
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public List<Receta> getList() {
        return list;
    }

    public void setList(List<Receta> list) {
        this.list = list != null ? list : new ArrayList<>();
        firePropertyChange(LIST);
    }

    public List<MedicamentoDetalle> getDetalleList() {
        return detalleList;
    }

    public void setDetalleList(List<MedicamentoDetalle> list) {
        this.detalleList = list != null ? list : new ArrayList<>();
        firePropertyChange(DETALLE_LIST);
    }
}
