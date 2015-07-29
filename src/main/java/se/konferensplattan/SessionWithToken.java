package se.konferensplattan;

public class SessionWithToken extends Session {

    private String token;

    public SessionWithToken(Session session, String token) {
        super(session.getName(), session.getSessionId());
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
