package sistema.presentation.tableModels;

import sistema.logic.entities.Receta;
import sistema.presentation.AbstractTableModel;

import java.util.List;

public class HistoricoRecetasTableModel extends AbstractTableModel<Receta> {

    public static final int ID = 0;
    public static final int ESTADO = 1;
    public static final int FECHA = 2;
    public static final int PACIENTE = 3;
    public static final int ID_PACIENTE = 4;
    public static final int MEDICO = 5;

    public HistoricoRecetasTableModel(int[] cols, List<Receta> rows) {
        super(cols, rows);
    }

    @Override
    protected void initColNames() {
        colNames = new String[6];
        colNames[ID] = "ID";
        colNames[ESTADO] = "Estado";
        colNames[FECHA] = "Fecha Confección";
        colNames[PACIENTE] = "Paciente";
        colNames[ID_PACIENTE] = "ID Paciente";
        colNames[MEDICO] = "Médico";
    }

    @Override
    protected Object getPropetyAt(Receta r, int col) {
        switch (cols[col]) {
            case ID:
                return r.getId();
            case ESTADO:
                return r.getEstado();
            case FECHA:
                return r.getFechaConfeccion();
            case PACIENTE:
                return r.getPaciente() != null ? r.getPaciente().getNombre() : "Sin paciente";
            case ID_PACIENTE:
                return r.getPaciente() != null ? r.getPaciente().getId() : "Sin ID";
            case MEDICO:
                return r.getMedico() != null ? r.getMedico().getNombre() : "Sin médico";
            default:
                return "";
        }
    }

}