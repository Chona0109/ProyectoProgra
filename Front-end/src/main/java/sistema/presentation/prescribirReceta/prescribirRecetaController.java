package sistema.presentation.prescribirReceta;

import sistema.logic.Proxy;
import logic.entities.Paciente;
import logic.entities.Receta;
import sistema.presentation.ThreadListener;

import javax.swing.*;
import java.util.List;

public class prescribirRecetaController implements ThreadListener {

    private prescribirRecetaModel model;

    public prescribirRecetaController(prescribirRecetaModel model) {
        this.model = model;
        model.init();
        cargarDatos();
    }

    private void cargarDatos() {
        new Thread(() -> {
            try {
                List<Receta> recetas = Proxy.instance().search(new Receta());
                SwingUtilities.invokeLater(() -> model.setList(recetas));
            } catch (Exception e) {
                System.err.println("Error cargando recetas: " + e.getMessage());
            }
        }).start();
    }

    public void create(Receta receta) throws Exception {
        Receta creada = Proxy.instance().createReceta(receta);
        model.setCurrent(creada);
        cargarDatos();
        model.setMode(prescribirRecetaModel.MODE_EDIT);
    }

    public void read(int id) throws Exception {
        Receta receta = new Receta();
        receta.setId(id);
        try {
            model.setCurrent(Proxy.instance().readReceta(receta));
            model.setMode(prescribirRecetaModel.MODE_EDIT);
        } catch (Exception ex) {
            Receta nueva = new Receta();
            nueva.setId(id);
            model.setCurrent(nueva);
            model.setMode(prescribirRecetaModel.MODE_CREATE);
            throw ex;
        }
    }

    public void update(Receta receta) throws Exception {
        Proxy.instance().updateReceta(receta);
        model.setCurrent(receta);
        cargarDatos();
        model.setMode(prescribirRecetaModel.MODE_EDIT);
    }

    public void clear() {
        model.setCurrent(new Receta());
        model.setMode(prescribirRecetaModel.MODE_CREATE);
    }

    public void setPaciente(Paciente paciente) throws Exception {
        Receta current = model.getCurrent();
        current.setPaciente(paciente);
        Proxy.instance().updateReceta(current);
        model.setCurrent(current);
    }

    public void removeMedicamento(int recetaId, int index) throws Exception {
        Proxy.instance().removeMedicamentoFromReceta(recetaId, index);
        model.setCurrent(Proxy.instance().readReceta(new Receta() {{ setId(recetaId); }}));
    }

    public void modificarDetalleMedicamento(int row) {
        try {
            Receta recetaActual = model.getCurrent();
            if (recetaActual == null) {
                JOptionPane.showMessageDialog(null, "No hay receta seleccionada",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Receta recetaActualizada = Proxy.instance().modificarDetalleMedicamento(null, recetaActual, row);

            if (recetaActualizada != null) {
                model.setCurrent(recetaActualizada);
                model.setDetalleList(recetaActualizada.getMedicamentos());
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Error al modificar detalle: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void deliver_message(String message) {
        cargarDatos();
    }
}
