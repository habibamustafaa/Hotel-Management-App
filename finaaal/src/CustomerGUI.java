import javax.swing.*;
import java.awt.*;
import manager.CustomerManager;
import model.Room;
import model.Service;
import utils.Validator;

public class CustomerGUI extends JFrame {
    private CustomerManager customerManager;
    private Room room1 = new Room(101, "Single", true, 150);
    private Room room2 = new Room(102, "Double", true, 200);

    public CustomerGUI(JFrame parent, CustomerManager customerManager) {
        this.customerManager = customerManager;
        setTitle("Customer Management");
        setSize(400, 300);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        JButton addBtn = new JButton("Add Customer");
        JButton viewBtn = new JButton("View Customers");
        JButton deleteBtn = new JButton("Delete Customer");
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
            JTextField roomField = new JTextField();
            JTextField serviceField = new JTextField();

            Object[] fields = {
                "Customer ID:", idField,
                "Name:", nameField,
                "Email:", emailField,
                "Phone:", phoneField,
                "Room Number (101 or 102):", roomField,
                "Service Name:", serviceField
            };

            int result = JOptionPane.showConfirmDialog(this, fields, "Add Customer", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    String name = nameField.getText();
                    String email = emailField.getText();
                    String phone = phoneField.getText();
                    String roomNumber = roomField.getText();
                    String serviceName = serviceField.getText();

                    if (!Validator.isValidEmail(email)) {
                        JOptionPane.showMessageDialog(this, "Invalid email format!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (!Validator.isValidPhone(phone)) {
                        JOptionPane.showMessageDialog(this, "Invalid phone format (10-15 digits)!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Room room = roomNumber.equals("101") ? room1 : roomNumber.equals("102") ? room2 : null;
                    if (room == null) {
                        JOptionPane.showMessageDialog(this, "Invalid room number!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Service service = new Service(1, serviceName, "Basic Service", 30.0);
                    customerManager.addCustomer(id, name, email, phone, room, service);
                    JOptionPane.showMessageDialog(this, "Customer added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid ID format!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        viewBtn.addActionListener(e -> customerManager.listCustomers());

        deleteBtn.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog(this, "Enter Customer ID to delete:");
            try {
                int id = Integer.parseInt(idStr);
                customerManager.deleteCustomer(id);
                JOptionPane.showMessageDialog(this, "Customer deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backBtn.addActionListener(e -> dispose());

        setVisible(true);
    }
}