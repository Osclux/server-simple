package se.konferensplattan;

import com.opentok.OpenTok;
import com.opentok.exception.OpenTokException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * TODO: Describe purpose
 */
public class SessionService {
    private static String apiSecret;
    private static String apiKey;
    private static List<Session> activeSessionList = new ArrayList<>();

    public static List<SessionWithToken> allActiveSessions() {
        return activeSessionList.stream().map(
                activeSession -> new SessionWithToken(activeSession, createTokenForSession(activeSession.getSessionId()))
        ).collect(Collectors.toList());
    }

    private static String createTokenForSession(String sessionId) {
        OpenTok opentok = new OpenTok(Integer.parseInt(apiKey), apiSecret);
        try {
            return opentok.generateToken(sessionId);
        } catch (OpenTokException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void createNewSession(String name) throws OpenTokException {
        OpenTok opentok = new OpenTok(Integer.parseInt(apiKey), apiSecret);
        String sessionId = opentok.createSession().getSessionId();
        activeSessionList.add(new Session(name, sessionId));
    }

    public static void setApiSecret(String apiSecret) {
        SessionService.apiSecret = apiSecret;
    }

    public static String getApiSecret() {
        return apiSecret;
    }

    public static void setApiKey(String apiKey) {
        SessionService.apiKey = apiKey;
    }

    public static String getApiKey() {
        return apiKey;
    }


    public static Optional<SessionWithToken> getSessionWithToken(String name) {
        Optional<Session> sessionOptional = activeSessionList.stream().filter(session -> session.getName().equals(name)).findFirst();
        if (sessionOptional.isPresent()) {
            Session session = sessionOptional.get();
            SessionWithToken sessionWithToken = new SessionWithToken(session, createTokenForSession(session.getSessionId()));
            return Optional.of(sessionWithToken);
        }
        return Optional.empty();
    }

    public static void createDefaultSessions() throws OpenTokException {
        for (DefaultSessionName name : DefaultSessionName.values()) {
            createNewSession(name.name());
        }
    }
}
