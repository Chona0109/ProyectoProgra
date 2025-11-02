package sistema.presentation.medicamentos;

import sistema.logic.Proxy;
import logic.entities.Medicamento;
import sistema.logic.SocketListener;
import sistema.presentation.ThreadListener;

import javax.swing.*;
import java.util.List;

public class MedicamentosController implements ThreadListener {

    private MedicamentosModel model;
    private MedicamentosForm view;
    private SocketListener socketListener;

    public MedicamentosController(MedicamentosForm view, MedicamentosModel model) {
        this.view = view;
        this.model = model;

        model.init();
        view.setController(this);
        view.setModel(model);

        // Inicializamos lista de medicamentos
        loadMedicamentos();

        // Iniciamos socket listener para actualizaciones en tiempo real
        try {
            socketListener = new SocketListener(this, Proxy.instance().getSid());
            socketListener.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==================== SOCKET LISTENER ====================
    @Override
    public void deliver_message(String message) {
        System.out.println("Mensaje recibido: " + message);
        try {
            search(new Medicamento()); // refresca lista automáticamente
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==================== MÉTODOS CRUD ====================
    public void search(Medicamento filter) throws Exception {
        model.setFilter(filter);
        List<Medicamento> rows = Proxy.instance().search(filter);
        model.setMode(MedicamentosModel.MODE_CREATE);
        model.setList(rows);
    }

    public void create(Medicamento e) throws Exception {
        Proxy.instance().create(e);
        model.setCurrent(new Medicamento());
        search(new Medicamento());
    }

    public void update(Medicamento e) throws Exception {
        Proxy.instance().updateMedicamento(e);
        search(new Medicamento());
        model.setCurrent(new Medicamento());
    }

    public void delete(String codigo) throws Exception {
        Medicamento m = new Medicamento();
        m.setCodigo(codigo);
        Proxy.instance().delete(m);
        model.setCurrent(new Medicamento());
        model.setList(Proxy.instance().search(new Medicamento()));
    }

    public void read(String codigo) throws Exception {
        Medicamento e = new Medicamento();
        e.setCodigo(codigo);
        try {
            model.setCurrent(Proxy.instance().read(e));
        } catch (Exception ex) {
            Medicamento nuevo = new Medicamento();
            nuevo.setCodigo(codigo);
            model.setCurrent(nuevo);
            throw ex;
        }
    }

    public void setCurrent(Medicamento e) {
        model.setCurrent(e);
        model.setMode(MedicamentosModel.MODE_EDIT);
    }

    public void clear() {
        model.setCurrent(new Medicamento());
        model.setMode(MedicamentosModel.MODE_CREATE);
    }

    // ==================== MÉTODOS AUXILIARES ====================
    private void loadMedicamentos() {
        new Thread(() -> {
            try {
                List<Medicamento> lista = Proxy.instance().search(new Medicamento());
                SwingUtilities.invokeLater(() -> model.setList(lista));
            } catch (Exception e) {
                System.err.println("Error cargando medicamentos: " + e.getMessage());
            }
        }).start();
    }

    public void searchMedicamentos(String codigo) {
        model.setList(Proxy.instance().searchMedicamentoByCodigo(codigo));
    }

    // ==================== STOP SOCKET ====================
    public void stop() {
        if (socketListener != null) socketListener.stop();
    }
}
