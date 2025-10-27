package sistema.presentation.Dashboard;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import sistema.logic.Service;
import sistema.logic.entities.Medicamento;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DashboardForm extends JPanel implements PropertyChangeListener {

    private DashboardModel model;
    private DashboardController controller;

    private JPanel panelPastel;
    private JPanel panelLinea;
    private JComboBox<String> comboMedicamentos;
    private JComboBox<Month> comboMesInicio;
    private JComboBox<Month> comboMesFin;
    private JButton btnActualizar;

    public DashboardForm(DashboardModel model, DashboardController controller) {
        this.model = model;
        this.controller = controller;
        this.model.addPropertyChangeListener(this);

        initComponents();
        cargarFiltros();
        actualizarGraficos();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        panelPastel = new JPanel(new BorderLayout());
        panelLinea = new JPanel(new BorderLayout());

        comboMedicamentos = new JComboBox<>();
        comboMesInicio = new JComboBox<>(Month.values());
        comboMesFin = new JComboBox<>(Month.values());
        btnActualizar = new JButton("Actualizar");

        JPanel panelFiltros = new JPanel();
        panelFiltros.add(new JLabel("Medicamento:"));
        panelFiltros.add(comboMedicamentos);
        panelFiltros.add(new JLabel("Mes Inicio:"));
        panelFiltros.add(comboMesInicio);
        panelFiltros.add(new JLabel("Mes Fin:"));
        panelFiltros.add(comboMesFin);
        panelFiltros.add(btnActualizar);

        JPanel panelGraficos = new JPanel(new GridLayout(1, 2));
        panelGraficos.add(panelPastel);
        panelGraficos.add(panelLinea);

        add(panelFiltros, BorderLayout.NORTH);
        add(panelGraficos, BorderLayout.CENTER);

        btnActualizar.addActionListener(e -> {
            String medicamento = (String) comboMedicamentos.getSelectedItem();
            Month mesInicio = (Month) comboMesInicio.getSelectedItem();
            Month mesFin = (Month) comboMesFin.getSelectedItem();
            controller.aplicarFiltros(medicamento, mesInicio, mesFin);
        });
    }

    private void cargarFiltros() {
        try {
            List<Medicamento> medicamentos = controller.obtenerMedicamentos();
            comboMedicamentos.removeAllItems();
            for (Medicamento m : medicamentos) {
                comboMedicamentos.addItem(m.getNombre());
            }
            if (!medicamentos.isEmpty()) {
                comboMedicamentos.setSelectedIndex(0);
            }

            comboMesInicio.setSelectedIndex(0);
            comboMesFin.setSelectedIndex(Month.DECEMBER.getValue() - 1);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar medicamentos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarGraficos() {
        actualizarGraficoPastel();
        actualizarGraficoLinea();
    }

    private void actualizarGraficoPastel() {
        try {
            DefaultPieDataset pieDataset = new DefaultPieDataset();
            Map<String, Long> recetasPorEstado = model.getRecetasPorEstado();
            recetasPorEstado.forEach(pieDataset::setValue);

            JFreeChart pieChart = ChartFactory.createPieChart(
                    "Recetas por Estado",
                    pieDataset,
                    true, true, false
            );

            panelPastel.removeAll();
            panelPastel.add(new ChartPanel(pieChart), BorderLayout.CENTER);
            panelPastel.revalidate();
            panelPastel.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void actualizarGraficoLinea() {
        try {
            DefaultCategoryDataset lineDataset = new DefaultCategoryDataset();
            Map<Month, Integer> medicamentosPorMes = model.getMedicamentosPorMes();

            for (Month mes : Month.values()) {
                int cantidad = medicamentosPorMes.getOrDefault(mes, 0);
                String nombreMes = mes.getDisplayName(TextStyle.SHORT, Locale.getDefault());
                lineDataset.addValue(cantidad, "Medicamentos", nombreMes);
            }

            JFreeChart lineChart = ChartFactory.createLineChart(
                    "Medicamentos por Mes",
                    "Mes",
                    "Cantidad",
                    lineDataset
            );

            panelLinea.removeAll();
            panelLinea.add(new ChartPanel(lineChart), BorderLayout.CENTER);
            panelLinea.revalidate();
            panelLinea.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (DashboardModel.RECETAS_POR_ESTADO.equals(evt.getPropertyName())
                || DashboardModel.RECETAS_POR_MES.equals(evt.getPropertyName())) {
            actualizarGraficos();
        }
    }
}