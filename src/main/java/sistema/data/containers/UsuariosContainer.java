package sistema.data.containers;

import sistema.logic.entities.*;
import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "usuarios")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsuariosContainer {

    @XmlElementWrapper(name = "lista")
    @XmlElement(name = "usuario")
    private List<Usuario> usuarios;

    public UsuariosContainer() {
        this.usuarios = new ArrayList<>();
    }

    public List<Usuario> getUsuarios() { return usuarios; }
    public void setUsuarios(List<Usuario> usuarios) { this.usuarios = usuarios; }
}
