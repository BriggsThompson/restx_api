package api.data.core;

public class MongoDbConfig {


    private String host;
    private String user;
    private String password;
    private int port;
    private String database;

    public String getHost() { return host; }
    public String getUser() { return user; }
    public String getPassword() { return password; }
    public String getDatabase() { return database; }
    public int getPort() { return port; }

    public MongoDbConfig setHost(String host) {
        this.host = host;
        return this;
    }

    public MongoDbConfig setUser(String user) {
        this.user = user;
        return this;
    }

    public MongoDbConfig setPassword(String password) {
        this.password = password;
        return this;
    }

    public MongoDbConfig setPort(int port) {
        this.port = port;
        return this;
    }

    public MongoDbConfig setDatabase(String database) {
        this.database = database;
        return this;
    }
}
