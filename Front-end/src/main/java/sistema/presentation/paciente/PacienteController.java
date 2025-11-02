package sistema.presentation.paciente;

import sistema.logic.Proxy;
import logic.entities.Paciente;
import sistema.logic.SocketListener;
import sistema.presentation.ThreadListener;

import javax.swing.*;
import java.util.List;

public class PacienteController implements ThreadListener {

    private PacienteModel model;
    private SocketListener socketListener;

    public PacienteController(PacientesForm form, PacienteModel model) {
        this.model = model;
        model.init();

        // Carga inicial de pacientes
        loadPacientes();

        // Inicia SocketListener para recibir actualizaciones
        try {
            socketListener = new SocketListener(this, Proxy.instance().getSid());
            socketListener.start();
        } catch (Exception e) {
            System.err.println("Error iniciando SocketListener: " + e.getMessage());
        }
    }

    private void loadPacientes() {
        new Thread(() -> {
            try {
                List<Paciente> lista = Proxy.instance().search(new Paciente());
                SwingUtilities.invokeLater(() -> model.setList(lista));
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

    public void setCurrent(Paciente p) {
        model.setCurrent(p);
    }

    public void update(Paciente p) throws Exception {
        Proxy.instance().updatePaciente(p);
        refreshPacientes();
    }

    private void refreshPacientes() {
        model.setList(Proxy.instance().search(new Paciente()));
        model.setCurrent(new Paciente());
    }

    public void delete(String id) throws Exception {
        Paciente p = new Paciente();
        p.setId(id);
        Proxy.instance().delete(p);
        model.setCurrent(new Paciente());
        model.setList(Proxy.instance().search(new Paciente()));
    }

    public void clear() {
        model.setCurrent(new Paciente());
        model.setMode(PacienteModel.MODE_CREATE);
    }

    public void search(String nombre) {
        model.setList(Proxy.instance().searchPacienteByName(nombre));
    }

    public void stop() {
        if (socketListener != null) socketListener.stop();
    }

    @Override
    public void deliver_message(String message) {
        // Actualiza la lista cuando llega un mensaje desde SocketListener
        new Thread(() -> {
            try {
                List<Paciente> lista = Proxy.instance().search(new Paciente());
                SwingUtilities.invokeLater(() -> model.setList(lista));
            } catch (Exception e) {
                System.err.println("Error actualizando pacientes: " + e.getMessage());
            }
        }).start();
        System.out.println("Mensaje recibido: " + message);
    }
}
