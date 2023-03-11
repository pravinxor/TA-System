package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;

import java.util.concurrent.CompletionStage;

public class ListedForm {
    public String title, poster, description;

    public CompletionStage<WSResponse> getForm() {
        WSClient ws = play.test.WSTestClient.newClient(9005);
        WSRequest request = ws.url("http://localhost:9005/sampleform");
//        ObjectMapper mapper = new ObjectMapper();
//        ObjectNode form = mapper.valueToTree(this);
        String jace = "{\"title\":\""+ title+ "\"}";
        return request.addHeader("Content-Type", "application/json")
                .post(jace)
                .thenApply((WSResponse r) -> r);
    }
}
