package sistema.presentation.medicos;

import sistema.logic.Proxy;
import logic.entities.*;
import sistema.presentation.ThhreadListener;
public class MedicosController implements ThhreadListener{

    private MedicosModel model;

    public MedicosController(MedicosForm medicosForm, MedicosModel model) {
        this.model = model;
        model.setDepartamentos(Proxy.instance().search(new Departamento()));
        model.setList(Proxy.instance().search(new Medico()));
    }

    public void create(Medico e) throws Exception {
        e.setDepartamento(model.getCurrent().getDepartamento());
        Proxy.instance().create(e);
        model.setCurrent(new Medico());
        model.setList(Proxy.instance().search(new Medico()));
    }

    public void setCurrent(Medico v) {
        model.setCurrent(v);
    }

    public void update(Medico medico) throws Exception {
        Proxy.instance().updateMedico(medico);
        refreshMedicos();
    }

    private void refreshMedicos() {
        model.setList(Proxy.instance().search(new Medico()));
        model.setCurrent(new Medico());
    }

    public void read(String id) throws Exception {
        Medico e = new Medico();
        e.setId(id);
        try {
            model.setCurrent(Proxy.instance().read(e));
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
        Proxy.instance().delete(m);
        model.setCurrent(new Medico());
        model.setList(Proxy.instance().search(new Medico()));
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
        model.setDepartamentos(Proxy.instance().search(d));
    }
    @Override
    public void refresh() {
        try {
            model.setList(Proxy.instance().search(new Medico()));
            model.setDepartamentos(Proxy.instance().search(new Departamento()));
        } catch (Exception e) {}
    }
}

