package sistema.data.containers;

import sistema.logic.entities.*;
import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "medicamentos")
@XmlAccessorType(XmlAccessType.FIELD)
public class MedicamentosContainer {

    @XmlElementWrapper(name = "lista")
    @XmlElement(name = "medicamento")
    private List<Medicamento> medicamentos;

    public MedicamentosContainer() {
        this.medicamentos = new ArrayList<>();
    }

    public List<Medicamento> getMedicamentos() { return medicamentos; }
    public void setMedicamentos(List<Medicamento> medicamentos) { this.medicamentos = medicamentos; }
}