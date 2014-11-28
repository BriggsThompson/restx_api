package api.data.core;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Arrays;

public class MongoDbConnector {

    private final int CONNECT_TIME_OUT_DURATION = 100;

    /***
     * Establishes a connection to mongo given a mongoDbConfig object.
     * @param mongoDbConfig
     * @return
     * @throws UnknownHostException
     */
    public DB establishConnection(MongoDbConfig mongoDbConfig) throws UnknownHostException {

        DB mongoDb = null;

        MongoClientOptions mongoClientOptions = new MongoClientOptions.Builder()
                .readPreference(ReadPreference.primary())
                .connectTimeout(CONNECT_TIME_OUT_DURATION)
                .build();

        MongoCredential mongoCredential = MongoCredential.createMongoCRCredential(
                mongoDbConfig.getUser(),
                mongoDbConfig.getDatabase(),
                mongoDbConfig.getPassword().toCharArray());

        MongoClient mongoClient = new MongoClient(new ServerAddress(mongoDbConfig.getHost()),
                Arrays.asList(mongoCredential),
                mongoClientOptions);

        mongoDb = mongoClient.getDB(mongoDbConfig.getDatabase());

        return mongoDb;
    }
}
