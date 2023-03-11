package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.IOException;

public class FormController extends Controller {
    public Result getForm() {
        JsonNode req = request().body().asJson();
        String title = req.get("title").asText();
        try {
            Form form = Form.findByTitle(title); // ( match where username and password both match )
            if (form != null && title.equals(form.title)) {
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode node = mapper.valueToTree(form);
                node.remove("id");
                return ok(node.toString());
            } else {
                System.out.println("Form with title: " + title + " not found");
                return ok("false");
            }
        } catch (Exception e) {
            System.out.println(e);
            return internalServerError();
        }
    }

    public Result registerNew() throws IOException {
        System.out.println("Adding new form");
        JsonNode req = request().body().asJson();
        String title = req.get("title").asText();

        Form u = Form.findByTitle(title);
        ObjectNode result = null;
        if (u == null) {
            System.out.println("new form");
        }

        // Make another function in the backend for editing form
        result = Json.newObject();
        ObjectMapper mapper = new ObjectMapper();
        Form form = mapper.readValue(req.toString(), Form.class);
        form.save();

        assert result != null;
        return ok(result);
    }
}
