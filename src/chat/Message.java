// Message.java
package chat;

public class Message {
    private String nickname;
    private String text;

    public Message(String nickname, String text) {
        this.nickname = nickname;
        this.text = text;
    }

    public String getNickname() { return nickname; }
    public String getText() { return text; }

    // Serialize as "nickname:text"
    public String serialize() {
        return nickname + ":" + text;
    }

    public static Message parse(String line) {
        int idx = line.indexOf(':');
        if (idx <= 0) return new Message("unknown", line);
        String nick = line.substring(0, idx);
        String txt = line.substring(idx + 1);
        return new Message(nick, txt);
    }
}
