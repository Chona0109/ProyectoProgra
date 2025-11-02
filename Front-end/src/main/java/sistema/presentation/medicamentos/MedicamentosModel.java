package sistema.presentation.medicamentos;

import logic.entities.Medicamento;
import sistema.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class MedicamentosModel extends AbstractModel {

    private Medicamento filter;
    private Medicamento current;
    private List<Medicamento> list;
    private int mode;

    public static final String CURRENT = "current";
    public static final String LIST = "list";
    public static final String FILTER = "filter";

    public static final int MODE_CREATE = 1;
    public static final int MODE_EDIT = 2;

    public MedicamentosModel() {
        filter = new Medicamento();
        current = new Medicamento();
        list = new ArrayList<>();
        mode = MODE_CREATE;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
        firePropertyChange(FILTER);
    }

    public void init() {
        filter = new Medicamento();
        current = new Medicamento();
        list = new ArrayList<>();
        mode = MODE_CREATE;
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
        firePropertyChange(FILTER);
    }

    public Medicamento getFilter() {
        return filter;
    }

    public void setFilter(Medicamento filter) {
        this.filter = filter != null ? filter : new Medicamento();
        firePropertyChange(FILTER);
    }

    public Medicamento getCurrent() {
        return current;
    }

    public void setCurrent(Medicamento current) {
        this.current = current != null ? current : new Medicamento();
        firePropertyChange(CURRENT);
    }

    public List<Medicamento> getList() {
        return list;
    }

    public void setList(List<Medicamento> list) {
        this.list = list != null ? list : new ArrayList<>();
        firePropertyChange(LIST);
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
