package sistema.logic.entities;

public class MedicamentoDetalle {
    private Medicamento medicamento;
    private int cantidad;
    private String indicaciones;
    private int dias;

    // Constructor con todos los parámetros
    public MedicamentoDetalle(Medicamento medicamento, int cantidad, String indicaciones, int dias) {
        this.medicamento = medicamento;
        this.cantidad = cantidad;
        this.indicaciones = indicaciones;
        this.dias = dias;
    }

    // Constructor vacío
    public MedicamentoDetalle() {
        // Permite inicializar y luego setear los valores con los setters
    }

    // Getters
    public Medicamento getMedicamento() {
        return medicamento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public int getDias() {
        return dias;
    }

    // Setters
    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }
}
