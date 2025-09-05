package sistema.logic.entities;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "departamento")
@XmlAccessorType(XmlAccessType.FIELD)
public class Departamento {
    @XmlID
    @XmlElement
    private String codigo;

    @XmlElement
    private String nombre;

    public Departamento(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public Departamento() {
        this("", "");
    }

    // Getters y Setters (mantener los existentes)
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}