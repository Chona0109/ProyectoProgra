package sistema.logic.entities;

import jakarta.xml.bind.annotation.*;


@XmlRootElement(name = "usuario")
@XmlAccessorType(XmlAccessType.FIELD)
public class Usuario {
    @XmlID
    @XmlElement
    private String id;

    @XmlElement
    private String nombre;

    @XmlElement
    private String clave;

    @XmlElement
    private Departamento departamento;

    public Usuario(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.clave = id;
    }

    public Usuario() {
        this.id = "";
        this.nombre = "";
        this.clave = "";
    }

    // Getters y Setters (mantener los existentes)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
    public Departamento getDepartamento() { return departamento; }
    public void setDepartamento(Departamento departamento) { this.departamento = departamento; }
}