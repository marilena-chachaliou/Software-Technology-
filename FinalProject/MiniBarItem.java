package inncontrol;

public class MiniBarItem {
    private int bookingId;
    private String item;
    private int quantity;
    private boolean charged;

    public MiniBarItem(int bookingId, String item, int quantity, boolean charged) {
        this.bookingId = bookingId;
        this.item = item;
        this.quantity = quantity;
        this.charged = charged;
    }

    public int getBookingId() { return bookingId; }
    public String getItem() { return item; }
    public int getQuantity() { return quantity; }
    public boolean getCharged() { return charged; }
}