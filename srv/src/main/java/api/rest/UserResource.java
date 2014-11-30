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

    @PermitAll
    @GET("/users/login")
    public Optional<User> login(String email, String passwordHash) {
        Optional<User> user = userCredentialsRepository.checkCredentials(email, passwordHash);
        if(user.isPresent()) {
            RestxSession.current().clearPrincipal();
            RestxSession.current().authenticateAs(user.get());
            return user;
        }

        throw new WebException(HttpStatus.UNAUTHORIZED, "{ login attempt failed.}");
    }

    @PermitAll
    @GET("/users/logout")
    public String logout() {
        if(RestxSession.current().getPrincipal().isPresent()) {
            RestxSession.current().clearPrincipal();
        }

        return "{ success : true }";
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

    private User signup(UserSignup userSignup) {
        User user = null;

        try {
            user = this.userRepository.createUser(userSignup);
        } catch (MongoException.DuplicateKey e) {
            throw new WebException(HttpStatus.UNPROCESSABLE_ENTITY, String.format("{ email %s already exists.}", userSignup.getEmail()));
        }

        this.userCredentialsRepository.setCredentials(user.getKey(), userSignup.getPasswordHash());

        if (RestxSession.current().getPrincipal().isPresent()) {
            RestxSession.current().clearPrincipal();
        }

        RestxSession.current().authenticateAs(user);

        return user;
    }

    /***********************************
    *
    * Tests for authentication.
    *
    ************************************/
    @PermitAll
    @GET("/users/authenticated")
    public Optional<User> authenticated() {

        if (!RestxSession.current().getPrincipal().isPresent()) {
            throw new WebException(HttpStatus.UNAUTHORIZED);
        }

        return (Optional<User>) RestxSession.current().getPrincipal();
    }

    @RolesAllowed(Roles.BUYER_SELLER)
    @GET("/users/buyerseller")
    public Optional<User> buyerSeller() {

        if (!RestxSession.current().getPrincipal().isPresent()) {
            throw new WebException(HttpStatus.UNAUTHORIZED);
        }
        return (Optional<User>) RestxSession.current().getPrincipal();
    }

    @RolesAllowed(Roles.ADMIN)
    @GET("/users/admin")
    public Optional<User> admin() {

        if (!RestxSession.current().getPrincipal().isPresent()) {
            throw new WebException(HttpStatus.UNAUTHORIZED);
        }
        return (Optional<User>) RestxSession.current().getPrincipal();
    }
}
