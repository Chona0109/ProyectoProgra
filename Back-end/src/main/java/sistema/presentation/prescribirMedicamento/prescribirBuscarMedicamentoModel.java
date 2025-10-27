package sistema.presentation.prescribirMedicamento;

import sistema.logic.entities.Medicamento;
import sistema.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class prescribirBuscarMedicamentoModel extends AbstractModel {

    public static final String LIST = "LIST";
    public static final String CURRENT = "CURRENT";

    private List<Medicamento> list;
    private Medicamento current;

    public prescribirBuscarMedicamentoModel() {
        list = new ArrayList<>();
        current = new Medicamento();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
    }

    public List<Medicamento> getList() {
        return list;
    }

    public void setList(List<Medicamento> list) {
        this.list = list != null ? list : new ArrayList<>();
        firePropertyChange(LIST);
    }

    public Medicamento getCurrent() {
        return current;
    }

    public void setCurrent(Medicamento current) {
        if (current == null) current = new Medicamento();
        this.current = current;
        firePropertyChange(CURRENT);
    }
}
