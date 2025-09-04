package sistema.presentation.paciente;

import sistema.logic.Service;
import sistema.logic.entities.Paciente;
import sistema.presentation.paciente.PacientesForm.*;

public class PacienteController {

    private sistema.presentation.paciente.PacienteModel model;

    public PacienteController(PacientesForm pacientesForm, PacienteModel model) {
        this.model = model;
        model.setList(Service.instance().findAllPaciente());
    }

    public void create(Paciente p) throws Exception {
        Service.instance().create(p);
        model.setCurrent(new Paciente());
        model.setList(Service.instance().findAllPaciente());
    }

    public void read(String id) throws Exception {
        Paciente p = new Paciente();
        p.setId(id);
        try {
            model.setCurrent(Service.instance().read(p));
        } catch (Exception ex) {
            Paciente nuevo = new Paciente();
            nuevo.setId(id);
            model.setCurrent(nuevo);
            throw ex;
        }
    }

    public void clear() {
        model.setCurrent(new Paciente());
    }

    public void delete(String id) throws Exception {
        Paciente p = new Paciente();
        p.setId(id);
        Service.instance().delete(p);
        model.setCurrent(new Paciente());
        model.setList(Service.instance().findAllPaciente());
    }

    public void search(String nombre) {
        model.setList(Service.instance().searchPacienteByName(nombre));
    }
}
