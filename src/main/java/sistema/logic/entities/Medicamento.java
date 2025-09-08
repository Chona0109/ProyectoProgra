package sistema.logic.entities;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Medicamento {
    @XmlID
    private String codigo;
    private String nombre;
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