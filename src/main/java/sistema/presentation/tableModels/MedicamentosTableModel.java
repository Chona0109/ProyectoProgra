package sistema.presentation.tableModels;
import sistema.logic.entities.*;
import sistema.presentation.AbstractTableModel;
import java.util.List;

public class MedicamentosTableModel extends AbstractTableModel<Medicamento> {

    public MedicamentosTableModel(int[] cols, List<Medicamento> rows) {
        super(cols, rows);
    }

    public static final int CODIGO = 0;
    public static final int DESCRIPCION = 1;

    @Override
    protected void initColNames() {
        colNames = new String[2];
        colNames[CODIGO] = "Codigo";
        colNames[DESCRIPCION] = "Descripcion";

    }
    @Override
    protected Object getPropetyAt(Medicamento e, int col) {
        switch (cols[col]) {
            case CODIGO:
                return e.getCodigo();
            case DESCRIPCION:
                return e.getDescripcion();
            default:
                return "";
        }
    }

}