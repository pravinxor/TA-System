package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.IOException;


/**
 * @description: user reg/login
 * @author: Swati Bhat
 * @create: 2019-11-16 15:15
 */

public class UserController extends Controller {
    public Result getUser() {
        JsonNode req = request().body().asJson();
        String username = req.get("username").asText();
        String password = req.get("password").asText();
        try {
            User user = User.findByName(username); // ( match where username and password both match )
            if (user != null && username.equals(user.username) && password.equals(user.password)) {
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode node = mapper.valueToTree(user);
                node.remove("id");
                return ok(node.toString());
            } else {
                System.out.println("Bad login");
                return ok("false");
            }
        } catch (Exception e) {
            System.out.println(e);
            return internalServerError();
        }
    }


    /**
     * When a user register, check if the username is taken
     * save to database if valid
     * POST
     *
     * @return success if valid, fail if already taken
     */
    public Result registerNew() throws IOException {
        System.out.println("In register");
        JsonNode req = request().body().asJson();
        String username = req.get("username").asText();

        User u = User.findByName(username);
        ObjectNode result;
        result = Json.newObject();
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(req.toString(), User.class);

        if (u == null) {
            System.out.println("new user");
            user.save();
        } else {
            User udb = User.findByName(username);
            user.id = udb.id;
            user.update();
        }

        // Make another function in the backend for editing users


        assert result != null;
        return ok(result);
    }
}
