package sistema.presentation.paciente;

import sistema.logic.entities.Paciente;
import sistema.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class PacienteModel extends AbstractModel {

    private Paciente current;
    private List<Paciente> list;

    public static final String CURRENT = "current";
    public static final String LIST = "list";

    public PacienteModel() {
        current = new Paciente();
        list = new ArrayList<>();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
    }

    public Paciente getCurrent() {
        return current;
    }

    public void setCurrent(Paciente current) {
        if (current == null) current = new Paciente();
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public List<Paciente> getList() {
        return list;
    }

    public void setList(List<Paciente> list) {
        this.list = list != null ? list : new ArrayList<>();
        firePropertyChange(LIST);
    }
}
