package logic;

import data.ListaMedicos;

public class ServiceMedico {
    private static ServiceMedico theInstance;

    public static ServiceMedico instance() {
        if (theInstance == null) theInstance = new ServiceMedico();
        return theInstance;
    }

    private ListaMedicos data;

    private ServiceMedico() {
        data = new ListaMedicos();
    }

    // =============== PERSONAS ===============
    public void create(Medico e) throws Exception {
        Usuario result = data.getMedicos().stream()
                .filter(i -> i.getId().equals(e.getId()))
                .findFirst()
                .orElse(null);
        if (result == null) {
            data.getMedicos().add(e);
        } else {
            throw new Exception("Medico ya existe");
        }
    }



// SI ALGO FALLA Muy probable sea este metodo:


    public Medico read(Usuario e) throws Exception {
        Medico result = data.getMedicos().stream()
                .filter(i -> i.getId().equals(e.getId()))
                .findFirst()
                .orElse(null);
        if (result != null) {
            return result;
        } else {
            throw new Exception("Medico no existe");
        }
    }
}
