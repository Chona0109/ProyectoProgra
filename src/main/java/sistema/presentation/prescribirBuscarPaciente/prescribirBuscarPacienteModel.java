package sistema.presentation.prescribirBuscarPaciente;

import sistema.logic.Service;
import sistema.logic.entities.Paciente;
import sistema.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class prescribirBuscarPacienteModel extends AbstractModel {

    public static final String LIST = "LIST";
    public static final String CURRENT = "CURRENT";

    private List<Paciente> list;
    private Paciente current;

    public prescribirBuscarPacienteModel() {
        list = Service.instance().findAllPaciente();
        current = new Paciente();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
    }

    public List<Paciente> getList() {
        return list;
    }

    public void setList(List<Paciente> list) {
        this.list = list != null ? list : new ArrayList<>();
        firePropertyChange(LIST);
    }

    public Paciente getCurrent() {
        return current;
    }

    public void setCurrent(Paciente current) {
        this.current = current != null ? current : new Paciente();
        firePropertyChange(CURRENT);
    }
}


