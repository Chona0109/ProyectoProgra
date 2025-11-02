package sistema.presentation.prescribirMedicamento;

import sistema.logic.Proxy;
import logic.entities.Medicamento;
import sistema.presentation.ThreadListener;

import java.util.List;

public class prescribirBuscarMedicamentoController implements ThreadListener {

    private prescribirBuscarMedicamentoModel model;

    public prescribirBuscarMedicamentoController(prescribirBuscarMedicamentoModel model) {
        this.model = model;
        model.init();
        loadMedicamentos();
    }

    private void loadMedicamentos() {
        model.setList(Proxy.instance().search(new Medicamento()));
        model.setMode(prescribirBuscarMedicamentoModel.MODE_CREATE);
    }

    public void search(String criterio, String valor) {
        List<Medicamento> resultados;

        if (valor == null || valor.trim().isEmpty()) {
            resultados = Proxy.instance().search(new Medicamento());
        } else if ("codigo".equalsIgnoreCase(criterio)) {
            resultados = Proxy.instance().searchMedicamentoByCodigo(valor.trim());
        } else if ("nombre".equalsIgnoreCase(criterio)) {
            resultados = Proxy.instance().searchMedicamentoByName(valor.trim());
        } else {
            resultados = Proxy.instance().search(new Medicamento());
        }

        model.setList(resultados);
        model.setMode(prescribirBuscarMedicamentoModel.MODE_CREATE);
    }

    public void seleccionarMedicamento(String codigo) {
        try {
            Medicamento m = new Medicamento();
            m.setCodigo(codigo);
            Medicamento encontrado = Proxy.instance().read(m);
            if (encontrado != null) {
                model.setCurrent(encontrado);
                model.setMode(prescribirBuscarMedicamentoModel.MODE_EDIT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        model.setCurrent(new Medicamento());
        model.setMode(prescribirBuscarMedicamentoModel.MODE_CREATE);
    }

    @Override
    public void deliver_message(String message) {
        loadMedicamentos();
    }
}
