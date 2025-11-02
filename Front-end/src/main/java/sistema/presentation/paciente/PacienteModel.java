package sistema.presentation.paciente;

import logic.entities.Paciente;
import sistema.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class PacienteModel extends AbstractModel {

    private Paciente filter;
    private Paciente current;
    private List<Paciente> list;
    private int mode;

    public static final String CURRENT = "current";
    public static final String LIST = "list";
    public static final String FILTER = "filter";

    public static final int MODE_CREATE = 1;
    public static final int MODE_EDIT = 2;

    public PacienteModel() {
        filter = new Paciente();
        current = new Paciente();
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
        filter = new Paciente();
        current = new Paciente();
        list = new ArrayList<>();
        mode = MODE_CREATE;
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
        firePropertyChange(FILTER);
    }

    public Paciente getFilter() { return filter; }

    public void setFilter(Paciente filter) {
        this.filter = filter != null ? filter : new Paciente();
        firePropertyChange(FILTER);
    }

    public Paciente getCurrent() { return current; }

    public void setCurrent(Paciente current) {
        this.current = current != null ? current : new Paciente();
        firePropertyChange(CURRENT);
    }

    public List<Paciente> getList() { return list; }

    public void setList(List<Paciente> list) {
        this.list = list != null ? list : new ArrayList<>();
        firePropertyChange(LIST);
    }

    public int getMode() { return mode; }

    public void setMode(int mode) { this.mode = mode; }
}
