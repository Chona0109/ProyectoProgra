package sistema.logic.entities;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class MedicamentoDetalle {
    private Medicamento medicamento;
    private int cantidad;
    private String indicaciones;
    private int dias;

    public MedicamentoDetalle(Medicamento medicamento, int cantidad, String indicaciones, int dias) {
        this.medicamento = medicamento;
        this.cantidad = cantidad;
        this.indicaciones = indicaciones;
        this.dias = dias;
    }

    public MedicamentoDetalle() {
    }

    public Medicamento getMedicamento() { return medicamento; }
    public void setMedicamento(Medicamento medicamento) { this.medicamento = medicamento; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public String getIndicaciones() { return indicaciones; }
    public void setIndicaciones(String indicaciones) { this.indicaciones = indicaciones; }
    public int getDias() { return dias; }
    public void setDias(int dias) { this.dias = dias; }
}