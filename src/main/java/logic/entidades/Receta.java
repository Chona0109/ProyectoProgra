package logic.entidades;

import logic.entidades.Medico;
import logic.entidades.Paciente;

public class Receta {
    private Medico medico;
    private Paciente paciente;
    private Medicamento medicamento;
    private String cantidad;
    private String Indicaciones;
    //Fecha de Confeccion
    // Fecha de Retiro


    public Receta(Medico m, Paciente p, Medicamento md, String c, String i) {

        this.medico = m;
        this.paciente = p;
        this.medicamento = md;
        this.cantidad = c;
        this.Indicaciones = i;

    }
    public Medico getMedico() {
        return medico;
    }
    public void setMedico(Medico medico) {
        this.medico = medico;
    }
    public Paciente getPaciente() {
        return paciente;
    }
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
    public Medicamento getMedicamento() {
        return medicamento;
    }
    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }
    public String getCantidad() {
        return cantidad;
    }
    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
    public String getIndicaciones() {
        return Indicaciones;
    }
    public void setIndicaciones(String indicaciones) {
        Indicaciones = indicaciones;
    }

}
