package sistema.presentation.historicoRecetas;

import sistema.logic.entities.Receta;

import java.util.ArrayList;
import java.util.List;

public class historicoRecetasModel {

    private List<Receta> list;
    private Receta current;

    public historicoRecetasModel() {
        list = new ArrayList<>();
        current = null;
    }

    public List<Receta> getList() {
        return list;
    }

    public void setList(List<Receta> list) {
        this.list = list != null ? list : new ArrayList<>();
    }

    public Receta getCurrent() {
        return current;
    }

    public void setCurrent(Receta current) {
        this.current = current;
    }

    // Devuelve la lista actual para la tabla
    public List<Receta> getCurrentList() {
        if (current != null) {
            List<Receta> tmp = new ArrayList<>();
            tmp.add(current);
            return tmp;
        }
        return list;
    }
}
