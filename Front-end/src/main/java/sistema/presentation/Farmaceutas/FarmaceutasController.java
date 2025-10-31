package sistema.presentation.Farmaceutas;

import sistema.logic.Proxy;
import logic.entities.Farmaceutico;
import sistema.presentation.ThhreadListener;

public class FarmaceutasController implements ThhreadListener {

    private FarmaceutasModel model;

    public FarmaceutasController(FarmaceutasForm farmaceutasForm, FarmaceutasModel model) {
        this.model = model;
        model.setList(Proxy.instance().search(new Farmaceutico()));
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

    public void search(String nombre) {
        model.setList(Proxy.instance().searchFarmaceuticoByName(nombre));
    }
    @Override
    public void refresh() {
        try {
            model.setList(Proxy.instance().search(new Farmaceutico()));
        } catch (Exception e) {}
    }
}
