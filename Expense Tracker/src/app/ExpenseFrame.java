package app;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.SQLException;

public class ExpenseFrame extends JFrame {
    private JTable table;
    private DefaultTableModel model;

    public ExpenseFrame() {
        setTitle("Expense Tracker");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table
        model = new DefaultTableModel(new String[]{"ID","Amount","Category","Date","Description"}, 0);
        table = new JTable(model);
        loadData();
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons
        JPanel panel = new JPanel();
        JButton addBtn = new JButton("Add Expense");
        JButton updateBtn = new JButton("Update Expense");
        JButton delBtn = new JButton("Delete Expense");
        JButton chartBtn = new JButton("Show Chart");

        panel.add(addBtn);
        panel.add(updateBtn);
        panel.add(delBtn);
        panel.add(chartBtn);
        add(panel, BorderLayout.SOUTH);

        // Actions
        addBtn.addActionListener(e -> addExpense());
        updateBtn.addActionListener(e -> updateExpense());
        delBtn.addActionListener(e -> deleteExpense());
        chartBtn.addActionListener(e -> ExpenseChart.showChart(this));
    }

    private void loadData() {
        try {
            model.setRowCount(0);
            for (Expense exp : ExpenseDAO.getAllExpenses()) {
                model.addRow(new Object[]{
                        exp.getId(), exp.getAmount(), exp.getCategory(),
                        exp.getDate(), exp.getDescription()});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void addExpense() {
        try {
            double amount = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter Amount:"));
            String category = JOptionPane.showInputDialog(this, "Enter Category:");
            String date = JOptionPane.showInputDialog(this, "Enter Date (YYYY-MM-DD):");
            String desc = JOptionPane.showInputDialog(this, "Enter Description:");

            ExpenseDAO.addExpense(new Expense(0, amount, category, date, desc));
            loadData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input!");
        }
    }


    private void updateExpense() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            try {
                int id = (int) model.getValueAt(row, 0);
                double amount = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter New Amount:", model.getValueAt(row,1)));
                String category = JOptionPane.showInputDialog(this, "Enter New Category:", model.getValueAt(row,2));
                String date = JOptionPane.showInputDialog(this, "Enter New Date (YYYY-MM-DD):", model.getValueAt(row,3));
                String desc = JOptionPane.showInputDialog(this, "Enter New Description:", model.getValueAt(row,4));

                ExpenseDAO.updateExpense(new Expense(id, amount, category, date, desc));
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a row to update.");
        }
    }


    private void deleteExpense() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int id = (int) model.getValueAt(row, 0);
            try {
                ExpenseDAO.deleteExpense(id);
                loadData();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a row to delete.");
        }
    }
}
