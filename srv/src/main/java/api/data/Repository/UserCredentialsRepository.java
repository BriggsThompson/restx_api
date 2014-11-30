package api.data.Repository;


import api.model.User;
import api.model.UserCredentials;
import com.google.common.base.Optional;
import com.mongodb.DB;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import restx.factory.Component;
import restx.security.CredentialsStrategy;

import javax.inject.Named;
import java.util.Date;

@Component
public class UserCredentialsRepository implements restx.security.UserRepository<User> {

    private final DB hazittDb;
    private final CredentialsStrategy credentialsStrategy;
    private Jongo jongo;
    private MongoCollection users;
    private MongoCollection usersCredentials;

    public UserCredentialsRepository(@Named("hazittDb") DB hazittDb,
                                     CredentialsStrategy credentialsStrategy) {
        this.hazittDb = hazittDb;
        this.jongo = new Jongo(this.hazittDb);

        this.usersCredentials = this.jongo.getCollection("userCredentials");
        this.users = this.jongo.getCollection("users");
        this.credentialsStrategy = credentialsStrategy;
    }

    public void setCredentials(String key, String passwordHash) {
        UserCredentials userCredentials = findCredentialsForUserKey(key);

        if (userCredentials == null) {
            userCredentials = new UserCredentials().setKey(key);
        }

        Date date = new Date();
        usersCredentials.save(
                userCredentials
                        .setPasswordHash(credentialsStrategy.cryptCredentialsForStorage(key, passwordHash))
                        .setLastUpdated(date)
                        .setCreated(date));
    }

    public Optional<User> checkCredentials(String email, String passwordHash) {
        Optional<User> user = findUserByName(email);
        if (!user.isPresent()) {
            return Optional.absent();
        }

        UserCredentials userCredentials = findCredentialsForUserKey(user.get().getKey());

        if (userCredentials == null) {
            return Optional.absent();
        }

        boolean authenticated = credentialsStrategy.checkCredentials(
                user.get().getKey(),
                passwordHash,
                userCredentials.getPasswordHash());
        if (!authenticated)
            return Optional.absent();

        return user;
    }

    @Override
    public Optional<String> findCredentialByUserName(String name) {
        Optional<User> user = findUserByName(name);
        if (!user.isPresent()) {
            return Optional.absent();
        }
        UserCredentials credentials = findCredentialsForUserKey(user.get().getKey());
        if (credentials == null) {
            return Optional.absent();
        }

        return Optional.fromNullable(credentials.getPasswordHash());
    }

    private UserCredentials findCredentialsForUserKey(String userKey) {
        return usersCredentials.findOne("{ _id:# }", new ObjectId(userKey)).as(UserCredentials.class);
    }

    @Override
    public Optional<User> findUserByName(String name) {
        return Optional.fromNullable(users.findOne("{ email : #}", name).as(User.class));
    }

    @Override
    public boolean isAdminDefined() {
        return false;
    }

    @Override
    public User defaultAdmin() {
        return null;
    }
}