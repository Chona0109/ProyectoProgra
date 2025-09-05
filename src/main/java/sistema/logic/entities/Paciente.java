package sistema.logic.entities;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlRootElement(name = "paciente")
@XmlAccessorType(XmlAccessType.FIELD)
public class Paciente {
    @XmlID
    @XmlElement
    private String id;

    @XmlElement
    private String nombre;

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaNacimiento;

    @XmlElement
    private String telefono;

    public Paciente(String id, String nombre, LocalDate fechaNacimiento, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
    }

    public Paciente() {
        this.id = "";
        this.nombre = "";
        this.fechaNacimiento = null;
        this.telefono = "";
    }

    // Getters y Setters (mantener los existentes)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}