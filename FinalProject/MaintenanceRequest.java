package inncontrol;

public class Message {
    private int id;
    private String sender;
    private String receiver;
    private String message;
    private String date;

    public Message(int id, String sender, String receiver, String message, String date) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.date = date;
    }

    public int getId() { return id; }
    public String getSender() { return sender; }
    public String getReceiver() { return receiver; }
    public String getMessage() { return message; }
    public String getDate() { return date; }
}