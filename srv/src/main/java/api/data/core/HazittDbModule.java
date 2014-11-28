package api.data.core;

import com.mongodb.DB;
import restx.config.Settings;
import restx.config.SettingsKey;
import restx.factory.Module;
import restx.factory.Provides;

import java.net.UnknownHostException;

@Module
public class HazittDbModule {


    @Settings
    public static interface HazittDbConfig {
        @SettingsKey(key = "hazitt.mongodb.host")
        String hazittMongoHost();

        @SettingsKey(key = "hazitt.mongodb.user")
        String hazittMongoUser();

        @SettingsKey(key = "hazitt.mongodb.password")
        String hazittMongoPassword();

        @SettingsKey(key = "hazitt.mongodb.database")
        String hazittMongoDatabase();

        @SettingsKey(key = "hazitt.mongodb.port")
        int hazittMongoPort();
    }

    @Provides
    public DB hazittDb(HazittDbConfig hazittDbConfig) throws UnknownHostException {
        MongoDbConfig config = new MongoDbConfig()
                .setHost(hazittDbConfig.hazittMongoHost())
                .setUser(hazittDbConfig.hazittMongoUser())
                .setPassword(hazittDbConfig.hazittMongoPassword())
                .setDatabase(hazittDbConfig.hazittMongoDatabase())
                .setPort(hazittDbConfig.hazittMongoPort());

        return new MongoDbConnector().establishConnection(config);
    }
}
