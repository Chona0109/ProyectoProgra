package View;

import logic.controllers.MedicosController;
import logic.models.MedicosModel;

import javax.swing.*;

public class MenuAdmin extends JFrame {

    private MedicosForm medicosForm;
    private MedicosController medicosController;
    private MedicosModel medicosModel;

    public MenuAdmin() {
        setTitle("Administrador - Sistema Recetas");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // ===================== MÉDICOS ======================
        medicosForm = new MedicosForm();
        medicosModel = new MedicosModel();
        medicosController = new MedicosController(medicosForm, medicosModel);

        medicosForm.setController(medicosController);
        medicosForm.setModel(medicosModel);

        tabbedPane.addTab("Médicos", medicosForm.getPanel());

        // ===================== FARMACEUTAS ==================
        JPanel farmaceutasForm = new JPanel(); // luego integras tu FarmaceutasForm
        farmaceutasForm.add(new JLabel("Aquí va el formulario de Farmaceutas"));
        tabbedPane.addTab("Farmaceutas", farmaceutasForm);

        // ===================== PACIENTES ====================
        JPanel pacientesForm = new JPanel(); // luego integras tu PacientesForm
        pacientesForm.add(new JLabel("Aquí va el formulario de Pacientes"));
        tabbedPane.addTab("Pacientes", pacientesForm);

        // ===================== MEDICAMENTOS =================
        JPanel medicamentosForm = new JPanel(); // luego integras tu MedicamentosForm
        medicamentosForm.add(new JLabel("Aquí va el formulario de Medicamentos"));
        tabbedPane.addTab("Medicamentos", medicamentosForm);

        // ===================== DASHBOARD ===================
        JPanel panelDashboard = new JPanel();
        panelDashboard.add(new JLabel("Aquí van los gráficos de indicadores"));
        tabbedPane.addTab("Dashboard", panelDashboard);

        // ===================== HISTÓRICO ===================
        JPanel panelHistorico = new JPanel();
        panelHistorico.add(new JLabel("Aquí va el histórico de recetas"));
        tabbedPane.addTab("Histórico", panelHistorico);

        // Agregar el tabbedPane al frame
        add(tabbedPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuAdmin().setVisible(true));
    }
}
