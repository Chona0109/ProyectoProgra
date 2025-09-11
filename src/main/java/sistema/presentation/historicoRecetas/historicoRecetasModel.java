package sistema.presentation.historicoRecetas;

import sistema.logic.entities.Receta;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class historicoRecetasModel {

    public static final String LIST = "list";
    public static final String CURRENT = "current";

    private List<Receta> list;
    private Receta current;
    private PropertyChangeSupport propertyChangeSupport;

    public historicoRecetasModel() {
        list = new ArrayList<>();
        current = null;
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public List<Receta> getList() {
        return list;
    }

    public void setList(List<Receta> list) {
        this.list = list != null ? list : new ArrayList<>();
        propertyChangeSupport.firePropertyChange(LIST, null, this.list);
    }

    public Receta getCurrent() {
        return current;
    }

    public void setCurrent(Receta current) {
        this.current = current;
        propertyChangeSupport.firePropertyChange(CURRENT, null, this.current);
    }

    public List<Receta> getCurrentList() {
        return list != null ? list : new ArrayList<>();
    }

}
