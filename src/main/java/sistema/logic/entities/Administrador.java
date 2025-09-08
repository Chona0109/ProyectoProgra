package sistema.logic.entities;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Administrador extends Usuario {

    public Administrador(String id, String nombre) {
        super(id, nombre);
    }

    public Administrador() {
        super();
    }
}