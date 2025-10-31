package logic.entities;

import java.io.Serializable;

public class Administrador extends Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    public Administrador(String id, String nombre) {
        super(id, nombre);
    }

    public Administrador() {
        super();
    }
}