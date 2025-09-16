import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import manager.CustomerManager;
import model.Customer;

public class InvoiceGUI extends JFrame {
    private CustomerManager customerManager;
    
    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setFocusPainted(false);
 
       ;
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
//constructor
    public InvoiceGUI(JFrame parent, CustomerManager customerManager) {
        this.customerManager = customerManager;
        setTitle("Generate Invoice");
        setSize(500, 400);
//      by5aly el page tfta7 fe nos el screen
        setLocationRelativeTo(parent); 
        setLayout(new BorderLayout());
//                                                    col,row ,dif
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel customerIdLabel = new JLabel("Customer ID:");
        JTextField customerIdField = new JTextField();
        
        JLabel daysLabel = new JLabel("Number of Days:");
        JTextField daysField = new JTextField();

        inputPanel.add(customerIdLabel);
        inputPanel.add(customerIdField);
        inputPanel.add(daysLabel);
        inputPanel.add(daysField);
        
        
        
        
        

        JTextArea invoiceArea = new JTextArea();
        invoiceArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(invoiceArea);

        JButton generateBtn = new JButton("Generate Invoice");
        JButton backBtn = new JButton("Back");

        generateBtn.addActionListener(e -> {
            try {
                int customerId = Integer.parseInt(customerIdField.getText());
                int days = Integer.parseInt(daysField.getText());
                
                Customer customer = customerManager.getCustomerById(customerId);
                if (customer != null) {
                    String invoice = generateInvoice(customer, days);
                    invoiceArea.setText(invoice);
                } else {
                    JOptionPane.showMessageDialog(this, "Customer not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backBtn.addActionListener(e -> dispose());
        
        JButton saveBtn = new JButton("Save Invoice");
        saveBtn.addActionListener(e -> {
        String invoiceText = invoiceArea.getText();
        if (!invoiceText.isEmpty()) {
        try (PrintWriter writer = new PrintWriter("invoice_" + customerIdField.getText() + ".txt")) {
            writer.println(invoiceText);
            JOptionPane.showMessageDialog(this, "Invoice saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving invoice!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
});

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(generateBtn);
        buttonPanel.add(backBtn);
        buttonPanel.add(saveBtn);
//      byzbt amaken el 7aga
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        
        styleButton(generateBtn, new Color(52, 152, 219));
        styleButton(backBtn, new Color(52, 152, 219));
        styleButton(saveBtn, new Color(52, 152, 219));
        
        
//      present page to user
        setVisible(true);
    }

    private String generateInvoice(Customer customer, int days) {
        double roomCost = customer.getRoom().getPrice() * days;
        double serviceCost = customer.getService().getCost() * days;
        double subtotal = roomCost + serviceCost;
        double tax = subtotal * 0.15;
        double total = subtotal + tax;
        
        return String.format(
            "======== Invoice ========\n" +
            "Customer: %s (ID: %d)\n" +
            "Room: %d (%s) - EGP%.2f/night\n" +
            "Service: %s - EGP%.2f/day\n" +
            "Days: %d\n" +
            "------------------------\n" +
            "Subtotal: EGP%.2f\n" +
            "Tax (15%%): EGP%.2f\n" +
            "Total: EGP%.2f\n" +
            "========================",
            customer.getName(), customer.getCustomerId(),
            customer.getRoom().getRoomNumber(), customer.getRoom().getRoomType(), customer.getRoom().getPrice(),
            customer.getService().getServiceName(), customer.getService().getCost(),
            days, subtotal, tax, total
        );
    }
    
}