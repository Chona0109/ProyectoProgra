package sistema.presentation.prescribirMedicamento;

import sistema.logic.entities.Medicamento;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class prescribirBuscarMedicamentoModel {

    public static final String LIST = "LIST";
    public static final String CURRENT = "CURRENT";

    private List<Medicamento> list;
    private Medicamento current;

    private PropertyChangeSupport support;

    public prescribirBuscarMedicamentoModel() {
        list = new ArrayList<>();
        support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void setList(List<Medicamento> list) {
        List<Medicamento> old = this.list;
        this.list = list;
        support.firePropertyChange(LIST, old, list);
    }

    public List<Medicamento> getList() {
        return list;
    }

    public void setCurrent(Medicamento current) {
        Medicamento old = this.current;
        this.current = current;
        support.firePropertyChange(CURRENT, old, current);
    }

    public Medicamento getCurrent() {
        return current;
    }
}
