package sistema.presentation.logIn;

import logic.entities.Farmaceutico;
import sistema.logic.Proxy;
import logic.entities.Usuario;
import sistema.presentation.ThhreadListener;

public class LogInController implements ThhreadListener {
    private final LogInModel model;
    private final loginForm view;

    public LogInController(LogInModel model, loginForm view) {
        this.model = model;
        this.view = view;
        this.view.setController(this);
        this.view.setModel(model);
    }

    public void login(Usuario usuario) throws Exception {
        Usuario logged = Proxy.instance().login(usuario);
        model.setCurrent(logged);
    }

    public void clear() {
        model.setCurrent(new Usuario());
    }

    @Override
    public void refresh() {

    }
}

