package sistema.presentation;

import sistema.presentation.Dashboard.DashboardController;
import sistema.presentation.Dashboard.DashboardForm;
import sistema.presentation.Dashboard.DashboardModel;
import sistema.presentation.historicoRecetas.historicoRecetas;
import sistema.presentation.historicoRecetas.historicoRecetasController;
import sistema.presentation.historicoRecetas.historicoRecetasModel;
import sistema.presentation.medicamentos.MedicamentosForm.MedicamentosForm;
import sistema.presentation.medicamentos.MedicamentosController;
import sistema.presentation.medicamentos.MedicamentosModel;

import sistema.presentation.paciente.PacientesForm;
import sistema.presentation.paciente.PacienteController;
import sistema.presentation.paciente.PacienteModel;

import sistema.presentation.Despacho.DespachoForm;
import sistema.presentation.Despacho.DespachoController;
import sistema.presentation.Despacho.DespachoModel;


import javax.swing.*;

public class MenuFarmaceutico extends JFrame {

    private MedicamentosForm medicamentosForm;
    private MedicamentosController medicamentosController;
    private MedicamentosModel medicamentosModel;

    private PacientesForm pacientesForm;
    private PacienteController pacientesController;
    private PacienteModel pacientesModel;

    private DespachoForm despachoForm;
    private DespachoController despachoController;
    private DespachoModel despachoModel;

    private historicoRecetasModel historicoRecetasModel;
    private historicoRecetasController historicoRecetasController;
    private historicoRecetas historicoRecetasForm;

    // Dashboard
    private DashboardModel dashboardModel;
    private DashboardController dashboardController;
    private DashboardForm dashboardForm;

    public MenuFarmaceutico() {
        setTitle("Farmacéutico - Sistema Recetas");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();


        medicamentosForm = new MedicamentosForm();
        medicamentosModel = new MedicamentosModel();
        medicamentosController = new MedicamentosController(medicamentosForm, medicamentosModel);
        medicamentosForm.setController(medicamentosController);
        medicamentosForm.setModel(medicamentosModel);
        tabbedPane.addTab("Medicamentos", medicamentosForm.getPanel());


        pacientesForm = new PacientesForm();
        pacientesModel = new PacienteModel();
        pacientesController = new PacienteController(pacientesForm, pacientesModel);
        pacientesForm.setController(pacientesController);
        pacientesForm.setModel(pacientesModel);
        tabbedPane.addTab("Pacientes", pacientesForm.getPanel());



        despachoModel = new DespachoModel();
        despachoController = new DespachoController(despachoModel);
        despachoForm = new DespachoForm(this, despachoModel, despachoController);
        tabbedPane.addTab("Despacho", despachoForm.getPanel());


        historicoRecetasModel = new historicoRecetasModel();
        historicoRecetasController = new historicoRecetasController(historicoRecetasModel);
        historicoRecetasForm = new historicoRecetas(this, historicoRecetasModel, historicoRecetasController);
        tabbedPane.addTab("Histórico", historicoRecetasForm.getPanel());



        DashboardModel dashboardModel = new DashboardModel();
        DashboardController dashboardController = new DashboardController(dashboardModel);
        DashboardForm dashboardForm = new DashboardForm(dashboardModel, dashboardController);
        tabbedPane.addTab("Dashboard", dashboardForm);


        add(tabbedPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuFarmaceutico().setVisible(true));
    }
}