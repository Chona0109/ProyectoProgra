package sistema.logic.entities;

import java.util.Objects;

public class Medicamento {
    private String codigo;
    private String descripcion;

    public Medicamento(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public Medicamento() {
        this.codigo = "";
        this.descripcion = "";
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

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
