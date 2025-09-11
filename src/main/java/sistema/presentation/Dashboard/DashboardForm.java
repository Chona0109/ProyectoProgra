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
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
import java.util.List;
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
                    true,   // legend
                    true,   // tooltips
                    false   // URLs
            );

            // Actualizar el panel de pastel
            panelPastel.removeAll();
            panelPastel.setLayout(new BorderLayout());
            panelPastel.add(new ChartPanel(pieChart), BorderLayout.CENTER);
            panelPastel.revalidate();
            panelPastel.repaint();

            // ------------------ Gráfico de líneas: Medicamentos por mes ------------------
            DefaultCategoryDataset lineDataset = new DefaultCategoryDataset();

            Map<Month, Integer> medicamentosPorMes = new HashMap<>();

            for (Receta r : recetas) {
                if (r.getFechaConfeccion() != null) {
                    Month mes = r.getFechaConfeccion().getMonth();
                    int cantidad = (r.getMedicamentos() != null) ? r.getMedicamentos().size() : 0;
                    medicamentosPorMes.merge(mes, cantidad, Integer::sum);
                }
            }

            // Ordenar los meses correctamente (Ene, Feb, ..., Dic)
            Arrays.stream(Month.values()).forEach(mes -> {
                Integer cantidad = medicamentosPorMes.getOrDefault(mes, 0);
                String nombreMes = mes.getDisplayName(TextStyle.SHORT, Locale.getDefault());
                lineDataset.addValue(cantidad, "Medicamentos", nombreMes);
            });

            JFreeChart lineChart = ChartFactory.createLineChart(
                    "Medicamentos por Mes",
                    "Mes",
                    "Cantidad de Medicamentos",
                    lineDataset
            );

            // Actualizar el panel de líneas
            panelLinea.removeAll();
            panelLinea.setLayout(new BorderLayout());
            panelLinea.add(new ChartPanel(lineChart), BorderLayout.CENTER);
            panelLinea.revalidate();
            panelLinea.repaint();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar los gráficos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}