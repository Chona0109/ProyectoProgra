package sistema.logic.entities;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "medico")
@XmlAccessorType(XmlAccessType.FIELD)
public class Medico extends Usuario {
    @XmlElement
    private String especialidad;

    public Medico(String id, String nombre, String especialidad) {
        super(id, nombre);
        this.especialidad = especialidad;
    }

    public Medico() {
        super();
        this.especialidad = "";
    }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
}