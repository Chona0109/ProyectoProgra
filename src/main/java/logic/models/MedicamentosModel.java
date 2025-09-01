package logic.models;


import logic.entidades.Medicamento;

import personas.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class MedicamentosModel extends AbstractModel {

    private Medicamento current;
    private List<Medicamento> list;


    public static final String CURRENT = "current";
    public static final String LIST = "list";
    public static final String DEPARTMENTS = "departments";
    public static final String DEPARTMENT = "department";

    public MedicamentosModel() {
        current = new Medicamento();
        list = new ArrayList<>();

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
        firePropertyChange(DEPARTMENTS);
        firePropertyChange(DEPARTMENT);
    }

    public Medicamento getCurrent() { return current; }

    public void setCurrent(Medicamento current) {
        if (current == null) current = new Medicamento();
        this.current = current;
        firePropertyChange(CURRENT);
        firePropertyChange(DEPARTMENT);
    }

    public List<Medicamento> getList() { return list; }

    public void setList(List<Medicamento> list) {
        this.list = list != null ? list : new ArrayList<>();
        firePropertyChange(LIST);
    }



}
