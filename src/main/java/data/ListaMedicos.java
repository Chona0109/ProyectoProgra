package data;

import logic.Medico;
import java.util.ArrayList;
import java.util.List;

public class ListaMedicos {
    private List<Medico> medicos;

    public ListaMedicos() {
        medicos = new ArrayList<>();
        medicos.add(new Medico("111", "Juan Perez", "123"));
        medicos.add(new Medico("222", "Maria Lopez", "123"));
    }

    public List<Medico> getMedicos() {
        return medicos;
    }
}

