package sistema.logic.entities;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "farmaceutico")
@XmlAccessorType(XmlAccessType.FIELD)
public class Farmaceutico extends Usuario {

    public Farmaceutico(String id, String nombre) {
        super(id, nombre);
    }

    public Farmaceutico() {
        super();
    }
}