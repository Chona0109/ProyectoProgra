package sistema.data.containers;

import sistema.logic.entities.*;
import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


@XmlRootElement(name = "administradores")
@XmlAccessorType(XmlAccessType.FIELD)
public class AdministradoresContainer {

    @XmlElementWrapper(name = "lista")
    @XmlElement(name = "administrador")
    private List<Administrador> administradores;

    public AdministradoresContainer() {
        this.administradores = new ArrayList<>();
    }

    public List<Administrador> getAdministradores() { return administradores; }
    public void setAdministradores(List<Administrador> administradores) { this.administradores = administradores; }
}
