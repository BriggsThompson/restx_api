package api.rest;

import api.data.Repository.UserCredentialsRepository;
import api.data.Repository.UserRepository;
import api.model.Roles;
import api.model.User;
import api.model.UserSignup;
import com.google.common.base.Optional;
import com.mongodb.MongoException;
import restx.WebException;
import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.http.HttpStatus;
import restx.security.PermitAll;
import restx.security.RestxSession;
import restx.security.RolesAllowed;

@Component
@RestxResource
public class UserResource {

    private final UserRepository userRepository;
    private final UserCredentialsRepository userCredentialsRepository;

    public UserResource(UserRepository userRepository, UserCredentialsRepository userCredentialsRepository) {
        this.userRepository = userRepository;
        this.userCredentialsRepository = userCredentialsRepository;
    }

//
//    @RolesAllowed(Roles.ADMIN)
//    @GET("/users")
//    public Iterable<User> findUsers() {
//        return userRepository.findAllUsers();
//    }
//
    @PermitAll
    @GET("/users/authenticated")
    public Optional<User> authenticated() {
        Optional<User> principal = (Optional<User>) RestxSession.current().getPrincipal();
        if (!principal.isPresent()) {
            throw new WebException(HttpStatus.UNAUTHORIZED);
        }

        return principal;
    }

    @RolesAllowed(Roles.BUYER_SELLER)
    @GET("/users/blocked")
    public Optional<User> getMyself() {
        Optional<User> principal = (Optional<User>) RestxSession.current().getPrincipal();
        if (!principal.isPresent()) {
            throw new WebException(HttpStatus.UNAUTHORIZED);
        }
        return principal;
    }

    /**
     * Signup for an account.
     *
     * Example post: {"firstName":"Briggs","lastName": "Thompson","email": "w.briggs.thompson@gmail.com","passwordHash": "someString"}
     *
     * @param userSignup
     * @return UserSignup
     */
    @PermitAll
    @POST("/users/signup")
    public User signupPost(UserSignup userSignup) {

        return signup(userSignup);
    }

    /**
     * Signup for an account.
     *
     * Example post: {"firstName":"Briggs","lastName": "Thompson","email": "w.briggs.thompson@gmail.com","passwordHash": "someString"}
     *
     * @param userSignup
     * @return UserSignup
     */
    @PermitAll
    @GET("/users/signup")
    public User signupGet(String email, String firstName, String lastName, String passwordHash) {
        return signup(new UserSignup().setEmail(email).setFirstName(firstName).setlastName(lastName).setPasswordHash(passwordHash));
    }

    private User signup(UserSignup userSignup) {
        User user = null;

        try {
            user = this.userRepository.createUser(userSignup);
        } catch (MongoException.DuplicateKey e) {
            throw new WebException(HttpStatus.UNPROCESSABLE_ENTITY, String.format("{ email %s already exists.}", userSignup.getEmail()));
        }

        this.userCredentialsRepository.setCredentials(user.getKey(), userSignup.getPasswordHash());

        if (!RestxSession.current().getPrincipal().isPresent()) {
            RestxSession.current().authenticateAs(user);
        }
        return user;
    }
}
