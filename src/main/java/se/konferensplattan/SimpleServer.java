package se.konferensplattan;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opentok.exception.OpenTokException;
import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static spark.Spark.*;

public class SimpleServer {

    private static final String apiKey = System.getProperty("API_KEY");
    private static final String apiSecret = System.getProperty("API_SECRET");

    public static void main(String[] args) throws OpenTokException, IOException {

        if (apiKey == null || apiKey.isEmpty() || apiSecret == null || apiSecret.isEmpty()) {
            System.out.println("You must define API_KEY and API_SECRET system properties in the build.gradle file.");
            System.exit(-1);
        }

        SessionService.setApiKey(apiKey);
        SessionService.setApiSecret(apiSecret);

        SessionService.createDefaultSessions();


        externalStaticFileLocation("./public");
        Configuration freemarkerConfig = new Configuration();
        freemarkerConfig.setClassForTemplateLoading(SimpleServer.class, "freemarker");
        get("/", (request, response) -> {

            // Use the test session for the web-client
            SessionWithToken testSession = SessionService.getSessionWithToken(DefaultSessionName.test.name()).get();
            String sessionId = testSession.getSessionId();
            String token = testSession.getToken();

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("apiKey", apiKey);
            attributes.put("sessionId", sessionId);
            attributes.put("token", token);

            return new ModelAndView(attributes, "index.ftl");

        }, new FreeMarkerEngine(freemarkerConfig));

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Get all sessions
        get("/activesessions", (request, response) -> {

            return SessionService.allActiveSessions();
        }, gson::toJson);

        // Get a specific named session
        get("/session/:name", (request, response) -> {

            String sessionName = request.params(":name");
            Optional<SessionWithToken> sessionOptional = SessionService.getSessionWithToken(sessionName);
            if (sessionOptional.isPresent()) {
                return sessionOptional.get();
            } else {
                throw new NotFoundException();
            }

        }, gson::toJson);

        exception(NotFoundException.class, (e, request, response) -> {
            response.status(404);
            response.body("Resource not found");
        });


    }
}
