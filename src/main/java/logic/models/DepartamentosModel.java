package logic.models;

import logic.entidades.Departamento;
import logic.entidades.Usuario;
import personas.AbstractModel;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class DepartamentosModel extends AbstractModel {
    private Usuario current;
    private List<Usuario> list;
    private List<Departamento> departamentos;

    public static final String CURRENT = "current";
    public static final String LIST = "list";
    public static final String DEPARTMENTS = "departments";
    public static final String DEPARTMENT = "department";

    public DepartamentosModel() {
        current = new Usuario();
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

    public Usuario getCurrent() { return current; }

    public void setCurrent(Usuario current) {
        if (current == null) current = new Usuario();
        this.current = current;
        firePropertyChange(CURRENT);
        firePropertyChange(DEPARTMENT);
    }

    public List<Usuario> getList() { return list; }

    public void setList(List<Usuario> list) {
        this.list = list != null ? list : new ArrayList<>();
        firePropertyChange(LIST);
    }

    public List<Departamento> getDepartamentos() { return departamentos; }

    public void setDepartamentos(List<Departamento> departamentos) {
        this.departamentos = departamentos != null ? departamentos : new ArrayList<>();
        firePropertyChange(DEPARTMENTS);
    }

    public void setDepartamento(Departamento departamento) {
        if (current == null) current = new Usuario();
        current.setDepartamento(departamento);
        firePropertyChange(DEPARTMENT);
    }
}
