package sistema.presentation.Despacho;

import sistema.logic.Service;
import sistema.logic.entities.Receta;
import java.util.List;
import java.util.stream.Collectors;

public class DespachoController {
    private DespachoModel model;

    public DespachoController(DespachoModel model) {
        this.model = model;
        model.setCurrent(new Receta());
        model.setList(Service.instance().findAllRecetas());
    }

    // -------------------- CRUD --------------------

    public void buscarReceta(String id) throws Exception {
        Receta receta = Service.instance().findRecetaById(id);
        model.setCurrent(receta);
    }

    public void buscarPorIdPaciente(String idPaciente) {
        if (idPaciente == null || idPaciente.isEmpty()) {
            model.setList(Service.instance().findAllRecetas());
        } else {
            model.setList(Service.instance().searchRecetaByIdPaciente(idPaciente));
        }

        // Limpiar current cuando se hace una búsqueda nueva
        model.setCurrent(new Receta());
    }

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

        // Actualizar la lista completa después del cambio
        model.setList(Service.instance().findAllRecetas());
    }

    public void clear() {
        model.setCurrent(new Receta());
        model.setList(Service.instance().findAllRecetas());
    }

    public void actualizarHistorico() {
        model.setList(Service.instance().findAllRecetas());
    }
}