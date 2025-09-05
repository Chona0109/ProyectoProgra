package sistema.data.containers;

import sistema.logic.entities.*;
import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


@XmlRootElement(name = "pacientes")
@XmlAccessorType(XmlAccessType.FIELD)
public class PacientesContainer {

    @XmlElementWrapper(name = "lista")
    @XmlElement(name = "paciente")
    private List<Paciente> pacientes;

    public PacientesContainer() {
        this.pacientes = new ArrayList<>();
    }

    public List<Paciente> getPacientes() { return pacientes; }
    public void setPacientes(List<Paciente> pacientes) { this.pacientes = pacientes; }
}