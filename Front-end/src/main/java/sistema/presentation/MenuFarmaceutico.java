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
import sistema.presentation.UsuariosLogeados.UsuariosLogeadosController;
import sistema.presentation.UsuariosLogeados.UsuariosLogeadosForm;
import sistema.presentation.UsuariosLogeados.UsuariosLogeadosModel;

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


    private UsuariosLogeadosForm usuariosLogeadosForm;
    private UsuariosLogeadosModel usuariosLogeadosModel;
    private UsuariosLogeadosController usuariosLogeadosController;

    public MenuFarmaceutico() {
        setTitle("Farmacéutico - Sistema Recetas");
        setSize(1200, 600);
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


        dashboardModel = new DashboardModel();
        dashboardController = new DashboardController(dashboardModel);
        dashboardForm = new DashboardForm(dashboardModel, dashboardController);
        tabbedPane.addTab("Dashboard", dashboardForm);


        usuariosLogeadosModel = new UsuariosLogeadosModel();
        usuariosLogeadosForm = new UsuariosLogeadosForm();
        usuariosLogeadosController = new UsuariosLogeadosController(usuariosLogeadosModel, usuariosLogeadosForm);

        usuariosLogeadosForm.setModel(usuariosLogeadosModel);
        usuariosLogeadosForm.setController(usuariosLogeadosController);


        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                tabbedPane,
                usuariosLogeadosForm.getPanel()
        );
        splitPane.setDividerLocation(900);
        splitPane.setOneTouchExpandable(true);

        add(splitPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuFarmaceutico().setVisible(true));
    }
}