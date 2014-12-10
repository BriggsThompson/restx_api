package api.rest;

import api.constants.Roles;
import api.data.Repository.UserCredentialsRepository;
import api.data.Repository.UserRepository;
import api.model.Message;
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

@RestxResource
@Component
public class EmailUserResource {

    private final UserRepository userRepository;
    private final UserCredentialsRepository userCredentialsRepository;

    public EmailUserResource(UserRepository userRepository, UserCredentialsRepository userCredentialsRepository) {
        this.userRepository = userRepository;
        this.userCredentialsRepository = userCredentialsRepository;
    }

    @PermitAll
    @GET("/users/login")
    public Optional<User> login(String email, String passwordHash) {
        Optional<User> user = userCredentialsRepository.checkCredentials(email, passwordHash);
        if(user.isPresent()) {
            UserResourceHelper.clearAndResetPrinciple(user.get());
            return user;
        }

        throw new WebException(HttpStatus.UNAUTHORIZED, "{ login attempt failed.}");
    }

    @PermitAll
    @GET("/users/logout")
    public Message logout() {
        if(RestxSession.current().getPrincipal().isPresent()) {
            RestxSession.current().clearPrincipal();
        }

        return new Message().setMessage("LOGOUT SUCCESS = TRUE");
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
            //Make sure this isn't a user via facebook.
            Optional<User> u = this.userCredentialsRepository.findUserByName(userSignup.getEmail());
            if(u.isPresent()) {
                Optional<String> uc = this.userCredentialsRepository.findCredentialByUserName(u.get().getEmail());
                if (uc.isPresent()) {
                    throw new MongoException.DuplicateKey(null);
                }

                user = u.get();
            } else {
                user = this.userRepository.createUser(userSignup);
            }


        } catch (MongoException.DuplicateKey e) {
            throw new WebException(HttpStatus.UNPROCESSABLE_ENTITY, String.format("{ email %s already exists.}", userSignup.getEmail()));
        }

        this.userCredentialsRepository.setCredentials(user.getKey(), userSignup.getPasswordHash());
        UserResourceHelper.clearAndResetPrinciple(user);

        return user;
    }

    @PermitAll
    @GET("/users/authenticated")
    public Optional<User> authenticated() {
        return UserResourceHelper.authorizationCheck();
    }

    @RolesAllowed(Roles.BUYER_SELLER)
    @GET("/users/buyerseller")
    public Optional<User> buyerSeller() {
        return UserResourceHelper.authorizationCheck();
    }

    @RolesAllowed(Roles.ADMIN)
    @GET("/users/admin")
    public Optional<User> admin() {
        return UserResourceHelper.authorizationCheck();
    }

}
