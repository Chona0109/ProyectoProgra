package sistema.presentation.medicos;

import sistema.logic.Service;
import sistema.logic.entities.*;
import sistema.presentation.medicos.MedicosForm.MedicosForm;

public class MedicosController {

    private MedicosModel model;

    public MedicosController(MedicosForm medicosForm, MedicosModel model) {
        this.model = model;
        model.setDepartamentos(Service.instance().search(new Departamento()));
        model.setList(Service.instance().findAllMedicos());
    }

    public void create(Medico e) throws Exception {
        e.setDepartamento(model.getCurrent().getDepartamento());
        Service.instance().create(e);
        model.setCurrent(new Medico());
        model.setList(Service.instance().findAllMedicos());
    }

    public void read(String id) throws Exception {
        Medico e = new Medico();
        e.setId(id);
        try {
            model.setCurrent(Service.instance().read(e));
        } catch (Exception ex) {
            Medico b = new Medico();
            b.setId(id);
            model.setCurrent(b);
            throw ex;
        }
    }

    public void clear() {
        model.setCurrent(new Medico());
    }

    public void delete(String id) throws Exception {
        Medico m = new Medico();
        m.setId(id);
        Service.instance().delete(m);
        model.setCurrent(new Medico());
        model.setList(Service.instance().findAllMedicos());
    }

    public void setDepartamento(int row) {
        if (row >= 0 && row < model.getDepartamentos().size()) {
            Departamento dep = model.getDepartamentos().get(row);
            model.setDepartamento(dep);
        }
    }

    public void searchDepartamentos(String nombre) {
        Departamento d = new Departamento();
        d.setNombre(nombre);
        model.setDepartamentos(Service.instance().search(d));
    }
}
