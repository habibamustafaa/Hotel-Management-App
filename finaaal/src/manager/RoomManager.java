package manager;

import model.Room;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RoomManager {
    private final ArrayList<Room> rooms = new ArrayList<>();
    private final String FILE_NAME = "rooms.txt";

    public RoomManager() {
        loadFromFile();
    }

    public void addRoom(int roomNumber, String roomType, boolean isAvailable, double price) {
        rooms.add(new Room(roomNumber, roomType, isAvailable, price));
        saveToFile();
        System.out.println("Room added.");
    }

    public void updateRoom(int roomNumber, String roomType, boolean isAvailable, double price) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                room.setRoomType(roomType);
                room.setAvailable(isAvailable);
                room.setPrice(price);
                saveToFile();
                System.out.println("Room updated.");
                return;
            }
        }
        System.out.println("Room not found.");
    }

    public void updateRoomStatus(int roomNumber, boolean isAvailable) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                room.setAvailable(isAvailable);
                saveToFile();
                System.out.println("Room status updated.");
                return;
            }
        }
        System.out.println("Room not found.");
    }

    public void deleteRoom(int roomNumber) {
        rooms.removeIf(room -> room.getRoomNumber() == roomNumber);
        saveToFile();
        System.out.println("Room deleted.");
    }

    public void listRooms() {
        if (rooms.isEmpty()) {
            System.out.println("No rooms available.");
            return;
        }

        StringBuilder roomList = new StringBuilder();
        for (Room room : rooms) {
            roomList.append("Room: ").append(room.getRoomNumber())
                    .append(" | Type: ").append(room.getRoomType())
                    .append(" | Available: ").append(room.isAvailable())
                    .append(" | Price: $").append(room.getPrice())
                    .append("\n");
        }

        JOptionPane.showMessageDialog(null, roomList.toString(), "Room List", JOptionPane.INFORMATION_MESSAGE);
    }

    public Room getAvailableRoomByType(String roomType) {
        for (Room room : rooms) {
            if (room.getRoomType().equalsIgnoreCase(roomType) && room.isAvailable()) {
                return room;
            }
        }
        return null;
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Room room : rooms) {
                writer.println(room.getRoomNumber() + "," + room.getRoomType() + "," + room.isAvailable() + "," + room.getPrice());
            }
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    private void loadFromFile() {
        rooms.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length == 4) {
                    int roomNumber = Integer.parseInt(parts[0]);
                    String roomType = parts[1];
                    boolean isAvailable = Boolean.parseBoolean(parts[2]);
                    double price = Double.parseDouble(parts[3]);
                    rooms.add(new Room(roomNumber, roomType, isAvailable, price));
                }
            }
        } catch (IOException e) {
            System.out.println("File not found, starting fresh.");
        }
    }
}