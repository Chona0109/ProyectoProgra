package sistema.presentation.paciente;

import logic.entities.Medico;
import sistema.logic.Proxy;
import logic.entities.Paciente;
import sistema.presentation.ThhreadListener;

public class PacienteController implements ThhreadListener {

    private PacienteModel model;

    public PacienteController(PacientesForm pacientesForm, PacienteModel model) {
        this.model = model;
        model.setList(Proxy.instance().search(new Paciente()));
    }

    public void create(Paciente p) throws Exception {
        Proxy.instance().create(p);
        model.setCurrent(new Paciente());
        model.setList(Proxy.instance().search(new Paciente()));
    }

    public void setCurrent(Paciente v) {
        model.setCurrent(v);
    }

    public void update(Paciente paciente) throws Exception {
        Proxy.instance().updatePaciente(paciente);
        refreshPacientes();
    }

    private void refreshPacientes() {
        model.setList(Proxy.instance().search(new Paciente()));
        model.setCurrent(new Paciente());
    }

    public void read(String id) throws Exception {
        Paciente p = new Paciente();
        p.setId(id);
        try {
            model.setCurrent(Proxy.instance().read(p));
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
        Proxy.instance().delete(p);
        model.setCurrent(new Paciente());
        model.setList(Proxy.instance() .search(new Paciente()));
    }

    public void search(String nombre) {
        model.setList(Proxy.instance().searchPacienteByName(nombre));
    }

    @Override
    public void refresh() {
        try {
            model.setList(Proxy.instance().search(new Paciente()));
        } catch (Exception e) {}
    }
}
