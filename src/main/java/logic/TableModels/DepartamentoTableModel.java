package logic.TableModels;

import logic.entidades.Departamento;
import logic.entidades.Usuario;
import personas.AbstractTableModel;

import java.util.List;

public class DepartamentoTableModel extends AbstractTableModel<Departamento> implements javax.swing.table.TableModel {
    public DepartamentoTableModel(int[] cols, List<Departamento> rows) {
        super(cols, rows);
    }

    public static final int CODIGO = 0;
    public static final int NOMBRE = 1;

    @Override
    protected void initColNames() {
        colNames = new String[2];
        colNames[CODIGO] = "Codigo";
        colNames[NOMBRE] = "Nombre";

    }
    @Override
    protected Object getPropetyAt(Departamento e, int col) {
        switch (cols[col]) {
            case CODIGO:
                return e.getCodigo();
            case NOMBRE:
                return e.getNombre();
            default:
                return "";
        }
    }

}