package sistema.presentation.Dashboard;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import sistema.logic.Service;
import sistema.logic.entities.Receta;

import javax.swing.*;
import java.awt.*;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class DashboardForm extends JPanel {

    private DashboardModel model;
    private DashboardController controller;

    private JPanel panelPastel;
    private JPanel panelLinea;

    public DashboardForm(DashboardModel model, DashboardController controller) {
        this.model = model;
        this.controller = controller;

        setLayout(new GridLayout(1, 2));

        panelPastel = new JPanel(new BorderLayout());
        panelLinea = new JPanel(new BorderLayout());

        add(panelPastel);
        add(panelLinea);

        updateCharts();
    }

    public void updateCharts() {
        try {
            // Obtener todas las recetas
            List<Receta> recetas = Service.instance().findAllRecetas();

            // ------------------ Gráfico de pastel: Recetas por estado ------------------
            DefaultPieDataset pieDataset = new DefaultPieDataset();
            Map<String, Long> estadoCount = recetas.stream()
                    .collect(Collectors.groupingBy(Receta::getEstado, Collectors.counting()));
            estadoCount.forEach(pieDataset::setValue);

            JFreeChart pieChart = ChartFactory.createPieChart(
                    "Recetas por Estado",
                    pieDataset,
                    true,
                    true,
                    false
            );

            panelPastel.removeAll();
            panelPastel.add(new ChartPanel(pieChart), BorderLayout.CENTER);
            panelPastel.revalidate();
            panelPastel.repaint();

            // ------------------ Gráfico de línea: Medicamentos por mes ------------------
            DefaultCategoryDataset lineDataset = new DefaultCategoryDataset();

            recetas.forEach(r -> {
                if (r.getFechaConfeccion() != null) {
                    String mes = r.getFechaConfeccion().getMonth()
                            .getDisplayName(TextStyle.SHORT, Locale.getDefault());
                    int cantidad = r.getMedicamentos() != null ? r.getMedicamentos().size() : 0;
                    lineDataset.addValue(cantidad, "Medicamentos", mes);
                }
            });

            JFreeChart lineChart = ChartFactory.createLineChart(
                    "Medicamentos por Mes",
                    "Mes",
                    "Cantidad de Medicamentos",
                    lineDataset
            );

            panelLinea.removeAll();
            panelLinea.add(new ChartPanel(lineChart), BorderLayout.CENTER);
            panelLinea.revalidate();
            panelLinea.repaint();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los gráficos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}