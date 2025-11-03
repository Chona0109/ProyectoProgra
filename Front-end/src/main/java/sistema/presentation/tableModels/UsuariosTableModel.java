package sistema.presentation.tableModels;

import logic.entities.Usuario;
import sistema.presentation.AbstractTableModel;

import java.util.List;

public class UsuariosTableModel extends AbstractTableModel<Usuario> {

    public static final int ID = 0;
    public static final int NOMBRE = 1;

    public UsuariosTableModel(int[] cols, List<Usuario> rows) {
        super(cols, rows);
    }

    @Override
    protected void initColNames() {
        colNames = new String[2];
        colNames[ID] = "ID Usuario";
        colNames[NOMBRE] = "Nombre";
    }

    @Override
    protected Object getPropetyAt(Usuario usuario, int col) {
        switch (cols[col]) {
            case ID:
                return usuario.getId();
            case NOMBRE:
                return usuario.getNombre();
            default:
                return "";
        }
    }
}