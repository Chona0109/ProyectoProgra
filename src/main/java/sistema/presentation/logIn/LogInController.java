package sistema.presentation.logIn;

import sistema.logic.Service;
import sistema.logic.entities.Usuario;

public class LogInController {
    private final LogInModel model;
    private final loginForm view;

    public LogInController(LogInModel model, loginForm view) {
        this.model = model;
        this.view = view;
        this.view.setController(this);
        this.view.setModel(model);
    }

    public void login(Usuario usuario) throws Exception {
        Service.instance().login(usuario);
        model.setCurrent(usuario);
    }

    public void clear() {
        model.setCurrent(new Usuario());
    }
}
