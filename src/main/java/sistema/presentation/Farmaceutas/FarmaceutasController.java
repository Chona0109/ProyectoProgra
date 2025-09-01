package sistema.presentation.Farmaceutas;

import sistema.logic.Service;
import sistema.logic.entities.Farmaceutico;
import sistema.presentation.Farmaceutas.FarmaceutasForm.FarmaceutasForm;

public class FarmaceutasController {

    private FarmaceutasModel model;

    public FarmaceutasController(FarmaceutasForm farmaceutasForm, FarmaceutasModel model) {
        this.model = model;
        model.setList(Service.instance().findAllFarmaceuticos());
    }

    public void create(Farmaceutico f) throws Exception {
        f.setNombre(model.getCurrent().getNombre());
        f.setClave(model.getCurrent().getClave());
        Service.instance().create(f);
        model.setCurrent(new Farmaceutico());
        model.setList(Service.instance().findAllFarmaceuticos());
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
