package sistema.presentation.Farmaceutas;

import sistema.logic.entities.Farmaceutico;
import sistema.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class FarmaceutasModel extends AbstractModel {

    private Farmaceutico current;
    private List<Farmaceutico> list;

    public static final String CURRENT = "current";
    public static final String LIST = "list";

    public FarmaceutasModel() {
        current = new Farmaceutico();
        list = new ArrayList<>();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
    }

    public Farmaceutico getCurrent() {
        return current;
    }

    public void setCurrent(Farmaceutico current) {
        if (current == null) current = new Farmaceutico();
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public List<Farmaceutico> getList() {
        return list;
    }

    public void setList(List<Farmaceutico> list) {
        this.list = list != null ? list : new ArrayList<>();
        firePropertyChange(LIST);
    }
}
