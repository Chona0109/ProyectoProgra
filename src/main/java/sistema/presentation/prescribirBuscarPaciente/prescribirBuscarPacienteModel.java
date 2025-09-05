package sistema.presentation.prescribirBuscarPaciente;

import sistema.logic.Service;
import sistema.logic.entities.Paciente;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class prescribirBuscarPacienteModel {

    public static final String LIST = "LIST";
    public static final String CURRENT = "CURRENT";

    private List<Paciente> list;
    private Paciente current;
    private PropertyChangeSupport support;

    public prescribirBuscarPacienteModel() {
        support = new PropertyChangeSupport(this);
        list = Service.instance().findAllPaciente();
    }

    public List<Paciente> getList() {
        return list;
    }

    public void setList(List<Paciente> newList) {
        List<Paciente> old = this.list;
        this.list = newList;
        support.firePropertyChange(LIST, old, newList);
    }

    public Paciente getCurrent() {
        return current;
    }

    public void setCurrent(Paciente current) {
        Paciente old = this.current;
        this.current = current;
        support.firePropertyChange(CURRENT, old, current);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }
}
