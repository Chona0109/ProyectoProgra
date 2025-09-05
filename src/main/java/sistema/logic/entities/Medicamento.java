package sistema.logic.entities;

import jakarta.xml.bind.annotation.*;
import java.util.Objects;

@XmlRootElement(name = "medicamento")
@XmlAccessorType(XmlAccessType.FIELD)
public class Medicamento {
    @XmlID
    @XmlElement
    private String codigo;

    @XmlElement
    private String nombre;

    @XmlElement
    private String presentacion;

    public Medicamento(String codigo, String nombre, String presentacion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.presentacion = presentacion;
    }

    public Medicamento() {
        this.codigo = "";
        this.presentacion = "";
        this.nombre = "";
    }

    // Getters y Setters (mantener los existentes)
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getPresentacion() { return presentacion; }
    public void setPresentacion(String presentacion) { this.presentacion = presentacion; }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Medicamento)) return false;
        Medicamento that = (Medicamento) o;
        return Objects.equals(codigo, that.codigo);
    }

    public int hashCode() {
        return Objects.hash(codigo);
    }
}