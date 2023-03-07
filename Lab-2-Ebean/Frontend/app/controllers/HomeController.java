package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import play.data.Form;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.login;

import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.CompletionStage;

/**
 * Software Service Market Place
 */
public class HomeController extends Controller {

    @Inject
    HttpExecutionContext ec;

    private final FormFactory formFactory;

    @Inject
    public HomeController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    /**
     * Index page
     */
    public Result index() {
        return ok(views.html.login.render(""));
    }

    /**
     * Index page
     */
    public Result signup() {
        return ok(views.html.register.render(null));
    }

    public CompletionStage<Result> loginHandler() {
        Form<User> loginForm = formFactory.form(User.class).bindFromRequest();
        if (loginForm.hasErrors())
            return (CompletionStage<Result>) badRequest(views.html.login.render(""));  // send parameter like register so that user could know

        return loginForm.get().user()
                .thenApplyAsync((WSResponse r) -> {
                    if (r.getStatus() == 200 && r.asJson() != null && !r.asJson().isBoolean()) {
                        // add username to session
                        session("username", loginForm.get().username);   // store username in session for your project
                        // redirect to index page, to display all categories
                        ObjectMapper mapper = new ObjectMapper();
                        try {
                            return ok(index.render(mapper.readValue(r.asJson().toString(), User.class)));
                        } catch (IOException e) {
                            System.out.println(r);
                            throw new RuntimeException(e);
                        }
                    } else {
                        System.out.println("response null");
                        String authorizeMessage = "Incorrect Username or Password ";
                        return badRequest(views.html.login.render(authorizeMessage));
                    }
                }, ec.current());
    }


    public CompletionStage<Result> signupHandler() {
        return signupHandler(null);
    }

    public CompletionStage<Result> signupHandler(User user) {
        Form<User> registrationForm = formFactory.form(User.class).bindFromRequest();
        if (registrationForm.hasErrors()) {
            return (CompletionStage<Result>) badRequest(views.html.register.render(""));
        }
        return registrationForm.get().registerUser()
                .thenApplyAsync((WSResponse r) -> {
                    //System.out.println(r.getBody());
                    if (r.getStatus() == 200 && r.asJson() != null) {
                        System.out.println("success");
                        return ok(login.render(""));
                    } else {
                        System.out.println("response null");
                        return badRequest(views.html.register.render("Error: " + r));
                    }
                }, ec.current());

    }
}