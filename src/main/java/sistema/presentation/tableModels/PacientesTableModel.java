package sistema.presentation.tableModels;

import sistema.logic.entities.Paciente;
import sistema.presentation.AbstractTableModel;

import java.util.List;

public class PacientesTableModel extends AbstractTableModel<Paciente> {

    public PacientesTableModel(int[] cols, List<Paciente> rows) {
        super(cols, rows);
    }

    public static final int ID = 0;
    public static final int NOMBRE = 1;
    public static final int FECHA = 2;
    public static final int TELEFONO = 3;

    @Override
    protected void initColNames() {
        colNames = new String[4];
        colNames[ID] = "ID";
        colNames[NOMBRE] = "Nombre";
        colNames[FECHA] = "Fecha Nacimiento";
        colNames[TELEFONO] = "Teléfono";
    }

    @Override
    protected Object getPropetyAt(Paciente p, int col) {
        switch (cols[col]) {
            case ID:
                return p.getId();
            case NOMBRE:
                return p.getNombre();
            case FECHA:
                return p.getFechaNacimiento() != null ? p.getFechaNacimiento().toString() : "";
            case TELEFONO:
                return p.getTelefono();
            default:
                return "";
        }
    }
}
