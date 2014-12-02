package api.data;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import restx.config.Settings;
import restx.config.SettingsKey;
import restx.factory.Module;
import restx.factory.Provides;

@Module
public class FacebookModule {

    @Settings
    public static interface FacebookConfig {
        @SettingsKey(key = "hazitt.facebook.app_id")
        String hazittFacebookAppId();

        @SettingsKey(key = "hazitt.facebook.secret_key")
        String hazittFacebookSecretKey();

        @SettingsKey(key = "hazitt.facebook.permissions")
        String hazittFacebookPermissions();
    }

    @Provides
    public Facebook facebook(FacebookConfig facebookConfig) {
        Facebook facebook = new FacebookFactory().getInstance();
        facebook.setOAuthAppId(facebookConfig.hazittFacebookAppId(), facebookConfig.hazittFacebookSecretKey());
        facebook.setOAuthPermissions(facebookConfig.hazittFacebookPermissions());
        return facebook;
    }
}
