package app;
import org.jfree.chart.*;
import org.jfree.data.general.DefaultPieDataset;
import javax.swing.*;
import java.sql.SQLException;
import java.util.Map;

public class ExpenseChart {
    public static void showChart(JFrame parent) {
        try {
            Map<String, Double> data = ExpenseDAO.getCategoryTotals();
            DefaultPieDataset dataset = new DefaultPieDataset();

            for (String cat : data.keySet()) {
                dataset.setValue(cat, data.get(cat));
            }

            JFreeChart chart = ChartFactory.createPieChart(
                    "Expenses by Category", dataset, true, true, false);

            ChartPanel chartPanel = new ChartPanel(chart);
            JDialog dialog = new JDialog(parent, "Expense Chart", true);
            dialog.setContentPane(chartPanel);
            dialog.setSize(600, 400);
            dialog.setLocationRelativeTo(parent);
            dialog.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
