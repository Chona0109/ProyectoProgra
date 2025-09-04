package sistema.presentation.changePassword;


import sistema.logic.Service;
import sistema.logic.entities.Usuario;
import sistema.logic.Sesion;

public class ChangePasswordController {
    private final ChangePasswordModel model;
    private final changeForm view;

    public ChangePasswordController(ChangePasswordModel model, changeForm view) {
        this.model = model;
        this.view = view;
        this.view.setController(this);
        this.view.setModel(model);
    }

    public void changePassword(String oldPass, String newPass, String confirmPass) throws Exception {
        Usuario current = Sesion.getUsuario();
        if (current == null) {
            throw new Exception("No hay usuario en sesión.");
        }

        if (!current.getClave().equals(oldPass)) {
            throw new Exception("La contraseña actual no es correcta.");
        }

        if (!newPass.equals(confirmPass)) {
            throw new Exception("La nueva contraseña y su confirmación no coinciden.");
        }

        // Cambiar contraseña en el usuario actual
        current.setClave(newPass);

        // Guardar cambios en el Service
        Service.instance().updateUsuario(current);

        model.setCurrent(current);
    }

    public void clear() {
        model.setCurrent(new Usuario());
    }
}