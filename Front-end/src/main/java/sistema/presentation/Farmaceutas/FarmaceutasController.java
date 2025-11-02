package sistema.presentation.Farmaceutas;

import sistema.logic.Proxy;
import logic.entities.Farmaceutico;
import sistema.presentation.Refresher;
import sistema.presentation.ThhreadListener;

import java.util.List;

public class FarmaceutasController implements ThhreadListener {

    private FarmaceutasModel model;
    private Refresher refresher;

    public FarmaceutasController(FarmaceutasForm farmaceutasForm, FarmaceutasModel model) {
        this.model = model;


        refresher = new Refresher(this);
        refresher.start();


        loadFarmaceuticos();
    }

    private void loadFarmaceuticos() {
        new Thread(() -> {
            try {
                List<Farmaceutico> lista = Proxy.instance().search(new Farmaceutico());
                model.setList(lista);
            } catch (Exception e) {
                System.err.println("Error cargando farmaceuticos: " + e.getMessage());
            }
        }).start();
    }


    public void setCurrent(Farmaceutico v) {
        model.setCurrent(v);
    }


    public void create(Farmaceutico f) throws Exception {
        Proxy.instance().create(f);
        model.setCurrent(new Farmaceutico());
        model.setList(Proxy.instance().search(new Farmaceutico()));
    }

    public void update(Farmaceutico farmaceutico) throws Exception {
        Proxy.instance().updateFarmaceutico(farmaceutico);
        refreshFarmaceuticos();
    }

    private void refreshFarmaceuticos() {
        model.setList(Proxy.instance().search(new Farmaceutico()));
        model.setCurrent(new Farmaceutico());
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

    public void clear() {
        model.setCurrent(new Farmaceutico());
    }

    public void delete(String id) throws Exception {
        Farmaceutico f = new Farmaceutico();
        f.setId(id);
        Proxy.instance().delete(f);
        model.setCurrent(new Farmaceutico());
        model.setList(Proxy.instance().search(new Farmaceutico()));
    }

    @Override
    public void refresh() {
        try {
            List<Farmaceutico> lista = Proxy.instance().search(new Farmaceutico());
            model.setList(lista);
        } catch (Exception e) {

        }
    }

}
