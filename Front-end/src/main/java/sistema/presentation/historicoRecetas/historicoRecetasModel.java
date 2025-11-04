package sistema.presentation.historicoRecetas;

import logic.entities.Receta;
import sistema.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class historicoRecetasModel extends AbstractModel {

    private Receta filter;
    private Receta current;
    private List<Receta> list;

    private int mode;

    public static final String CURRENT = "current";
    public static final String LIST = "list";
    public static final String FILTER = "filter";
    public static final int MODE_CREATE = 1;
    public static final int MODE_EDIT = 2;

    public historicoRecetasModel() {
        init();
    }

    public void init() {
        filter = new Receta();
        current = new Receta();
        list = new ArrayList<>();
        mode = MODE_CREATE;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
        firePropertyChange(FILTER);
    }


    public Receta getFilter() { return filter; }
    public void setFilter(Receta filter) {
        this.filter = filter != null ? filter : new Receta();
        firePropertyChange(FILTER);
    }

    public Receta getCurrent() { return current; }
    public void setCurrent(Receta current) {
        this.current = current != null ? current : new Receta();
        firePropertyChange(CURRENT);
    }

    public List<Receta> getList() { return list; }
    public void setList(List<Receta> list) {
        this.list = list != null ? list : new ArrayList<>();
        firePropertyChange(LIST);
    }

    public List<Receta> getCurrentList() {
        return list != null ? list : new ArrayList<>();
    }

    public int getMode() { return mode; }
    public void setMode(int mode) { this.mode = mode; }
}
