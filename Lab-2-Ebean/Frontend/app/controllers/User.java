package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;

import java.util.concurrent.CompletionStage;


public class User {

    private String username, password, firstname, lastname, position, affiliation, email, phone, fax, address, city, country, zipcode, comments, status, degreeplan, semesterstart, yearstart, semesterend, yearend;

    private String[] prevcourses;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public CompletionStage<WSResponse> checkAuthorized() {

        WSClient ws = play.test.WSTestClient.newClient(9005);
        //add username password
        WSRequest request = ws.url("http://localhost:9005/login");
        ObjectNode res = Json.newObject();
        res.put("username", this.username);
        res.put("password", this.password);
        return request.addHeader("Content-Type", "application/json")
                .post(res)
                .thenApply((WSResponse r) -> {
                    return r;
                });
    }


    public CompletionStage<WSResponse> registerUser() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode array = mapper.valueToTree(this.prevcourses);
        WSClient ws = play.test.WSTestClient.newClient(9005);
        // send this. user
        ObjectNode res = Json.newObject();
        res.put("username", this.username);
        res.put("password", this.password);
        res.put("firstname", this.firstname);
        res.put("lastname", this.lastname);
        res.put("position", this.position);
        res.put("affiliation", this.affiliation);
        res.put("email", this.email);
        res.put("phone", this.phone);
        res.put("fax", this.fax);
        res.put("address", this.address);
        res.put("city", this.city);
        res.put("country", this.country);
        res.put("zipcode", this.zipcode);
        res.put("comments", this.comments);
        res.put("status", this.status);
        res.put("degreeplan", this.degreeplan);
        res.put("semesterstart", this.semesterstart);
        res.put("yearstart", this.yearstart);
        res.put("semesterend", this.semesterend);
        res.put("yearend", this.yearend);
        res.putArray("prevcourses").addAll(array);

        System.out.println(username);
        System.out.println(password);

        WSRequest request = ws.url("http://localhost:9005/signup");
        return request.addHeader("Content-Type", "application/json")
                .post(res)
                .thenApply((WSResponse r) -> {
                    return r;
                });
    }

}
