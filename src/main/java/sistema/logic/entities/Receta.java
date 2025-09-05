package sistema.logic.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Receta {

    private String id;
    private Medico medico;
    private Paciente paciente;
    private List<MedicamentoDetalle> medicamentos;
    private LocalDate fechaConfeccion;
    private LocalDate fechaRetiro;
    private String estado; // ahora es String

    public Receta() {
        this.medicamentos = new ArrayList<>();
        this.fechaConfeccion = LocalDate.now();
        this.estado = "EN_PROCESO"; // valor inicial
        this.fechaRetiro = LocalDate.now().plusDays(3);
    }

    public Receta(Medico medico, Paciente paciente) {
        this();
        this.medico = medico;
        this.paciente = paciente;
    }

    // --- Getters & Setters ---
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public List<MedicamentoDetalle> getMedicamentos() { return medicamentos; }
    public void setMedicamentos(List<MedicamentoDetalle> medicamentos) {
        this.medicamentos = medicamentos != null ? medicamentos : new ArrayList<>();
    }

    public LocalDate getFechaConfeccion() { return fechaConfeccion; }
    public void setFechaConfeccion(LocalDate fechaConfeccion) { this.fechaConfeccion = fechaConfeccion; }

    public LocalDate getFechaRetiro() { return fechaRetiro; }
    public void setFechaRetiro(LocalDate fechaRetiro) { this.fechaRetiro = fechaRetiro; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public int getCantidadMedicamentos() { return medicamentos.size(); }

    // Métodos para agregar y remover medicamentos
    public void agregarMedicamento(MedicamentoDetalle detalle) {
        if (detalle != null) medicamentos.add(detalle);
    }

    public void removerMedicamento(int index) {
        if (index >= 0 && index < medicamentos.size()) {
            medicamentos.remove(index);
        }
    }
}
