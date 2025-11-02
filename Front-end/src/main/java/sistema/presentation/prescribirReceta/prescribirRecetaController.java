package sistema.presentation.prescribirReceta;

import logic.entities.MedicamentoDetalle;
import logic.entities.Medico;
import sistema.logic.Proxy;
import logic.entities.Paciente;
import logic.entities.Receta;
import sistema.presentation.Refresher;
import sistema.presentation.ThhreadListener;
import javax.swing.*;
import java.util.List;

public class prescribirRecetaController implements ThhreadListener {

    private prescribirRecetaModel model;
    private Refresher refresher;

    public prescribirRecetaController(prescribirRecetaModel model) {
        this.model = model;

        // Inicializa el refresher
        refresher = new Refresher(this);
        refresher.start();

        // Inicializa el modelo
        model.setCurrent(new Receta());

        // Carga los datos iniciales
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
        model.setList(Proxy.instance().search(new Receta()));
    }


    public void read(int id) throws Exception {
        Receta receta = new Receta();
        receta.setId(id);
        try {
            model.setCurrent(Proxy.instance().readReceta(receta));
        } catch (Exception ex) {
            Receta nueva = new Receta();
            nueva.setId(id);
            model.setCurrent(nueva);
            throw ex;
        }
    }

    public void update(Receta receta) throws Exception {
        Proxy.instance().updateReceta(receta);
        model.setCurrent(new Receta());
        model.setList(Proxy.instance().search(new Receta()));
    }


    public void clear() {
        model.setCurrent(new Receta());
    }



    public void setPaciente(Paciente paciente) throws Exception {
        Receta current = model.getCurrent();
        current.setPaciente(paciente);
        Proxy.instance().updateReceta(current);
        model.setCurrent(current);
    }



    public void removeMedicamento(int recetaId, int index) throws Exception {
        Proxy.instance().removeMedicamentoFromReceta(recetaId, index);
        model.setCurrent(Proxy.instance().readReceta(new Receta(){{ setId(recetaId); }}));

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
    public void refresh() {
        cargarDatos();
    }
}


