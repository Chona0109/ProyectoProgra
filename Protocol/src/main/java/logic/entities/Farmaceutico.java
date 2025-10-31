package logic.entities;

import java.io.Serializable;

public class Farmaceutico extends Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    public Farmaceutico(String id, String nombre) {
        super(id, nombre);
    }

    public Farmaceutico() {
        super();
    }
}