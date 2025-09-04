package sistema.presentation.tableModels;

import sistema.logic.entities.Medicamento;
import sistema.presentation.AbstractTableModel;
import java.util.List;

public class MedicamentosTableModel extends AbstractTableModel<Medicamento> {

    public MedicamentosTableModel(int[] cols, List<Medicamento> rows) {
        super(cols, rows);
    }

    public static final int CODIGO = 0;
    public static final int NOMBRE = 1;
    public static final int PRESENTACION = 2;  // Nuevo campo

    @Override
    protected void initColNames() {
        colNames = new String[3];  // Cambiar a 3 columnas
        colNames[CODIGO] = "Código";
        colNames[NOMBRE] = "Nombre";
        colNames[PRESENTACION] = "Presentación";
    }

    @Override
    protected Object getPropetyAt(Medicamento e, int col) {
        switch (cols[col]) {
            case CODIGO:
                return e.getCodigo();
            case NOMBRE:
                return e.getNombre();
            case PRESENTACION:
                return e.getPresentacion();
            default:
                return "";
        }
    }
}
