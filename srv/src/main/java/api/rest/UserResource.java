package api.rest;

import api.model.User;
import com.google.common.base.Optional;
import restx.WebException;
import restx.http.HttpStatus;
import restx.security.RestxSession;

public class UserResource {

    public static Optional<User> authorizationCheck() {
        if (!RestxSession.current().getPrincipal().isPresent()) {
            throw new WebException(HttpStatus.UNAUTHORIZED);
        }

        return (Optional<User>) RestxSession.current().getPrincipal();
    }


    public static void clearAndResetPrinciple(User user) {
        if (RestxSession.current().getPrincipal().isPresent()) {
            RestxSession.current().clearPrincipal();
        }

        RestxSession.current().authenticateAs(user);
    }
}
