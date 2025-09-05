package sistema.presentation.tableModels;

import sistema.logic.entities.Receta;
import sistema.presentation.AbstractTableModel;

import java.util.List;

public class HistoricoRecetasTableModel extends AbstractTableModel<Receta> {

    public static final int ID = 0;
    public static final int ESTADO = 1;
    public static final int FECHA = 2;
    public static final int PACIENTE = 3;

    public HistoricoRecetasTableModel(int[] cols, List<Receta> rows) {
        super(cols, rows);
    }

    @Override
    protected void initColNames() {
        colNames = new String[4];
        colNames[ID] = "ID";
        colNames[ESTADO] = "Estado";
        colNames[FECHA] = "Fecha Confección";
        colNames[PACIENTE] = "Paciente";
    }

    @Override
    protected Object getPropetyAt(Receta r, int col) {
        switch (cols[col]) {
            case ID: return r.getId();
            case ESTADO: return r.getEstado();
            case FECHA: return r.getFechaConfeccion();
            case PACIENTE: return r.getPaciente() != null ? r.getPaciente().getNombre() : "Sin paciente";
            default: return "";
        }
    }

    public void setRows(List<Receta> rows) {
        this.rows = rows != null ? rows : List.of();
        fireTableDataChanged();
    }
}
