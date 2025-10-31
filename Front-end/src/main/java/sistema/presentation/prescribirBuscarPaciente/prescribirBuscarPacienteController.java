package sistema.presentation.prescribirBuscarPaciente;

import logic.entities.Medico;
import sistema.logic.Proxy;
import logic.entities.Paciente;

import java.util.List;
import sistema.presentation.ThhreadListener;
public class prescribirBuscarPacienteController implements ThhreadListener {

    private prescribirBuscarPacienteModel model;

    public prescribirBuscarPacienteController(prescribirBuscarPacienteModel model) {
        this.model = model;
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
    }

    public void seleccionarPaciente(String id) {
        try {
            Paciente p = new Paciente();
            p.setId(id);
            Paciente encontrado = Proxy.instance().read(p);
            model.setCurrent(encontrado);
        } catch (Exception e) {

        }
    }
    @Override
    public void refresh() {
        try {
            model.setList(Proxy.instance().search(new Paciente()));
        } catch (Exception e) {}
    }
}
