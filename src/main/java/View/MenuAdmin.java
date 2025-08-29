package View;

import logic.Farmaceutico;

import javax.swing.*;

public class MenuAdmin extends JFrame {

    public MenuAdmin() {
        setTitle("Administrador - Sistema Recetas");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Panel de Médicos
        MedicosForm medicosForm = new MedicosForm();
        tabbedPane.addTab("Médicos", medicosForm.getPanel());

        // Panel de Farmaceutas
        FarmaceutasForm farmaceutasForm = new FarmaceutasForm();
        tabbedPane.addTab("Farmaceutas", farmaceutasForm.getPanel());

        // Panel de Pacientes
        PacientesForm pacientesForm = new PacientesForm();
        tabbedPane.addTab("Pacientes", pacientesForm.getPanel());

        // Panel de Medicamentos
        MedicamentosForm medicamentosForm = new MedicamentosForm();
        tabbedPane.addTab("Medicamentos", medicamentosForm.getPanel());

        // Panel de Dashboard
        JPanel panelDashboard = new JPanel();
        panelDashboard.add(new JLabel("Aquí van los gráficos de indicadores"));
        tabbedPane.addTab("Dashboard", panelDashboard);

        // Panel de Histórico
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
