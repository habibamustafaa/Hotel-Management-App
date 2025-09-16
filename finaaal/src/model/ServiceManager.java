package model;

import java.io.*;
import java.util.*;

public class ServiceManager {
    private final String FILE_PATH = "data/services.txt";

    public ServiceManager() {
        initializeFile();
    }

    private void initializeFile() {
        File file = new File(FILE_PATH);
        try {
            file.getParentFile().mkdirs();
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error initializing services file: " + e.getMessage());
        }
    }

    public void addService(int id, String name, String desc, double price) throws IOException {
        List<Service> services = getAllServices();
        services.add(new Service(id, name, desc, price));
        saveAll(services);
    }

    public void updateService(int id, String name, String desc, double price) throws IOException {
        List<Service> services = getAllServices();
        boolean found = false;

        for (Service s : services) {
            if (s.getId() == id) {
                s.setName(name);
                s.setDescription(desc);
                s.setPrice(price);
                found = true;
                break;
            }
        }

        if (!found) {
            throw new NoSuchElementException("Service with ID " + id + " not found.");
        }

        saveAll(services);
    }

    public void deleteService(int id) throws IOException {
        List<Service> services = getAllServices();
        boolean removed = services.removeIf(s -> s.getId() == id);

        if (!removed) {
            throw new NoSuchElementException("Service with ID " + id + " not found.");
        }

        saveAll(services);
    }

    private void saveAll(List<Service> services) throws IOException {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Service service : services) {
                writer.write(service.toFileString());
                writer.newLine();
            }
        }
    }

    public List<Service> getAllServices() throws IOException {
        List<Service> services = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return services;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                services.add(Service.fromFileString(line));
            }
        }

        return services;
    }

    public Service getServiceById(int id) throws IOException {
        for (Service s : getAllServices()) {
            if (s.getId() == id) return s;
        }
        return null;
    }

    public Service getServiceByName(String name) throws IOException {
        if (name == null) return null;

        for (Service s : getAllServices()) {
            if (s != null && s.getServiceName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        return null;
    }
}