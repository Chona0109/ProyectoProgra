package sistema.logic.entities;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "medicamentoDetalle")
@XmlAccessorType(XmlAccessType.FIELD)
public class MedicamentoDetalle {
    @XmlElement
    private Medicamento medicamento;

    @XmlElement
    private int cantidad;

    @XmlElement
    private String indicaciones;

    @XmlElement
    private int dias;

    public MedicamentoDetalle(Medicamento medicamento, int cantidad, String indicaciones, int dias) {
        this.medicamento = medicamento;
        this.cantidad = cantidad;
        this.indicaciones = indicaciones;
        this.dias = dias;
    }

    public MedicamentoDetalle() {
        // Constructor vacío para JAXB
    }

    // Getters y Setters (mantener los existentes)
    public Medicamento getMedicamento() { return medicamento; }
    public void setMedicamento(Medicamento medicamento) { this.medicamento = medicamento; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public String getIndicaciones() { return indicaciones; }
    public void setIndicaciones(String indicaciones) { this.indicaciones = indicaciones; }
    public int getDias() { return dias; }
    public void setDias(int dias) { this.dias = dias; }
}