package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import daos.EventDao;
import daos.UserDao;
import models.*;
import play.db.jpa.Transactional;
import play.libs.F;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yellowstar on 11/16/15.
 */
public class MainController extends Controller {
    private final String CLIENT_ID = "538923068949-7omlgmdftfnht7l4s2s2oog77kngecr5.apps.googleusercontent.com";
    private final String END_POINT = "https://www.googleapis.com/oauth2/v3/tokeninfo";

    @Inject
    private UserDao userDao;
    @Inject
    private EventDao eventDao;

    @Transactional
    public Result getUserProfile(int uid) {
        User user = userDao.findById(uid);
        return ok(Json.toJson(user));
    }

    @Transactional
    public Result addRecord(int uid) {
        JsonNode jsonNode = request().body().asJson();
        Record record = Json.fromJson(jsonNode, Record.class);
        User user = userDao.findById(uid);
        record.setOwner(user);
        user.getRecords().add(record);
        return ok();
    }

    @Transactional
    public Result invite(int eid, int uid) {
        User user = userDao.findById(uid);
        Event event = eventDao.findById(eid);
        Attend attend = new Attend();
        attend.setEvent(event);
        attend.setUser(user);
        attend.setStatus(0); // 0 invited, 1 accepted, 2 rejected, 3 passed
        user.getAttends().add(attend);
        event.getAttends().add(attend);
        return ok();
    }

    @Transactional
    public Result getEvent(int eid) {
        Event event = eventDao.findById(eid);
        return ok(Json.toJson(event));
    }

    @Transactional
    public Result createEvent() {
        JsonNode json = request().body().asJson();
        Event event = Json.fromJson(json, Event.class);
        event = eventDao.create(event);
        return ok(Json.toJson(event));
    }

    @Transactional
    public Result getUserEvents(int uid) {
        User user = userDao.findById(uid);
        List<UserEventWithStatus> events = user.getUserEvent();
        return ok(Json.toJson(events));
    }

    @Transactional
    public Result authenticate() {
        JsonNode json = request().body().asJson();
        Identity identity = Json.fromJson(json, Identity.class);

        WSClient client = WS.client();
        WSRequest request = client.url(END_POINT);
        request = request.setQueryParameter("id_token", identity.getIdToken());
        F.Promise<JsonNode> promise = request.get().map(response -> {
            return response.asJson();
        });
        JsonNode res = promise.get(2000);
        if (!verfify(res)) return notFound();
        String sub = res.findPath("sub").asText();
        User user = userDao.findBySub(sub);
        if (user == null) {
            user = new User();
            // get and set profile
            userDao.create(user);
        }
        /*GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Arrays.asList(CLIENT_ID))
                .setIssuer("https://accounts.google.com")
                .build();
        try {
            GoogleIdToken idToken = verifier.verify(identity.getIdToken());
            if (idToken != null) {
                Payload payload = idToken.getPayload();
                payload.getSubject();

                System.out.println("User ID: " + payload.getSubject());
            } else {
                System.out.println("Invalid ID token.");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }*/
        return ok(Json.toJson(user));
    }

    private boolean verfify(JsonNode res) {
        if (!res.findPath("aud").asText().equals(CLIENT_ID)) return false;
        return true;
    }
}
