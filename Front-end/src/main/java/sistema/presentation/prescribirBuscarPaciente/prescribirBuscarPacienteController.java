package sistema.presentation.prescribirBuscarPaciente;

import sistema.logic.Proxy;
import logic.entities.Paciente;
import sistema.presentation.ThreadListener;

import java.util.List;

public class prescribirBuscarPacienteController implements ThreadListener {

    private prescribirBuscarPacienteModel model;

    public prescribirBuscarPacienteController(prescribirBuscarPacienteModel model) {
        this.model = model;
        model.init();
        loadPacientes();
    }

    private void loadPacientes() {
        // Carga inicial de todos los pacientes
        List<Paciente> lista = Proxy.instance().search(new Paciente());
        model.setList(lista);
        model.setMode(prescribirBuscarPacienteModel.MODE_CREATE);
    }

    public void search(String criterio, String valor) {
        List<Paciente> resultados;

        if (valor == null || valor.trim().isEmpty()) {
            resultados = Proxy.instance().search(new Paciente());
        } else if ("ID".equalsIgnoreCase(criterio)) {
            resultados = Proxy.instance().searchPacienteById(valor.trim());
        } else if ("Nombre".equalsIgnoreCase(criterio)) {
            resultados = Proxy.instance().searchPacienteByName(valor.trim());
        } else {
            resultados = Proxy.instance().search(new Paciente());
        }

        model.setList(resultados);
        model.setMode(prescribirBuscarPacienteModel.MODE_CREATE);
    }

    public void seleccionarPaciente(String id) {
        try {
            Paciente p = new Paciente();
            p.setId(id);
            Paciente encontrado = Proxy.instance().read(p);
            if (encontrado != null) {
                model.setCurrent(encontrado);
                model.setMode(prescribirBuscarPacienteModel.MODE_EDIT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        model.setCurrent(new Paciente());
        model.setMode(prescribirBuscarPacienteModel.MODE_CREATE);
    }

    @Override
    public void deliver_message(String message) {
        loadPacientes(); // Refresca toda la lista al recibir mensaje
    }
}
