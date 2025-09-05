package sistema.logic.entities;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "administrador")
@XmlAccessorType(XmlAccessType.FIELD)
public class Administrador extends Usuario {

    public Administrador(String id, String nombre) {
        super(id, nombre);
    }

    public Administrador() {
        super();
    }
}