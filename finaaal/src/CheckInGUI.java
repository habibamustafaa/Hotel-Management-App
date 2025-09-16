import javax.swing.*;
import java.awt.*;
import manager.CustomerManager;
import manager.RoomManager;
import model.Room;
import model.Service;
import utils.Validator;

public class CheckInGUI extends JFrame {
    private CustomerManager customerManager;
    private RoomManager roomManager;

    public CheckInGUI(JFrame parent, CustomerManager customerManager) {
        this.customerManager = customerManager;
        this.roomManager = new RoomManager();
        setTitle("Guest Check-In");
        setSize(500, 400);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel idLabel = new JLabel("Customer ID:");
        JTextField idField = new JTextField();
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JLabel phoneLabel = new JLabel("Phone:");
        JTextField phoneField = new JTextField();
        JLabel roomTypeLabel = new JLabel("Room Type:");
        JTextField roomTypeField = new JTextField();
        JLabel serviceLabel = new JLabel("Service Name:");
        JTextField serviceField = new JTextField();

        JButton checkInBtn = new JButton("Check In");
        JButton backBtn = new JButton("Back");

        panel.add(idLabel);
        panel.add(idField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(phoneLabel);
        panel.add(phoneField);
        panel.add(roomTypeLabel);
        panel.add(roomTypeField);
        panel.add(serviceLabel);
        panel.add(serviceField);
        panel.add(checkInBtn);
        panel.add(backBtn);

        add(panel);

        checkInBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String email = emailField.getText();
                String phone = phoneField.getText();
                String roomType = roomTypeField.getText();
                String serviceName = serviceField.getText();

                if (!Validator.isValidEmail(email)) {
                    JOptionPane.showMessageDialog(this, "Invalid email format!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!Validator.isValidPhone(phone)) {
                    JOptionPane.showMessageDialog(this, "Invalid phone format (10-15 digits)!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Room room = roomManager.getAvailableRoomByType(roomType);
                if (room == null) {
                    JOptionPane.showMessageDialog(this, "No available room of type " + roomType, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Service service = new Service(1, serviceName, "Basic Service", 30.0);
                customerManager.addCustomer(id, name, email, phone, room, service);
                roomManager.updateRoomStatus(room.getRoomNumber(), false); // Mark room as booked
                JOptionPane.showMessageDialog(this, "Check-in successful! Room " + room.getRoomNumber() + " assigned.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backBtn.addActionListener(e -> dispose());

        setVisible(true);
    }
}