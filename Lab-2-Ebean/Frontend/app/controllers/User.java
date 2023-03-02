package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;

import java.util.concurrent.CompletionStage;


public class User {
    public String username, password, firstname, lastname, position, affiliation, email, phone, fax, address, city, country, zipcode, comments, status, degreeplan, semesterstart, yearstart, semesterend, yearend;

    public String[] prevcourses;
    public CompletionStage<WSResponse> checkAuthorized() {

        WSClient ws = play.test.WSTestClient.newClient(9005);
        WSRequest request = ws.url("http://localhost:9005/login");
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode user = mapper.valueToTree(this);
        return request.addHeader("Content-Type", "application/json")
                .post(user)
                .thenApply((WSResponse r) -> r);
    }

    public CompletionStage<WSResponse> user() {
        WSClient ws = play.test.WSTestClient.newClient(9005);
        WSRequest request = ws.url("http://localhost:9005/user");
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode user = mapper.valueToTree(this);
        return request.addHeader("Content-Type", "application/json")
                .post(user)
                .thenApply((WSResponse r) -> r);
    }


    public CompletionStage<WSResponse> registerUser() {
        ObjectMapper mapper = new ObjectMapper();
        //ArrayNode array = mapper.valueToTree(this.prevcourses);
        WSClient ws = play.test.WSTestClient.newClient(9005);
        // send this. user
        ObjectNode user = mapper.valueToTree(this);
        //user.putArray("prevcourses").addAll(array);

        System.out.println(user);

        WSRequest request = ws.url("http://localhost:9005/signup");
        return request.addHeader("Content-Type", "application/json")
                .post(user)
                .thenApply((WSResponse r) -> r);
    }

}
