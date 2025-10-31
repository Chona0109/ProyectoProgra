package sistema.presentation;

import sistema.presentation.Dashboard.DashboardController;
import sistema.presentation.Dashboard.DashboardForm;
import sistema.presentation.Dashboard.DashboardModel;
import sistema.presentation.prescribirReceta.prescribirReceta;
import sistema.presentation.prescribirReceta.prescribirRecetaModel;
import sistema.presentation.prescribirReceta.prescribirRecetaController;

import sistema.presentation.historicoRecetas.historicoRecetas;
import sistema.presentation.historicoRecetas.historicoRecetasModel;
import sistema.presentation.historicoRecetas.historicoRecetasController;

import javax.swing.*;

public class MenuMedico extends JFrame {

    private prescribirReceta prescribirRecetaForm;
    private prescribirRecetaModel prescribirRecetaModel;
    private prescribirRecetaController prescribirRecetaController;

    private historicoRecetas historicoRecetasForm;
    private historicoRecetasModel historicoRecetasModel;
    private historicoRecetasController historicoRecetasController;


    private DashboardModel dashboardModel;
    private DashboardController dashboardController;
    private DashboardForm dashboardForm;

    public MenuMedico() {
        setTitle("Médico - Sistema Recetas");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();


        prescribirRecetaModel = new prescribirRecetaModel();
        prescribirRecetaController = new prescribirRecetaController(prescribirRecetaModel);
        prescribirRecetaForm = new prescribirReceta(this, prescribirRecetaModel, prescribirRecetaController);
        tabbedPane.addTab("Prescribir Receta", prescribirRecetaForm.getPanel());




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
        SwingUtilities.invokeLater(() -> new MenuMedico().setVisible(true));
    }
}
