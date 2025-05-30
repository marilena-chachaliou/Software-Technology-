package inncontrol;

public class Booking {
    private String guestName;
    private String roomNumber;
    private String checkIn;
    private String checkOut;

    public Booking(String guestName, String roomNumber, String checkIn, String checkOut) {
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public String getGuestName() { return guestName; }
    public String getRoomNumber() { return roomNumber; }
    public String getCheckIn() { return checkIn; }
    public String getCheckOut() { return checkOut; }
}
