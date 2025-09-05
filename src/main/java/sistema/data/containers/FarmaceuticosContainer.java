package sistema.data.containers;

import sistema.logic.entities.*;
import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "farmaceuticos")
@XmlAccessorType(XmlAccessType.FIELD)
public class FarmaceuticosContainer {

    @XmlElementWrapper(name = "lista")
    @XmlElement(name = "farmaceutico")
    private List<Farmaceutico> farmaceuticos;

    public FarmaceuticosContainer() {
        this.farmaceuticos = new ArrayList<>();
    }

    public List<Farmaceutico> getFarmaceuticos() { return farmaceuticos; }
    public void setFarmaceuticos(List<Farmaceutico> farmaceuticos) { this.farmaceuticos = farmaceuticos; }
}