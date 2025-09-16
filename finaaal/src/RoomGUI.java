import javax.swing.*;
import java.awt.*;
import manager.RoomManager;

public class RoomGUI extends JFrame {
    private RoomManager roomManager;

    public RoomGUI(JFrame parent) {
        this.roomManager = new RoomManager();
        setTitle("Room Management");
        setSize(400, 300);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        JButton addBtn = new JButton("Add Room");
        JButton viewBtn = new JButton("View Rooms");
        JButton assignBtn = new JButton("Assign Room to Guest");
        JButton backBtn = new JButton("Back");

        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        panel.add(addBtn);
        panel.add(viewBtn);
        panel.add(assignBtn);
        panel.add(backBtn);

        add(panel);

        addBtn.addActionListener(e -> {
            JTextField numberField = new JTextField();
            JTextField typeField = new JTextField();
            JTextField priceField = new JTextField();
            JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Available", "Booked", "Under Maintenance"});

            Object[] fields = {
                "Room Number:", numberField,
                "Room Type:", typeField,
                "Price:", priceField,
                "Status:", statusCombo
            };

            int result = JOptionPane.showConfirmDialog(this, fields, "Add Room", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    int roomNumber = Integer.parseInt(numberField.getText());
                    String roomType = typeField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    String status = (String) statusCombo.getSelectedItem();
                    boolean isAvailable = status.equals("Available");

                    roomManager.addRoom(roomNumber, roomType, isAvailable, price);
                    JOptionPane.showMessageDialog(this, "Room added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid number format!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        viewBtn.addActionListener(e -> roomManager.listRooms());

        assignBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Assign room functionality will be implemented in CheckInGUI."));

        backBtn.addActionListener(e -> dispose());

        setVisible(true);
    }
}