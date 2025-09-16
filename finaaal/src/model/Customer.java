package model;

public final class Customer {
    private final int customerId;
    private String name;
    private String email;
    private String phone;
    private Room room;
    private Service service;

    public Customer(int customerId, String name, String email, String phone, Room room, Service service) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.room = room;
        this.service = service;
    }

    public int getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public Room getRoom() { return room; }
    public Service getService() { return service; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setRoom(Room room) { this.room = room; }
    public void setService(Service service) { this.service = service; }

    public double calculateBill() {
        double roomCost = room.getPrice();
        double serviceCost = service.getCost();
        double tax = (roomCost + serviceCost) * 0.15;
        return roomCost + serviceCost + tax;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Email: %s | Phone: %s | Room: %d | Service: %s | Bill: $%.2f",
                customerId, name, email, phone, room.getRoomNumber(),
                service.getServiceName(), calculateBill());
    }
}