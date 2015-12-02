package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import daos.AttendDao;
import daos.EventDao;
import daos.RecordDao;
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
import java.util.Arrays;
import java.util.Date;
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
    @Inject
    private RecordDao recordDao;
    @Inject
    private AttendDao attendDao;

    @Transactional
    public Result getUser(int uid) {
        User user = userDao.findById(uid);
        return ok(Json.toJson(user));
    }

    @Transactional
    public Result addRecord(int uid) {
        JsonNode jsonNode = request().body().asJson();
        RecordDTO recordDTO = Json.fromJson(jsonNode, RecordDTO.class);
        Record record = new Record();
        record.setStartTime(recordDTO.getStartTime());
        record.setEndTime(recordDTO.getEndTime());
        record.setDistance(recordDTO.getDistance());
        record.setPath(recordDTO.getPath().getValue());
        User user = userDao.findById(uid);
        record.setOwner(user);
        recordDao.create(record);
        return ok(Json.toJson(record));
    }

    @Transactional
    public Result getRecords(int uid) {
        User user = userDao.findById(uid);
        return ok(Json.toJson(user.getRecords()));
    }

    @Transactional
    public Result invite(int eid, String email) {
        User user = userDao.findByEmail(email);
        Event event = eventDao.findById(eid);
        Attend attend = new Attend();
        attend.setEvent(event);
        attend.setUser(user);
        attend.setStatus(1); // -1 all, 0 owned, 1 invited, 2 accepted, 3 rejected, 4 passed
        attendDao.create(attend);
        return ok(Json.toJson(attend));
    }

    @Transactional
    public Result getEvent(int eid) {
        Event event = eventDao.findById(eid);
        return ok(Json.toJson(event));
    }

    @Transactional
    public Result createEvent(int uid) {
        JsonNode json = request().body().asJson();
        Event event = Json.fromJson(json, Event.class);
        User user = userDao.findById(uid);
        event.setOwner(user);
        event = eventDao.create(event);
        return ok(Json.toJson(event));
    }

    @Transactional
    public Result getUserEvents(int uid, int status) {
        User user = userDao.findById(uid);
        if (status == 0) {
            List<Event> events = user.getEvents();
            return ok(Json.toJson(events));
        } else {
            List<UserEventWithStatus> events = user.getUserEvent();
            return ok(Json.toJson(events));
        }
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
            user.setEmail(res.findPath("email").asText());
            user.setAvatar(res.findPath("picture").asText());
            user.setGoogleId(sub);
            user.setName(res.findPath("name").asText());
            user.setExpiration(new Date(res.findPath("exp").asLong()));
            userDao.create(user);
        }
        /*GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Arrays.asList(CLIENT_ID))
                .setIssuer("https://accounts.google.com")
                .build();
        try {
            GoogleIdToken idToken = verifier.verify(identity.getIdToken());
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();
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
