package sistema.logic.entities;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Farmaceutico extends Usuario {

    public Farmaceutico(String id, String nombre) {
        super(id, nombre);
    }

    public Farmaceutico() {
        super();
    }
}