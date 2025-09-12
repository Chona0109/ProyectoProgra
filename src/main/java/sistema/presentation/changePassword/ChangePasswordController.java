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

    public void changePassword(String userId, String oldPass, String newPass, String confirmPass) throws Exception {
        if (userId == null || userId.isEmpty()) {
            throw new Exception("Debe ingresar su ID de usuario.");
        }


        Usuario user = Service.instance().findUserById(userId);
        if (user == null) {
            throw new Exception("Usuario no encontrado.");
        }


        if (!user.getClave().equals(oldPass)) {
            throw new Exception("La contraseña actual es incorrecta.");
        }


        if (!newPass.equals(confirmPass)) {
            throw new Exception("La nueva contraseña y su confirmación no coinciden.");
        }


        user.setClave(newPass);
        Service.instance().updateUsuario(user);


        model.setCurrent(user);
    }

    public void clear() {
        model.setCurrent(new Usuario());
    }
}