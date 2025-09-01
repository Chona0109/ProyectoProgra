package sistema.presentation.tableModels;

import sistema.logic.entities.Farmaceutico;
import sistema.presentation.AbstractTableModel;

import java.util.List;

public class FarmaceuticosTableModel extends AbstractTableModel<Farmaceutico> {

    public FarmaceuticosTableModel(int[] cols, List<Farmaceutico> rows) {
        super(cols, rows);
    }

    public static final int ID = 0;
    public static final int NOMBRE = 1;

    @Override
    protected void initColNames() {
        colNames = new String[2];
        colNames[ID] = "ID";
        colNames[NOMBRE] = "Nombre";
    }

    @Override
    protected Object getPropetyAt(Farmaceutico e, int col) {
        switch (cols[col]) {
            case ID:
                return e.getId();
            case NOMBRE:
                return e.getNombre();
            default:
                return "";
        }
    }
}
