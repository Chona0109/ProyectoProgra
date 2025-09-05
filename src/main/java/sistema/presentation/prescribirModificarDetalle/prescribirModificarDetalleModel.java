package sistema.presentation.prescribirModificarDetalle;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class prescribirModificarDetalleModel {

    public static final String INDICACIONES = "INDICACIONES";
    public static final String CANTIDAD = "CANTIDAD";
    public static final String DIAS = "DIAS";

    private String indicaciones;
    private int cantidad;
    private int dias;

    private PropertyChangeSupport support;

    public prescribirModificarDetalleModel() {
        support = new PropertyChangeSupport(this);
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        String old = this.indicaciones;
        this.indicaciones = indicaciones;
        support.firePropertyChange(INDICACIONES, old, indicaciones);
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        int old = this.cantidad;
        this.cantidad = cantidad;
        support.firePropertyChange(CANTIDAD, old, cantidad);
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        int old = this.dias;
        this.dias = dias;
        support.firePropertyChange(DIAS, old, dias);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }
}
