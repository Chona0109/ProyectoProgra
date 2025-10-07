package sistema.presentation.Farmaceutas;

import sistema.logic.Service;
import sistema.logic.entities.Farmaceutico;

public class FarmaceutasController {

    private FarmaceutasModel model;

    public FarmaceutasController(FarmaceutasForm farmaceutasForm, FarmaceutasModel model) {
        this.model = model;
        model.setList(Service.instance().findAllFarmaceuticos());
    }

    public void setCurrent(Farmaceutico v) {
        model.setCurrent(v);
    }


    public void create(Farmaceutico f) throws Exception {
        Service.instance().create(f);
        model.setCurrent(new Farmaceutico());
        model.setList(Service.instance().findAllFarmaceuticos());
    }

    public void update(Farmaceutico farmaceutico) throws Exception {
        Service.instance().updateFarmaceutico(farmaceutico);
        refreshFarmaceuticos();
    }

    private void refreshFarmaceuticos() {
        model.setList(Service.instance().findAllFarmaceuticos());
        model.setCurrent(new Farmaceutico());
    }



    public void read(String id) throws Exception {
        Farmaceutico f = new Farmaceutico();
        f.setId(id);
        try {
            model.setCurrent(Service.instance().read(f));
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
        Service.instance().delete(f);
        model.setCurrent(new Farmaceutico());
        model.setList(Service.instance().findAllFarmaceuticos());
    }

    public void search(String nombre) {
        model.setList(Service.instance().searchFarmaceuticoByName(nombre));
    }
}
