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


    public void buscarPorIdPaciente(String idPaciente) {
        if (idPaciente == null || idPaciente.isEmpty()) {
            model.setList(Service.instance().findAllRecetas());
        } else {
            model.setList(Service.instance().searchRecetaByIdPaciente(idPaciente));
        }

        model.setCurrent(new Receta());
    }

    public void avanzarEstado(Receta receta) throws Exception {
        Service.instance().avanzarEstado(receta);
        model.setList(Service.instance().findAllRecetas());
    }

    public void clear() {
        model.setCurrent(new Receta());
        model.setList(Service.instance().findAllRecetas());
    }

    public void actualizarHistorico() {
        model.setList(Service.instance().findAllRecetas());
    }

    public String generarDetallesDe(Receta receta) {
        return Service.instance().generarDetallesReceta(receta);
    }



}