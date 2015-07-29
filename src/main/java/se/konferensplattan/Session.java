package se.konferensplattan;

public class Session {

    private final String name;
    private final String sessionId;

    public Session(String name, String sessionId) {
        this.name = name;
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getName() {
        return name;
    }

}
