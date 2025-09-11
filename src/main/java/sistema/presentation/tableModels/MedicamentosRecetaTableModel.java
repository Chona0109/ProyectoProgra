package sistema.presentation.tableModels;

import sistema.logic.entities.MedicamentoDetalle;
import sistema.presentation.AbstractTableModel;

import java.util.List;

public class MedicamentosRecetaTableModel extends AbstractTableModel<MedicamentoDetalle> {

    public static final int MEDICAMENTO = 0;
    public static final int PRESENTACION = 1;
    public static final int CANTIDAD = 2;
    public static final int INDICACIONES = 3;
    public static final int DIAS = 4;

    public MedicamentosRecetaTableModel(int[] cols, List<MedicamentoDetalle> rows) {
        super(cols, rows);
    }

    @Override
    protected void initColNames() {
        colNames = new String[5]; // Crear array con 5 elementos
        colNames[MEDICAMENTO] = "Medicamento";
        colNames[PRESENTACION] = "Presentación";
        colNames[CANTIDAD] = "Cantidad";
        colNames[INDICACIONES] = "Indicaciones";
        colNames[DIAS] = "Duración (días)";
    }

    @Override
    protected Object getPropetyAt(MedicamentoDetalle detalle, int col) {
        // col aquí es el índice de la columna en el array cols[], no el valor directo
        switch (cols[col]) {
            case MEDICAMENTO:
                return detalle.getMedicamento() != null ? detalle.getMedicamento().getNombre() : "";
            case PRESENTACION:
                return detalle.getMedicamento() != null ? detalle.getMedicamento().getPresentacion() : "";
            case CANTIDAD:
                return detalle.getCantidad();
            case INDICACIONES:
                return detalle.getIndicaciones() != null ? detalle.getIndicaciones() : "";
            case DIAS:
                return detalle.getDias();
            default:
                return "";
        }
    }
}
