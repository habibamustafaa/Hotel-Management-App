package manager;

import model.Employee;
import utils.Validator;
import java.io.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class EmployeeManager {
    private final ArrayList<Employee> employees = new ArrayList<>();
    private final String FILE_NAME = "employees.txt";

    public EmployeeManager() {
        loadFromFile();
    }

    public void addEmployee(int id, String name, String email, String phone) {
        if (!Validator.isValidEmail(email)) {
            JOptionPane.showMessageDialog(null, "Invalid email.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!Validator.isValidPhone(phone)) {
            JOptionPane.showMessageDialog(null, "Invalid phone.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        employees.add(new Employee(id, name, email, phone));
        saveToFile();
        JOptionPane.showMessageDialog(null, "Employee added.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void updateEmployee(int id, String name, String email, String phone) {
        for (Employee emp : employees) {
            if (emp.getId() == id) {
                emp.setName(name);
                emp.setEmail(email);
                emp.setPhone(phone);
                saveToFile();
                JOptionPane.showMessageDialog(null, "Employee updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Employee not found.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void deleteEmployeeById(int id) {
        employees.removeIf(emp -> emp.getId() == id);
        saveToFile();
        JOptionPane.showMessageDialog(null, "Employee deleted.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void listEmployees() {
        if (employees.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No employees available.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder employeeList = new StringBuilder();
        for (Employee employee : employees) {
            employeeList.append(employee.toString()).append("\n");
        }

        JOptionPane.showMessageDialog(null, employeeList.toString(), "Employee List", JOptionPane.INFORMATION_MESSAGE);
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Employee emp : employees) {
                writer.println(emp.getId() + "," + emp.getName() + "," + emp.getEmail() + "," + emp.getPhone());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing to file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadFromFile() {
        employees.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String email = parts[2];
                    String phone = parts[3];
                    employees.add(new Employee(id, name, email, phone));
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File not found, starting fresh.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}