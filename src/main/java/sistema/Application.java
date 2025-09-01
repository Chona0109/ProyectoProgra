package sistema;

import sistema.presentation.medicos.MedicosForm.MedicosForm;
import sistema.presentation.medicos.MedicosController;
import sistema.presentation.medicos.MedicosModel;

import javax.swing.*;

public class Application {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {}

        MedicosForm view = new MedicosForm();
        MedicosModel model = new MedicosModel();
        MedicosController controller = new MedicosController(view, model);

        view.setController(controller);
        view.setModel(model);

        JFrame window = new JFrame();
        window.setSize(800, 600);
        window.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        window.setTitle("Gestión de Médicos");
        window.setContentPane(view.getPanel());
        window.setVisible(true);
    }
}
