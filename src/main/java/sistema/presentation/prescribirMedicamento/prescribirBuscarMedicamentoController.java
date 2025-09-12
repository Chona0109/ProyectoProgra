package sistema.presentation.prescribirMedicamento;

import sistema.logic.Service;
import sistema.logic.entities.Medicamento;

import java.util.List;

public class prescribirBuscarMedicamentoController {

    private prescribirBuscarMedicamentoModel model;

    public prescribirBuscarMedicamentoController(prescribirBuscarMedicamentoModel model) {
        this.model = model;
        model.setList(Service.instance().findAllMedicamentos());
    }

    public void search(String criterio, String valor) {
        List<Medicamento> resultados;

        if (valor == null || valor.trim().isEmpty()) {
            resultados = Service.instance().findAllMedicamentos();
        } else if ("codigo".equalsIgnoreCase(criterio)) {
            resultados = Service.instance().searchMedicamentoByCodigo(valor.trim());
        } else if ("Nombre".equalsIgnoreCase(criterio)) {
            resultados = Service.instance().searchMedicamentoByName(valor.trim());
        } else {
            resultados = Service.instance().findAllMedicamentos();
        }

        model.setList(resultados);
    }

    public void seleccionarMedicamento(String codigo) {
        try {
            Medicamento m = new Medicamento();
            m.setCodigo(codigo);
            Medicamento encontrado = Service.instance().read(m);
            if (encontrado != null) {
                model.setCurrent(encontrado);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
