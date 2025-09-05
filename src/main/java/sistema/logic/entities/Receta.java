package sistema.logic.entities;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "receta")
@XmlAccessorType(XmlAccessType.FIELD)
public class Receta {

    @XmlID
    @XmlElement
    private String id;

    @XmlElement
    private Medico medico;

    @XmlElement
    private Paciente paciente;

    @XmlElementWrapper(name = "medicamentos")
    @XmlElement(name = "medicamentoDetalle")
    private List<MedicamentoDetalle> medicamentos;

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaConfeccion;

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaRetiro;

    @XmlElement
    private String estado;

    public Receta() {
        this.medicamentos = new ArrayList<>();
        this.fechaConfeccion = LocalDate.now();
        this.estado = "EN_PROCESO";
        this.fechaRetiro = LocalDate.now().plusDays(3);
    }

    public Receta(Medico medico, Paciente paciente) {
        this();
        this.medico = medico;
        this.paciente = paciente;
    }

    // Getters y Setters (mantener los existentes)
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

    public void agregarMedicamento(MedicamentoDetalle detalle) {
        if (detalle != null) medicamentos.add(detalle);
    }

    public void removerMedicamento(int index) {
        if (index >= 0 && index < medicamentos.size()) {
            medicamentos.remove(index);
        }
    }
}