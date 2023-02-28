package models;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import play.data.validation.Constraints;

import javax.json.JsonArray;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class User extends Model {
    private static final long serialVersionUID = 1L;

    @Id
    public Long id;

    @Constraints.Required
    public String username, password, firstname, lastname, position, affiliation, email, phone, fax, address, city, country, zipcode, comments, status;

    @Constraints.Required
    public JsonNode prevcourses;
    public static Find<Long, User> find = new Find<Long, User>() {
    };

    public static User findByName(String name) {
        return User.find
                .where()
                .eq("username", name)
                .findUnique();
    }
}
