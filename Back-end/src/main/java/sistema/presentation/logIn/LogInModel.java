package sistema.presentation.logIn;

import sistema.logic.entities.Usuario;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class LogInModel {
    private Usuario current;
    private final PropertyChangeSupport support;

    public static final String CURRENT = "current";

    public LogInModel() {
        current = new Usuario();
        support = new PropertyChangeSupport(this);
    }

    public void setCurrent(Usuario u) {
        Usuario old = this.current;
        this.current = u;
        support.firePropertyChange(CURRENT, old, u);
    }

    public Usuario getCurrent() { return current; }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        support.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        support.removePropertyChangeListener(l);
    }
}
