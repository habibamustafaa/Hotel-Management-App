package model;

public class Room {
    private int roomNumber;
    private String roomType;
    private boolean isAvailable;
    private double price;

    public Room(int roomNumber, String roomType, boolean isAvailable, double price) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.isAvailable = isAvailable;
        this.price = price;
    }

    public int getRoomNumber() { return roomNumber; }
    public String getRoomType() { return roomType; }
    public boolean isAvailable() { return isAvailable; }
    public double getPrice() { return price; }

    public void setRoomNumber(int roomNumber) { this.roomNumber = roomNumber; }
    public void setRoomType(String roomType) { this.roomType = roomType; }
    public void setAvailable(boolean available) { isAvailable = available; }
    public void setPrice(double price) { this.price = price; }
}