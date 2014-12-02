package api.constants;

import restx.config.Settings;
import restx.config.SettingsKey;

@Settings
public interface ISettings {

    @SettingsKey(key = "facebook.app_id")
    String facebookAppId();

    @SettingsKey(key = "facebook.secret_key")
    String facebookSecretKey();


}