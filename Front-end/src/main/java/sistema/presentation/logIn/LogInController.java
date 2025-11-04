package sistema.presentation.logIn;

import sistema.logic.Proxy;
import logic.entities.Usuario;
import sistema.presentation.ThreadListener;

public class LogInController implements ThreadListener {

    private final LogInModel model;
    private final loginForm view;

    public LogInController(LogInModel model, loginForm view) {
        this.model = model;
        this.view = view;
        this.view.setController(this);
        this.view.setModel(model);
        model.init();
    }

    public void login(Usuario usuario) throws Exception {
        Usuario logged = Proxy.instance().login(usuario);
        model.setCurrent(logged);
    }

    public void clear() {
        model.setCurrent(new Usuario());
    }

    @Override
    public void deliver_message(String message) {

        System.out.println("Mensaje recibido en login: " + message);
    }
}
