package api.data.Repository;


import api.model.Roles;
import api.model.User;
import api.model.UserSignup;
import com.mongodb.DB;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import restx.factory.Component;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.Date;

import static java.util.Arrays.asList;

@Component
public class UserRepository {

    private DB hazittDb;
    private Jongo jongo;
    private MongoCollection users;

    public UserRepository(@Named("hazittDb") DB hazittDb) {
        this.hazittDb = hazittDb;
        this.jongo = new Jongo(this.hazittDb);
        users = this.jongo.getCollection("users");
    }

    public User createUser(UserSignup userSignup) {
        Date date = new Date();
        User user = new User()
                .setEmail(userSignup.getEmail())
                .setFirstName(userSignup.getFirstName())
                .setLastName(userSignup.getLastName())
                .setRoles(new ArrayList<>(asList(Roles.BUYER_SELLER)))
                .setCreated(date)
                .setLastUpdated(date);

        users.insert(user);

        return user;
    }
}