package sistema.presentation.medicos;

import sistema.presentation.AbstractModel;
import logic.entities.*;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class MedicosModel extends AbstractModel {

    private Medico filter;
    private Medico current;
    private List<Medico> list;
    private List<Departamento> departamentos;

    private int mode;

    public static final String CURRENT = "current";
    public static final String LIST = "list";
    public static final String DEPARTMENTS = "departments";
    public static final String DEPARTMENT = "department";
    public static final String FILTER = "filter";
    public static final int MODE_CREATE = 1;
    public static final int MODE_EDIT = 2;

    public MedicosModel() {
        init();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
        firePropertyChange(DEPARTMENTS);
        firePropertyChange(DEPARTMENT);
        firePropertyChange(FILTER);
    }

    public void init() {
        filter = new Medico();
        current = new Medico();
        list = new ArrayList<>();
        departamentos = new ArrayList<>();
        mode = MODE_CREATE;
    }


    public Medico getFilter() { return filter; }
    public void setFilter(Medico filter) {
        this.filter = filter != null ? filter : new Medico();
        firePropertyChange(FILTER);
    }

    public Medico getCurrent() { return current; }
    public void setCurrent(Medico current) {
        this.current = current != null ? current : new Medico();
        firePropertyChange(CURRENT);
        firePropertyChange(DEPARTMENT);
    }

    public List<Medico> getList() { return list; }
    public void setList(List<Medico> list) {
        this.list = list != null ? list : new ArrayList<>();
        firePropertyChange(LIST);
    }

    public List<Departamento> getDepartamentos() { return departamentos; }
    public void setDepartamentos(List<Departamento> departamentos) {
        this.departamentos = departamentos != null ? departamentos : new ArrayList<>();
        firePropertyChange(DEPARTMENTS);
    }

    public void setDepartamento(Departamento departamento) {
        if (current != null) current.setDepartamento(departamento);
        firePropertyChange(DEPARTMENT);
    }

    public int getMode() { return mode; }
    public void setMode(int mode) { this.mode = mode; }
}
