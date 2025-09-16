package manager;

import model.Customer;
import model.Room;
import model.Service;
import utils.Validator;
import java.io.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.ServiceManager;

public class CustomerManager {
    private final ArrayList<Customer> customers = new ArrayList<>();
    private final String FILE_NAME = "customers.txt";

    public CustomerManager() {
        loadFromFile();
    }

    public void addCustomer(int customerId, String name, String email, String phone, Room room, Service service) {
        if (!Validator.isValidEmail(email)) {
            JOptionPane.showMessageDialog(null, "Invalid email.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!Validator.isValidPhone(phone)) {
            JOptionPane.showMessageDialog(null, "Invalid phone.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        customers.add(new Customer(customerId, name, email, phone, room, service));
        saveToFile();
        JOptionPane.showMessageDialog(null, "Customer added.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void updateCustomer(int customerId, String name, String email, String phone, Room room, Service service) {
        for (Customer customer : customers) {
            if (customer.getCustomerId() == customerId) {
                customer.setName(name);
                customer.setEmail(email);
                customer.setPhone(phone);
                customer.setRoom(room);
                customer.setService(service);
                saveToFile();
                JOptionPane.showMessageDialog(null, "Customer updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Customer not found.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void deleteCustomer(int customerId) {
        customers.removeIf(customer -> customer.getCustomerId() == customerId);
        saveToFile();
        JOptionPane.showMessageDialog(null, "Customer deleted.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void listCustomers() {
        if (customers.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No customers available.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder customerList = new StringBuilder();
        for (Customer customer : customers) {
            customerList.append(customer.toString()).append("\n");
        }

        JOptionPane.showMessageDialog(null, customerList.toString(), "Customer List", JOptionPane.INFORMATION_MESSAGE);
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Customer customer : customers) {
                writer.println(customer.getCustomerId() + "," +
                        customer.getName() + "," +
                        customer.getEmail() + "," +
                        customer.getPhone() + "," +
                        customer.getRoom().getRoomNumber() + "," +
                        customer.getService().getServiceName());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing to file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadFromFile() {
        customers.clear();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            ServiceManager serviceManager = new ServiceManager();

            String line;
            while ((line = reader.readLine()) != null) {
//               نفصل السطر لعناصر وتتحطفي ليست
                String[] parts = line.split(",", -1);
                if (parts.length == 6) {
                    int customerId = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String email = parts[2];
                    String phone = parts[3];
                    int roomNumber = Integer.parseInt(parts[4]);
                    String serviceName = parts[5];

                    Room room = new Room(roomNumber, "Standard", true, 100);
                    Service service = serviceManager.getServiceByName(serviceName);
                    if (service == null) {
                        service = new Service(1, serviceName, "Basic Service", 30.0);
                    }

                    customers.add(new Customer(customerId, name, email, phone, room, service));
                }
            }

            reader.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File not found, starting fresh.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public Customer getCustomerById(int id) {
        for (Customer customer : customers) {
            if (customer.getCustomerId() == id) {
                return customer;
            }
        }
        return null;
    }
}