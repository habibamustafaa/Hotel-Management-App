import javax.swing.*;
import java.awt.*;
import manager.EmployeeManager;
import utils.Validator;

public class EmployeeGUI extends JFrame {
    private EmployeeManager employeeManager;

    public EmployeeGUI(JFrame parent, EmployeeManager employeeManager) {
        this.employeeManager = employeeManager;
        setTitle("Employee Management");
        setSize(400, 300);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        JButton addBtn = new JButton("Add Employee");
        JButton viewBtn = new JButton("View Employees");
        JButton deleteBtn = new JButton("Delete Employee");
        JButton backBtn = new JButton("Back");

        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        panel.add(addBtn);
        panel.add(viewBtn);
        panel.add(deleteBtn);
        panel.add(backBtn);

        add(panel);

        addBtn.addActionListener(e -> {
            JTextField idField = new JTextField();
            JTextField nameField = new JTextField();
            JTextField emailField = new JTextField();
            JTextField phoneField = new JTextField();

            Object[] fields = {
                "Employee ID:", idField,
                "Name:", nameField,
                "Email:", emailField,
                "Phone:", phoneField
            };

            int result = JOptionPane.showConfirmDialog(this, fields, "Add Employee", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    String name = nameField.getText();
                    String email = emailField.getText();
                    String phone = phoneField.getText();

                    if (!Validator.isValidEmail(email)) {
                        JOptionPane.showMessageDialog(this, "Invalid email format!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (!Validator.isValidPhone(phone)) {
                        JOptionPane.showMessageDialog(this, "Invalid phone format (10-15 digits)!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    employeeManager.addEmployee(id, name, email, phone);
                    JOptionPane.showMessageDialog(this, "Employee added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid ID format!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        viewBtn.addActionListener(e -> employeeManager.listEmployees());

        deleteBtn.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog(this, "Enter Employee ID to delete:");
            try {
                int id = Integer.parseInt(idStr);
                employeeManager.deleteEmployeeById(id);
                JOptionPane.showMessageDialog(this, "Employee deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backBtn.addActionListener(e -> dispose());

        setVisible(true);
    }
}