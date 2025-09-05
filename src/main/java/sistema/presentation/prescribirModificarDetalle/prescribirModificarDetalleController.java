package sistema.presentation.prescribirModificarDetalle;

public class prescribirModificarDetalleController {

    private prescribirModificarDetalleModel model;

    public prescribirModificarDetalleController(prescribirModificarDetalleModel model) {
        this.model = model;
    }

    public void cambiarIndicaciones(String indicaciones) {
        model.setIndicaciones(indicaciones);
    }

    public void cambiarCantidad(int cantidad) {
        model.setCantidad(cantidad);
    }

    public void cambiarDias(int dias) {
        model.setDias(dias);
    }

    public void guardar() {
        System.out.println("Detalle guardado: indicaciones=" + model.getIndicaciones() +
                ", cantidad=" + model.getCantidad() + ", dias=" + model.getDias());
    }
}
