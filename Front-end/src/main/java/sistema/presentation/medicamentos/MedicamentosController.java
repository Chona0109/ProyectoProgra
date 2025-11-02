package sistema.presentation.medicamentos;

import logic.entities.Farmaceutico;
import sistema.logic.Proxy;
import logic.entities.Medicamento;
import sistema.presentation.Refresher;
import sistema.presentation.ThhreadListener;

import javax.swing.*;
import java.util.List;

public class MedicamentosController implements ThhreadListener {

    private MedicamentosModel model;
private Refresher refresher;

    public MedicamentosController(MedicamentosForm medicamentosForm, MedicamentosModel model) {
        this.model = model;


        refresher = new Refresher(this);
        refresher.start();


        loadMedicamentos();
    }
    private void loadMedicamentos() {
        new Thread(() -> {
            try {
                List<Medicamento> lista = Proxy.instance().search(new Medicamento());
                SwingUtilities.invokeLater(() -> model.setList(lista)); // 🔹 actualizar en EDT
            } catch (Exception e) {
                System.err.println("Error cargando medicamentos: " + e.getMessage());
            }
        }).start();
    }
    public void create(Medicamento e) throws Exception {
        Proxy.instance().create(e);
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

    public void setCurrent(Medicamento v) {
        model.setCurrent(v);
    }

    public void update(Medicamento medicamento) throws Exception {
        Proxy.instance().updateMedicamento(medicamento);
        refreshMedicamentos();
    }

    private void refreshMedicamentos() {
        model.setList(Proxy.instance().search(new Medicamento()));
        model.setCurrent(new Medicamento());
    }

    public void clear() {
        model.setCurrent(new Medicamento());
    }

    public void delete(String codigo) throws Exception {
        Medicamento m = new Medicamento();
        m.setCodigo(codigo);
        Proxy.instance().delete(m);
        model.setCurrent(new Medicamento());
        model.setList(Proxy.instance().search(new Medicamento()));
    }
    @Override
    public void refresh() {
        new Thread(() -> {
            try {
                List<Medicamento> lista = Proxy.instance().search(new Medicamento());
                SwingUtilities.invokeLater(() -> model.setList(lista));
            } catch (Exception e) {

            }
        }).start();
    }

    public void searchMedicamentos(String codigo) {
        model.setList(Proxy.instance().searchMedicamentoByCodigo(codigo));
    }
}
