package sistema.presentation.Farmaceutas;

import sistema.logic.Proxy;
import logic.entities.Farmaceutico;
import sistema.logic.SocketListener;
import sistema.presentation.ThreadListener;

import java.util.List;

public class FarmaceutasController implements ThreadListener {

    private FarmaceutasModel model;
    private FarmaceutasForm view;
    private SocketListener socketListener;

    public FarmaceutasController(FarmaceutasForm view, FarmaceutasModel model) {
        this.view = view;
        this.model = model;

        model.init();

        view.setController(this);
        view.setModel(model);

        try {
            socketListener = new SocketListener(this, Proxy.instance().getSid());
            socketListener.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Inicializamos la lista
        try {
            model.setList(Proxy.instance().search(new Farmaceutico()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==================== SOCKET LISTENER ====================
    @Override
    public void deliver_message(String message) {
        System.out.println("Mensaje recibido: " + message);
        try {

            search(new Farmaceutico()); // refresca la lista al recibir mensaje
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==================== MÉTODOS CRUD ====================
    public void search(Farmaceutico filter) throws Exception {
        model.setFilter(filter);
        List<Farmaceutico> rows = Proxy.instance().search(model.getFilter());
        model.setMode(FarmaceutasModel.MODE_CREATE);
        model.setList(rows);
    }

    public void create(Farmaceutico e) throws Exception {
        Proxy.instance().create(e);
        model.setCurrent(new Farmaceutico());
        search(new Farmaceutico());
    }

    public void update(Farmaceutico e) throws Exception {
        Proxy.instance().updateFarmaceutico(e);
        search(new Farmaceutico());
        model.setCurrent(new Farmaceutico());
    }

    public void delete(String id) throws Exception {
        Farmaceutico f = new Farmaceutico();
        f.setId(id);
        Proxy.instance().delete(f);
        model.setCurrent(new Farmaceutico());
        model.setList(Proxy.instance().search(new Farmaceutico()));
    }

    public void setCurrent(Farmaceutico e) {
        model.setCurrent(e);
        model.setMode(FarmaceutasModel.MODE_EDIT);
    }

    public void clear() {
        model.setCurrent(new Farmaceutico());
        model.setMode(FarmaceutasModel.MODE_CREATE);
    }

    public void read(String id) throws Exception {
        Farmaceutico f = new Farmaceutico();
        f.setId(id);
        try {
            model.setCurrent(Proxy.instance().read(f));
        } catch (Exception ex) {
            Farmaceutico nuevo = new Farmaceutico();
            nuevo.setId(id);
            model.setCurrent(nuevo);
            throw ex;
        }
    }

    // ==================== STOP SOCKET ====================
    public void stop() {
        if (socketListener != null) socketListener.stop();
    }
}
