package sistema.data.containers;

import sistema.logic.entities.*;
import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

// Contenedor para Departamentos
@XmlRootElement(name = "departamentos")
@XmlAccessorType(XmlAccessType.FIELD)
public class DepartamentosContainer {

    @XmlElementWrapper(name = "lista")
    @XmlElement(name = "departamento")
    private List<Departamento> departamentos;

    public DepartamentosContainer() {
        this.departamentos = new ArrayList<>();
    }

    public List<Departamento> getDepartamentos() { return departamentos; }
    public void setDepartamentos(List<Departamento> departamentos) { this.departamentos = departamentos; }
}