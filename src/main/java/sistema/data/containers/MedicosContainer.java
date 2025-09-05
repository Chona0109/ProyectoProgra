package sistema.data.containers;

import sistema.logic.entities.*;
import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "medicos")
@XmlAccessorType(XmlAccessType.FIELD)
public class MedicosContainer {

    @XmlElementWrapper(name = "lista")
    @XmlElement(name = "medico")
    private List<Medico> medicos;

    public MedicosContainer() {
        this.medicos = new ArrayList<>();
    }

    public List<Medico> getMedicos() { return medicos; }
    public void setMedicos(List<Medico> medicos) { this.medicos = medicos; }
}
