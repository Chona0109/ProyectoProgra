package sistema.presentation;

import sistema.presentation.Dashboard.DashboardController;
import sistema.presentation.Dashboard.DashboardForm;
import sistema.presentation.Dashboard.DashboardModel;
import sistema.presentation.historicoRecetas.historicoRecetas;
import sistema.presentation.historicoRecetas.historicoRecetasController;
import sistema.presentation.historicoRecetas.historicoRecetasModel;

import sistema.presentation.Despacho.DespachoForm;
import sistema.presentation.Despacho.DespachoController;
import sistema.presentation.Despacho.DespachoModel;


import javax.swing.*;

public class MenuFarmaceutico extends JFrame {

    private DespachoForm despachoForm;
    private DespachoController despachoController;
    private DespachoModel despachoModel;

    private historicoRecetasModel historicoRecetasModel;
    private historicoRecetasController historicoRecetasController;
    private historicoRecetas historicoRecetasForm;


    private DashboardModel dashboardModel;
    private DashboardController dashboardController;
    private DashboardForm dashboardForm;

    public MenuFarmaceutico() {
        setTitle("Farmacéutico - Sistema Recetas");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();


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