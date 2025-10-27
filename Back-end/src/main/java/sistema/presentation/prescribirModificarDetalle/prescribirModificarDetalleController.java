package sistema.presentation.prescribirModificarDetalle;

import sistema.logic.entities.MedicamentoDetalle;

public class prescribirModificarDetalleController {

    private prescribirModificarDetalleModel model;

    public prescribirModificarDetalleController(prescribirModificarDetalleModel model) {
        this.model = model;
    }

    public void setCurrentDetalle(MedicamentoDetalle detalle) {
        model.setCurrent(detalle);
    }

    public void cambiarCantidad(int cantidad) {
        model.setCantidad(cantidad);
    }

    public void cambiarDias(int dias) {
        model.setDias(dias);
    }

    public void cambiarIndicaciones(String indicaciones) {
        model.setIndicaciones(indicaciones);
    }
}
