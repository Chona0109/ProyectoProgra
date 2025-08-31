package personas;
import java.beans.PropertyChangeListener;

import logic.entidades.Usuario;

public class Persona extends AbstractModel {
    Usuario current;

    public static final String CURRENT = "current";

    public Persona() {

        current = new Usuario();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
    }

    public Usuario getCurrent() {
        return current;
    }

    public void setCurrent(Usuario u) {
        this.current = u;
        firePropertyChange(CURRENT);
    }

}