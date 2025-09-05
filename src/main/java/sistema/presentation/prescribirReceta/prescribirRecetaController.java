package sistema.presentation.prescribirReceta;

import sistema.presentation.prescribirBuscarPaciente.prescribirBuscarPaciente;
import sistema.presentation.prescribirBuscarPaciente.prescribirBuscarPacienteController;
import sistema.presentation.prescribirBuscarPaciente.prescribirBuscarPacienteModel;
import sistema.presentation.prescribirMedicamento.prescribirMedicamento;
import sistema.presentation.prescribirMedicamento.prescribirBuscarMedicamentoController;
import sistema.presentation.prescribirMedicamento.prescribirBuscarMedicamentoModel;

import javax.swing.*;

public class prescribirRecetaController {

    private prescribirRecetaModel model;
    private JFrame parent;

    public prescribirRecetaController(prescribirRecetaModel model, JFrame parent) {
        this.model = model;
        this.parent = parent;
    }

    // Abrir panel para buscar paciente
    public void buscarPaciente() {
        prescribirBuscarPacienteModel pacienteModel = new prescribirBuscarPacienteModel();
        prescribirBuscarPacienteController pacienteController = new prescribirBuscarPacienteController(pacienteModel);
        prescribirBuscarPaciente dialog = new prescribirBuscarPaciente(parent);
        dialog.setModel(pacienteModel);
        dialog.setController(pacienteController);
        dialog.setVisible(true);

        // Al seleccionar paciente, actualizar el modelo principal
        pacienteModel.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals(prescribirBuscarPacienteModel.CURRENT)) {
                model.setPacienteSeleccionado(pacienteModel.getCurrent());
            }
        });
    }

    // Abrir panel para buscar medicamento
    public void agregarMedicamento() {
        prescribirBuscarMedicamentoModel medModel = new prescribirBuscarMedicamentoModel();
        prescribirBuscarMedicamentoController medController = new prescribirBuscarMedicamentoController(medModel);
        prescribirMedicamento dialog = new prescribirMedicamento(parent);
        dialog.setModel(medModel);
        dialog.setController(medController);
        dialog.setVisible(true);

        // Al seleccionar medicamento, agregar al modelo principal
        medModel.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals(prescribirBuscarMedicamentoModel.CURRENT)) {
                model.agregarMedicamento(medModel.getCurrent());
            }
        });
    }
}
