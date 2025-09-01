package sistema.presentation.medicos;

import sistema.logic.entities.*;
import sistema.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class MedicosModel extends AbstractModel {

    private Medico current;
    private List<Medico> list;
    private List<Departamento> departamentos;

    public static final String CURRENT = "current";
    public static final String LIST = "list";
    public static final String DEPARTMENTS = "departments";
    public static final String DEPARTMENT = "department";

    public MedicosModel() {
        current = new Medico();
        list = new ArrayList<>();
        departamentos = new ArrayList<>();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
        firePropertyChange(DEPARTMENTS);
        firePropertyChange(DEPARTMENT);
    }

    public Medico getCurrent() { return current; }

    public void setCurrent(Medico current) {
        if (current == null) current = new Medico();
        this.current = current;
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
}
