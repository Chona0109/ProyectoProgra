package sistema.presentation.Despacho;

import sistema.logic.Proxy;
import logic.entities.Receta;
import sistema.presentation.ThhreadListener;
public class DespachoController implements ThhreadListener {
    private DespachoModel model;

    public DespachoController(DespachoModel model) {
        this.model = model;
        model.setCurrent(new Receta());
        model.setList(Proxy.instance().search(new Receta()));
    }


    public void buscarPorIdPaciente(String idPaciente) {
        if (idPaciente == null || idPaciente.isEmpty()) {
            model.setList(Proxy.instance().search(new Receta()));
        } else {
            model.setList(Proxy.instance().searchRecetaByIdPaciente(idPaciente));
        }

        model.setCurrent(new Receta());
    }

    public void avanzarEstado(Receta receta) throws Exception {
        Proxy.instance().avanzarEstado(receta);
        model.setList(Proxy.instance().search(new Receta()));
    }

    public void clear() {
        model.setCurrent(new Receta());
        model.setList(Proxy.instance().search(new Receta()));
    }


    public String generarDetallesDe(Receta receta) {
        return Proxy.instance().generarDetallesReceta(receta);
    }

    @Override
    public void refresh() {
        try {
            model.setList(Proxy.instance().search(new Receta()));
        } catch (Exception e) {}
    }



}