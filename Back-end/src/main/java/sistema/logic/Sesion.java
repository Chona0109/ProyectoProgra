package sistema.logic;

import sistema.logic.entities.Usuario;

public class Sesion {
    private static Usuario usuario;

    public static void setUsuario(Usuario u) {
        usuario = u;
    }

    public static Usuario getUsuario() {
        return usuario;
    }

    public static boolean isLoggedIn() {
        return usuario != null;
    }

    public static void logout() {
        usuario = null;
    }
}
