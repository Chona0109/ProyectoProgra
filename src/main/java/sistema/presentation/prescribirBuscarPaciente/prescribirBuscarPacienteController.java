package sistema.presentation.prescribirBuscarPaciente;

import sistema.logic.Service;
import sistema.logic.entities.Paciente;

import java.util.List;

public class prescribirBuscarPacienteController {

    private prescribirBuscarPacienteModel model;

    public prescribirBuscarPacienteController(prescribirBuscarPacienteModel model) {
        this.model = model;
    }

    // Buscar pacientes según criterio
    public void search(String criterio, String valor) {
        List<Paciente> resultados;

        if (valor == null || valor.trim().isEmpty()) {
            resultados = Service.instance().findAllPaciente();
        } else if ("ID".equalsIgnoreCase(criterio)) {
            resultados = Service.instance().searchPacienteById(valor.trim());
        } else if ("Nombre".equalsIgnoreCase(criterio)) {
            resultados = Service.instance().searchPacienteByName(valor.trim());
        } else {
            resultados = Service.instance().findAllPaciente();
        }

        model.setList(resultados);
    }

    public void seleccionarPaciente(String id) {
        try {
            Paciente p = new Paciente();
            p.setId(id);
            Paciente encontrado = Service.instance().read(p);
            model.setCurrent(encontrado);
        } catch (Exception e) {
            // Silenciar error si no se encuentra
        }
    }
}
