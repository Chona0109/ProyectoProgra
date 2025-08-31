package personas;

import View.View;
import logic.entidades.Usuario;
import logic.Service;

public class Controller {

    View view;
    Persona model;

    public Controller(View view, Persona model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }
    public void read(String id) throws Exception {
        Usuario e = new Usuario();
        e.setId(id);
        model.setCurrent(Service.instance().read(e));
    }

//Vamos a necesitar hacer Un controller para cada lista(Medicos, Farmaceuticas y Paciante )
}
