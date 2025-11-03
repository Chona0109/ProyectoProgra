package sistema.presentation.UsuariosLogeados;

import logic.entities.Usuario;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class UsuariosLogeadosModel {
    public static final String LIST = "list";
    public static final String CURRENT = "current";

    private List<Usuario> list;
    private Usuario current;
    private PropertyChangeSupport propertySupport;


    public UsuariosLogeadosModel() {
        this.list = new ArrayList<>();
        this.current = new Usuario();
        this.propertySupport = new PropertyChangeSupport(this);
    }

    public void init() {
        setList(new ArrayList<>());
        setCurrent(new Usuario());
    }

    public List<Usuario> getList() {
        return list;
    }

    public void setList(List<Usuario> list) {
        List<Usuario> oldList = this.list;
        this.list = list;
        propertySupport.firePropertyChange(LIST, oldList, this.list);
    }

    public Usuario getCurrent() {
        return current;
    }

    public void setCurrent(Usuario current) {
        Usuario oldCurrent = this.current;
        this.current = current;
        propertySupport.firePropertyChange(CURRENT, oldCurrent, this.current);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }
}