package personas;

import View.View;
import logic.Medico;
import logic.Usuario;
import logic.ServiceMedico;

public class Controller {

    View view;
    Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }
    public void read(String id) throws Exception {
        Usuario e = new Usuario();
        e.setId(id);
        model.setCurrent(ServiceMedico.instance().read(e));
    }

//Vamos a necesitar hacer Un controller para cada lista(Medicos, Farmaceuticas y Paciante )
}
