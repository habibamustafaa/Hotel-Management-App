package model;

public class Service {
    private int id;
    private String name;
    private String description;
    private double price;

    public Service(int id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getId() { return id; }
    public String getServiceName() { return name; }
    public String getDescription() { return description; }
    public double getCost() { return price; }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(double price) { this.price = price; }

    public String toFileString() {
        return id + "," + name + "," + description + "," + price;
    }

    public static Service fromFileString(String line) {
        String[] parts = line.split(",", -1);
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        String desc = parts[2];
        double price = Double.parseDouble(parts[3]);
        return new Service(id, name, desc, price);
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Description: " + description + ", Price: " + price;
    }
}