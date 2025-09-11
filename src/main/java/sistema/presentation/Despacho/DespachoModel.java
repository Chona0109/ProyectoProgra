package sistema.presentation.Despacho;

import sistema.logic.entities.Receta;
import sistema.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class DespachoModel extends AbstractModel {
    private Receta current;
    private List<Receta> list;

    public static final String CURRENT = "current";
    public static final String LIST = "list";

    public DespachoModel() {
        current = new Receta();
        list = new ArrayList<>();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
    }

    public Receta getCurrent() {
        return current;
    }

    public void setCurrent(Receta current) {
        if (current == null) current = new Receta();
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public List<Receta> getList() {
        return list;
    }

    public void setList(List<Receta> list) {
        this.list = list != null ? list : new ArrayList<>();
        firePropertyChange(LIST);
    }
}