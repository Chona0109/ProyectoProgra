package sistema.presentation.prescribirReceta;

import sistema.logic.entities.Paciente;
import sistema.logic.entities.Medicamento;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class prescribirRecetaModel {

    public static final String PACIENTE = "PACIENTE";
    public static final String MEDICAMENTOS = "MEDICAMENTOS";

    private PropertyChangeSupport support;
    private Paciente pacienteSeleccionado;
    private List<Medicamento> medicamentosSeleccionados;

    public prescribirRecetaModel() {
        support = new PropertyChangeSupport(this);
        medicamentosSeleccionados = new ArrayList<>();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public Paciente getPacienteSeleccionado() {
        return pacienteSeleccionado;
    }

    public void setPacienteSeleccionado(Paciente pacienteSeleccionado) {
        Paciente old = this.pacienteSeleccionado;
        this.pacienteSeleccionado = pacienteSeleccionado;
        support.firePropertyChange(PACIENTE, old, pacienteSeleccionado);
    }

    public List<Medicamento> getMedicamentosSeleccionados() {
        return medicamentosSeleccionados;
    }

    public void agregarMedicamento(Medicamento m) {
        medicamentosSeleccionados.add(m);
        support.firePropertyChange(MEDICAMENTOS, null, medicamentosSeleccionados);
    }

    public void limpiarMedicamentos() {
        medicamentosSeleccionados.clear();
        support.firePropertyChange(MEDICAMENTOS, null, medicamentosSeleccionados);
    }
}
