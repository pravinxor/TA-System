package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Form extends Model {
    @Id
    public long id;

    @Constraints.Required
    public String title, poster, description;

    public static Find<Long, Form> find = new Find<Long, Form>() {
    };

    public static Form findByTitle(String title) {
        return Form.find
                .where()
                .eq("title", title)
                .findUnique();
    }
}
