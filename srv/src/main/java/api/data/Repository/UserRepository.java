package api.data.Repository;


import api.constants.Roles;
import api.model.User;
import api.model.UserSignup;
import com.google.common.base.Optional;
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

    public User createUser(facebook4j.User facebookUser) {
        Date date = new Date();
        User user = new User()
                .setFacebookId(facebookUser.getId())
                .setEmail(facebookUser.getEmail())
                .setFirstName(facebookUser.getFirstName())
                .setLastName(facebookUser.getLastName())
                .setRoles(new ArrayList<>(asList(Roles.BUYER_SELLER)))
                .setCreated(date)
                .setLastUpdated(date);

        users.insert(user);

        return user;
    }

    public Optional<User> getUser(facebook4j.User user) {
        if (user.getEmail() != null) {
            Optional<User> u = Optional.fromNullable(users.findOne("{ $or:[ { email : # }, { facebookId : # } ] }", user.getEmail(), user.getId()).as(User.class));
            if (u.isPresent() && u.get().getFacebookId() == null) {
                u.get().setFacebookId(user.getId());
                users.save(u.get());
            }
            return u;
        }
        else
            return Optional.fromNullable(users.findOne("{ # : # }", "facebookId", user.getId()).as(User.class));
    }
}