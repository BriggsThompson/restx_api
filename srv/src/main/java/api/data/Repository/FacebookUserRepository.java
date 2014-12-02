package api.data.Repository;


import api.model.User;
import com.google.common.base.Optional;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.auth.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restx.factory.Component;

import javax.inject.Named;

@Component
public class FacebookUserRepository {

    private Facebook facebook;
    private UserRepository userRepository;

    private final static Logger logger = LoggerFactory.getLogger(FacebookUserRepository.class);


    public FacebookUserRepository(@Named("facebook") Facebook facebook,
                                  UserRepository userRepository) {

        this.facebook = facebook;
        this.userRepository = userRepository;
    }

    public Optional<User> login(Optional<String> code, String callBackURI) {
        AccessToken accessToken = null;
        facebook.setOAuthCallbackURL(callBackURI);

        if(!code.isPresent()) {
            logger.error("{ 'code' wasn't returned from Facebook Login API } ");
        }

        try {
            accessToken = facebook.getOAuthAccessToken(code.get());
        } catch (FacebookException e) {
            logger.error(e.getMessage());
            return Optional.absent();
        }

        facebook.setOAuthAccessToken(accessToken);
        facebook4j.User facebookUser = null;

        try {
            facebookUser = facebook.getUser(facebook.getId());
        } catch (FacebookException e) {
            logger.error(e.getMessage());
            return Optional.absent();
        }

        Optional<User> optionalUser = userRepository.getUser(facebookUser);
        if (optionalUser.isPresent()) {
            return Optional.fromNullable((User)optionalUser.get());
        }

        return Optional.fromNullable(userRepository.createUser(facebookUser));

    }

    public String getAuthorizationURL(String callbackURL) {
        return facebook.getOAuthAuthorizationURL(callbackURL);
    }
}