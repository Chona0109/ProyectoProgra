package sistema.presentation.Farmaceutas;

import sistema.presentation.AbstractModel;
import logic.entities.Farmaceutico;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class FarmaceutasModel extends AbstractModel {

    private Farmaceutico filter;
    private Farmaceutico current;
    private List<Farmaceutico> list;

    private int mode;

    public static final String CURRENT = "current";
    public static final String LIST = "list";
    public static final String FILTER = "filter";
    public static final int MODE_CREATE = 1;
    public static final int MODE_EDIT = 2;

    public FarmaceutasModel() {
        init();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
        firePropertyChange(FILTER);
    }

    public void init() {
        filter = new Farmaceutico();
        current = new Farmaceutico();
        list = new ArrayList<>();
        mode = MODE_CREATE;
    }

    public Farmaceutico getFilter() { return filter; }
    public void setFilter(Farmaceutico filter) {
        this.filter = filter != null ? filter : new Farmaceutico();
        firePropertyChange(FILTER);
    }

    public Farmaceutico getCurrent() { return current; }
    public void setCurrent(Farmaceutico current) {
        this.current = current != null ? current : new Farmaceutico();
        firePropertyChange(CURRENT);
    }

    public List<Farmaceutico> getList() { return list; }
    public void setList(List<Farmaceutico> list) {
        this.list = list != null ? list : new ArrayList<>();
        firePropertyChange(LIST);
    }

    public int getMode() { return mode; }
    public void setMode(int mode) { this.mode = mode; }
}
