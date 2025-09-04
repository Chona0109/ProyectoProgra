package sistema.presentation.medicamentos;

import sistema.logic.Service;
import sistema.logic.entities.Medicamento;
import sistema.presentation.medicamentos.MedicamentosForm.MedicamentosForm;

public class MedicamentosController {

    private MedicamentosModel model;

    public MedicamentosController(MedicamentosForm medicamentosForm, MedicamentosModel model) {
        this.model = model;
        model.setList(Service.instance().findAllMedicamentos());
    }

    public void create(Medicamento e) throws Exception {
        Service.instance().create(e);
        model.setCurrent(new Medicamento());
        model.setList(Service.instance().findAllMedicamentos());
    }

    public void read(String codigo) throws Exception {
        Medicamento e = new Medicamento();
        e.setCodigo(codigo);
        try {
            model.setCurrent(Service.instance().read(e));
        } catch (Exception ex) {
            Medicamento nuevo = new Medicamento();
            nuevo.setCodigo(codigo);
            model.setCurrent(nuevo);
            throw ex;
        }
    }

    public void clear() {
        model.setCurrent(new Medicamento());
    }

    public void delete(String codigo) throws Exception {
        Medicamento m = new Medicamento();
        m.setCodigo(codigo);
        Service.instance().delete(m);
        model.setCurrent(new Medicamento());
        model.setList(Service.instance().findAllMedicamentos());
    }

    public void searchMedicamentos(String codigo) {
        model.setList(Service.instance().searchMedicamentoByCodigo(codigo));
    }
}
