package sistema.data.containers;

import sistema.logic.entities.*;
import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

// Contenedor para Recetas
@XmlRootElement(name = "recetas")
@XmlAccessorType(XmlAccessType.FIELD)
public class RecetasContainer {

    @XmlElementWrapper(name = "lista")
    @XmlElement(name = "receta")
    private List<Receta> recetas;

    public RecetasContainer() {
        this.recetas = new ArrayList<>();
    }

    public List<Receta> getRecetas() { return recetas; }
    public void setRecetas(List<Receta> recetas) { this.recetas = recetas; }
}
