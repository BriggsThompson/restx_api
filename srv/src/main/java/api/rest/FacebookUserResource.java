package api.rest;


import api.data.Repository.FacebookUserRepository;
import api.model.User;
import com.google.common.base.Optional;
import restx.RestxRequest;
import restx.RestxResponse;
import restx.WebException;
import restx.annotations.GET;
import restx.annotations.Param;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.http.HttpStatus;
import restx.security.PermitAll;

import java.io.IOException;

@RestxResource
@Component
public class FacebookUserResource {

    private final FacebookUserRepository facebookUserRepository;

    public FacebookUserResource(FacebookUserRepository facebookUserRepository) {
        this.facebookUserRepository = facebookUserRepository;
    }

    @PermitAll
    @GET("/users/facebook/login/callback")
    public User callback(@Param(value="request", kind= Param.Kind.CONTEXT) final RestxRequest request) throws IOException {

        Optional<User> user = facebookUserRepository.login(request.getQueryParam("code"), request.getBaseUri().concat(request.getRestxPath()));
        if (!user.isPresent()) {
            throw new WebException(HttpStatus.UNAUTHORIZED);
        }

        UserResource.clearAndResetPrinciple(user.get());

        return user.get();
    }

    @PermitAll
    @GET("/users/facebook/login")
    public void login(@Param(value="request", kind= Param.Kind.CONTEXT) RestxRequest request) throws IOException {
        final String callbackURL = request.getBaseUri().concat(request.getRestxUri()).concat("/callback");

        throw new WebException(HttpStatus.FOUND) {
            @Override
            public void writeTo(RestxRequest restxRequest, RestxResponse restxResponse) throws IOException {
                restxResponse
                        .setStatus(getStatus())
                        .setHeader("Location", facebookUserRepository.getAuthorizationURL(callbackURL));

            }
        };
    }
}
