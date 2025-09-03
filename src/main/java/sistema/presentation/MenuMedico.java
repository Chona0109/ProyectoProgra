package sistema.presentation;

import sistema.presentation.paciente.PacientesForm;
import sistema.presentation.paciente.PacienteController;
import sistema.presentation.paciente.PacienteModel;

import sistema.presentation.medicamentos.MedicamentosForm.MedicamentosForm;
import sistema.presentation.medicamentos.MedicamentosController;
import sistema.presentation.medicamentos.MedicamentosModel;

import javax.swing.*;

public class MenuMedico extends JFrame {

    private PacientesForm pacientesForm;
    private PacienteController pacientesController;
    private PacienteModel pacientesModel;

    private MedicamentosForm medicamentosForm;
    private MedicamentosController medicamentosController;
    private MedicamentosModel medicamentosModel;

    public MenuMedico() {
        setTitle("Médico - Sistema Recetas");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // PACIENTES
        pacientesForm = new PacientesForm();
        pacientesModel = new PacienteModel();
        pacientesController = new PacienteController(pacientesForm, pacientesModel);
        pacientesForm.setController(pacientesController);
        pacientesForm.setModel(pacientesModel);
        tabbedPane.addTab("Pacientes", pacientesForm.getPanel());

        // MEDICAMENTOS
        medicamentosForm = new MedicamentosForm();
        medicamentosModel = new MedicamentosModel();
        medicamentosController = new MedicamentosController(medicamentosForm, medicamentosModel);
        medicamentosForm.setController(medicamentosController);
        medicamentosForm.setModel(medicamentosModel);
        tabbedPane.addTab("Medicamentos", medicamentosForm.getPanel());

        // DASHBOARD (placeholder)
        JPanel panelDashboard = new JPanel();
        panelDashboard.add(new JLabel("Gráficos de indicadores del médico"));
        tabbedPane.addTab("Dashboard", panelDashboard);

        add(tabbedPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuMedico().setVisible(true));
    }
}