package sistema.presentation;

import sistema.presentation.Dashboard.DashboardController;
import sistema.presentation.Dashboard.DashboardForm;
import sistema.presentation.Dashboard.DashboardModel;
import sistema.presentation.medicos.MedicosController;
import sistema.presentation.medicos.MedicosForm.MedicosForm;
import sistema.presentation.medicos.MedicosModel;

import sistema.presentation.medicamentos.MedicamentosController;
import sistema.presentation.medicamentos.MedicamentosForm.MedicamentosForm;
import sistema.presentation.medicamentos.MedicamentosModel;

import sistema.presentation.Farmaceutas.FarmaceutasForm.FarmaceutasForm;
import sistema.presentation.Farmaceutas.FarmaceutasController;
import sistema.presentation.Farmaceutas.FarmaceutasModel;

import sistema.presentation.paciente.PacientesForm;
import sistema.presentation.paciente.PacienteController;
import sistema.presentation.paciente.PacienteModel;

import sistema.presentation.historicoRecetas.*;

import javax.swing.*;

public class MenuAdmin extends JFrame {

    private MedicosForm medicosForm;
    private MedicosController medicosController;
    private MedicosModel medicosModel;

    private MedicamentosForm medicamentosForm;
    private MedicamentosController medicamentosController;
    private MedicamentosModel medicamentosModel;

    private FarmaceutasForm farmaceutasForm;
    private FarmaceutasController farmaceutasController;
    private FarmaceutasModel farmaceutasModel;

    private PacientesForm pacientesForm;
    private PacienteController pacientesController;
    private PacienteModel pacientesModel;

    private historicoRecetas historicoRecetas;
    private historicoRecetasModel historicoRecetasModel;
    private historicoRecetasController historicoRecetasController;


    private DashboardModel dashboardModel;
    private DashboardController dashboardController;
    private DashboardForm dashboardForm;

    public MenuAdmin() {
        setTitle("Administrador - Sistema Recetas");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();


        medicosForm = new MedicosForm();
        medicosModel = new MedicosModel();
        medicosController = new MedicosController(medicosForm, medicosModel);
        medicosForm.setController(medicosController);
        medicosForm.setModel(medicosModel);
        tabbedPane.addTab("Médicos", medicosForm.getPanel());


        medicamentosForm = new MedicamentosForm();
        medicamentosModel = new MedicamentosModel();
        medicamentosController = new MedicamentosController(medicamentosForm, medicamentosModel);
        medicamentosForm.setController(medicamentosController);
        medicamentosForm.setModel(medicamentosModel);
        tabbedPane.addTab("Medicamentos", medicamentosForm.getPanel());


        farmaceutasForm = new FarmaceutasForm();
        farmaceutasModel = new FarmaceutasModel();
        farmaceutasController = new FarmaceutasController(farmaceutasForm, farmaceutasModel);
        farmaceutasForm.setController(farmaceutasController);
        farmaceutasForm.setModel(farmaceutasModel);
        tabbedPane.addTab("Farmaceutas", farmaceutasForm.getPanel());


        pacientesForm = new PacientesForm();
        pacientesModel = new PacienteModel();
        pacientesController = new PacienteController(pacientesForm, pacientesModel);
        pacientesForm.setController(pacientesController);
        pacientesForm.setModel(pacientesModel);
        tabbedPane.addTab("Pacientes", pacientesForm.getPanel());

        historicoRecetasModel = new historicoRecetasModel();
        historicoRecetasController = new historicoRecetasController(historicoRecetasModel);
        historicoRecetas = new historicoRecetas(this, historicoRecetasModel, historicoRecetasController);
        tabbedPane.addTab("Histórico", historicoRecetas.getPanel());

        DashboardModel dashboardModel = new DashboardModel();
        DashboardController dashboardController = new DashboardController(dashboardModel);
        DashboardForm dashboardForm = new DashboardForm(dashboardModel, dashboardController);
        tabbedPane.addTab("Dashboard", dashboardForm);


        add(tabbedPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuAdmin().setVisible(true));
    }
}
