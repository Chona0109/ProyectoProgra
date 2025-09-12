package sistema.presentation.prescribirReceta;

import sistema.logic.Service;
import sistema.logic.entities.MedicamentoDetalle;
import sistema.logic.entities.Paciente;
import sistema.logic.entities.Receta;

import javax.swing.*;

public class prescribirRecetaController {

    private prescribirRecetaModel model;

    public prescribirRecetaController(prescribirRecetaModel model) {
        this.model = model;
        model.setCurrent(new Receta());
        model.setList(Service.instance().findAllRecetas());
    }



    public void create(Receta receta) throws Exception {
        Service.instance().createReceta(receta);
        model.setCurrent(new Receta());
        model.setList(Service.instance().findAllRecetas());
    }

    public void read(String id) throws Exception {
        Receta receta = new Receta();
        receta.setId(id);
        try {
            model.setCurrent(Service.instance().readReceta(receta));
        } catch (Exception ex) {
            Receta nueva = new Receta();
            nueva.setId(id);
            model.setCurrent(nueva);
            throw ex;
        }
    }

    public void update(Receta receta) throws Exception {
        Service.instance().updateReceta(receta);
        model.setCurrent(new Receta());
        model.setList(Service.instance().findAllRecetas());
    }

    public void delete(String id) throws Exception {
        Receta receta = new Receta();
        receta.setId(id);
        Service.instance().deleteReceta(receta);
        model.setCurrent(new Receta());
        model.setList(Service.instance().findAllRecetas());
    }

    public void clear() {
        model.setCurrent(new Receta());
    }



    public void setPaciente(Paciente paciente) throws Exception {
        Receta current = model.getCurrent();
        current.setPaciente(paciente);
        Service.instance().updateReceta(current);
        model.setCurrent(current);
    }

    public void addMedicamento(String recetaId, MedicamentoDetalle detalle) throws Exception {
        Service.instance().addMedicamentoToReceta(recetaId, detalle);
        model.setCurrent(Service.instance().readReceta(new Receta(){{ setId(recetaId); }}));
    }

    public void removeMedicamento(String recetaId, int index) throws Exception {
        Service.instance().removeMedicamentoFromReceta(recetaId, index);
        model.setCurrent(Service.instance().readReceta(new Receta(){{ setId(recetaId); }}));
    }
    public void modificarDetalleMedicamento(int row) {
        try {
            Receta recetaActualizada = Service.instance().modificarDetalleMedicamento(model.getCurrent(), row);
            if (recetaActualizada != null) {
                model.setCurrent(recetaActualizada);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al modificar detalle: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


}
