package sistema.presentation.historicoRecetas;

import sistema.logic.entities.Receta;
import sistema.presentation.AbstractModel;
import java.util.ArrayList;
import java.util.List;

public class historicoRecetasModel extends AbstractModel {

    public static final String LIST = "list";
    public static final String CURRENT = "current";

    private List<Receta> list;
    private Receta current;

    public historicoRecetasModel() {
        list = new ArrayList<>();
        current = new Receta();
    }

    @Override
    public void addPropertyChangeListener(java.beans.PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(LIST);
        firePropertyChange(CURRENT);
    }

    public List<Receta> getList() {
        return list;
    }

    public void setList(List<Receta> list) {
        this.list = list != null ? list : new ArrayList<>();
        firePropertyChange(LIST);
    }

    public Receta getCurrent() {
        return current;
    }

    public void setCurrent(Receta current) {
        this.current = current != null ? current : new Receta();
        firePropertyChange(CURRENT);
    }

    public List<Receta> getCurrentList() {
        return list != null ? list : new ArrayList<>();
    }
}
