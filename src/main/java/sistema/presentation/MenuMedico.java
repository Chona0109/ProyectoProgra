package sistema.presentation;

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

    public MenuMedico() {
        setTitle("Médico - Sistema Recetas");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // PRESCRIBIR RECETA
        prescribirRecetaModel = new prescribirRecetaModel();
        prescribirRecetaController = new prescribirRecetaController(prescribirRecetaModel);
        prescribirRecetaForm = new prescribirReceta(this, prescribirRecetaModel, prescribirRecetaController);
        tabbedPane.addTab("Prescribir Receta", prescribirRecetaForm.getPanel());


        JPanel panelDashboard = new JPanel();
        panelDashboard.add(new JLabel("Aquí van los gráficos de indicadores"));
        tabbedPane.addTab("Dashboard", panelDashboard);

        // HISTÓRICO DE RECETAS
        historicoRecetasModel = new historicoRecetasModel();
        historicoRecetasController = new historicoRecetasController(historicoRecetasModel);
        historicoRecetasForm = new historicoRecetas(this, historicoRecetasModel, historicoRecetasController);
        tabbedPane.addTab("Histórico", historicoRecetasForm.getPanel());

        add(tabbedPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuMedico().setVisible(true));
    }
}
