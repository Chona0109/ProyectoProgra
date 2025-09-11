package sistema.presentation.Despacho;

import sistema.logic.Service;
import sistema.logic.entities.Receta;

public class DespachoController {
    private DespachoModel model;

    public DespachoController(DespachoModel model) {
        this.model = model;
        model.setCurrent(new Receta());
        model.setList(Service.instance().findAllRecetas()); // asumimos que existe
    }

    // -------------------- Buscar Receta --------------------
    public Receta buscarReceta(String id) throws Exception {
        if (id == null || id.isEmpty())
            throw new Exception("Debe ingresar el ID de la receta");

        Receta receta = new Receta();
        receta.setId(id);

        Receta encontrada = Service.instance().readReceta(receta); // Llama al Service para buscar por ID

        model.setCurrent(encontrada);
        return encontrada;
    }

    // -------------------- Avanzar Estado --------------------
    public void avanzarEstado(Receta receta) throws Exception {
        if (receta == null) throw new Exception("No hay receta seleccionada");

        String estadoActual = receta.getEstado();
        switch (estadoActual) {
            case "DESPACHADA":
                throw new Exception("La receta esta en estado Despachada, Debe ser Confeccionada Primero por un Medico.");

            case "CONFECCIONADA":
                receta.setEstado("EN_PROCESO");
                break;
            case "EN_PROCESO":
                receta.setEstado("LISTA");
                break;
            case "LISTA":
                receta.setEstado("ENTREGADA");
                break;
            case "ENTREGADA":
                throw new Exception("La receta ya fue entregada");
            default:
                throw new Exception("Estado inválido: " + estadoActual);
        }

        Service.instance().updateReceta(receta);
        model.setCurrent(receta);
        model.setList(Service.instance().findAllRecetas());
    }

    // -------------------- Clear --------------------
    public void clear() {
        model.setCurrent(new Receta());
    }
}