package logic.controllers;

import View.Departamentos;
import logic.Service;
import logic.entidades.Departamento;
import logic.entidades.Usuario;
import logic.models.DepartamentosModel;

import java.util.List;

public class DepartamentosController {

    private Departamentos view;
    private DepartamentosModel model;

    // La entidad que estamos editando
    private Usuario entity;

    public DepartamentosController(Departamentos view, DepartamentosModel model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);

        // Cargar departamentos iniciales
        List<Departamento> departamentos = Service.instance().search(new Departamento());
        model.setDepartamentos(departamentos);
    }

    // Asignar la entidad actual a editar
    public void setCurrentEntity(Usuario u) {
        this.entity = u;
    }

    // Asignar departamento seleccionado a la entidad
    public void setDepartamento(int row) {
        if (row >= 0 && row < model.getDepartamentos().size() && entity != null) {
            Departamento dep = model.getDepartamentos().get(row);
            entity.setDepartamento(dep);
        }
    }

    public void searchDepartamentos(String nombre) {
        Departamento d = new Departamento();
        d.setNombre(nombre);
        model.setDepartamentos(Service.instance().search(d));
    }
}
