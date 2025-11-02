package sistema.presentation.logIn;

import logic.entities.Usuario;
import sistema.presentation.AbstractModel;

import java.beans.PropertyChangeListener;

public class LogInModel extends AbstractModel {

    private Usuario current;

    public static final String CURRENT = "current";

    public LogInModel() {
        init();
    }

    public void init() {
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

    public void setCurrent(Usuario current) {
        this.current = current != null ? current : new Usuario();
        firePropertyChange(CURRENT);
    }
}
