package sistema.presentation.prescribirMedicamento;

import logic.entities.Medico;
import sistema.logic.Proxy;
import logic.entities.Medicamento;
import sistema.presentation.ThhreadListener;
import java.util.List;

public class prescribirBuscarMedicamentoController implements ThhreadListener {

    private prescribirBuscarMedicamentoModel model;

    public prescribirBuscarMedicamentoController(prescribirBuscarMedicamentoModel model) {
        this.model = model;
        model.setList(Proxy.instance().search(new Medicamento()));
    }

    public void search(String criterio, String valor) {
        List<Medicamento> resultados;

        if (valor == null || valor.trim().isEmpty()) {
            resultados = Proxy.instance().search(new Medicamento());
        } else if ("codigo".equalsIgnoreCase(criterio)) {
            resultados = Proxy.instance().searchMedicamentoByCodigo(valor.trim());
        } else if ("Nombre".equalsIgnoreCase(criterio)) {
            resultados = Proxy.instance().searchMedicamentoByName(valor.trim());
        } else {
            resultados = Proxy.instance().search(new Medicamento());
        }

        model.setList(resultados);
    }

    public void seleccionarMedicamento(String codigo) {
        try {
            Medicamento m = new Medicamento();
            m.setCodigo(codigo);
            Medicamento encontrado = Proxy.instance().read(m);
            if (encontrado != null) {
                model.setCurrent(encontrado);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void refresh() {
        try {
            model.setList(Proxy.instance().search(new Medicamento()));
        } catch (Exception e) {}
    }
}
