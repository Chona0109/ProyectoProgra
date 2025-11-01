package sistema.presentation.paciente;

import logic.entities.Medico;
import sistema.logic.Proxy;
import logic.entities.Paciente;
import sistema.presentation.Refresher;
import sistema.presentation.ThhreadListener;

import java.util.List;

public class PacienteController implements ThhreadListener {

    private PacienteModel model;
    private Refresher refresher;

    public PacienteController(PacientesForm pacientesForm, PacienteModel model) {
        this.model = model;

        // Inicia refresher
        refresher = new Refresher(this);
        refresher.start();

        // Carga inicial de pacientes
        loadPacientes();
    }

    private void loadPacientes() {
        new Thread(() -> {
            try {
                List<Paciente> lista = Proxy.instance().searchPaciente(new Paciente());
                model.setList(lista);
            } catch (Exception e) {
                System.err.println("Error cargando pacientes: " + e.getMessage());
            }
        }).start();
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
