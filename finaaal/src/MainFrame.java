import javax.swing.*;
import java.awt.*;
import manager.CustomerManager;
import manager.EmployeeManager;
//apply inheretance
public class MainFrame extends JFrame {
//    encapsulation
    private CustomerManager customerManager = new CustomerManager();
    private EmployeeManager employeeManager = new EmployeeManager();
//    main button
    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(15, 25, 15, 25)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
//constructor
    public MainFrame() {
        setTitle("Hotel Management System");
        setSize(1000, 800);
//        el program done lma close main panel
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(216, 160, 166));
        
        
        JPanel panel = new JPanel(new GridBagLayout());
//        3shan myb2ash white aw shafaf
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        GridBagConstraints gbc = new GridBagConstraints();
//         kol dorar ya5od el panal kolaha
        gbc.gridwidth = GridBagConstraints.REMAINDER;
//        nafs el kalam bs horizontal
        gbc.fill = GridBagConstraints.HORIZONTAL;
//        dif ben kol zorar
        gbc.insets = new Insets(15, 0, 15, 0);
        gbc.ipadx = 50;
        gbc.ipady = 20;

        JLabel titleLabel = new JLabel("Hotel Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(50, 50, 80));
        panel.add(titleLabel, gbc);
//        add buttons
        JButton employeeBtn = new JButton("Manage Employees");
        JButton customerBtn = new JButton("Manage Customers");
        JButton roomBtn = new JButton("Manage Rooms");
        JButton checkInBtn = new JButton("Guest Check-In");
        JButton invoiceBtn = new JButton("Generate Invoice");
//        color button
        styleButton(employeeBtn, new Color(52, 152, 219));
        styleButton(customerBtn, new Color(155, 89, 182));
        styleButton(roomBtn, new Color(26, 188, 156));
        styleButton(checkInBtn, new Color(241, 196, 15));
        styleButton(invoiceBtn, new Color(46, 204, 113));

        panel.add(employeeBtn, gbc);
        panel.add(customerBtn, gbc);
        panel.add(roomBtn, gbc);
        panel.add(checkInBtn, gbc);
        panel.add(invoiceBtn, gbc);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        
        employeeBtn.addActionListener(e -> new EmployeeGUI(this, employeeManager));
        customerBtn.addActionListener(e -> new CustomerGUI(this, customerManager));
        roomBtn.addActionListener(e -> new RoomGUI(this));
        checkInBtn.addActionListener(e -> new CheckInGUI(this, customerManager));
        invoiceBtn.addActionListener(e -> new InvoiceGUI(this, customerManager));

        setVisible(true);
    }
}
