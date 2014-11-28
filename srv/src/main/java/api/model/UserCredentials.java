package api.model;

import api.data.core.ICollection;
import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;

import java.util.Date;

public class UserCredentials implements ICollection {

    @ObjectId
    @Id
    private String key;
    private String passwordHash;
    private Date created;
    private Date lastUpdated;

    public String getKey() {
        return key;
    }

    public Date getCreated() {
        return created;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public UserCredentials setKey(final String key) {
        this.key = key;
        return this;
    }

    public UserCredentials setPasswordHash(final String passwordHash) {
        this.passwordHash = passwordHash;
        return this;
    }

    public UserCredentials setLastUpdated(final Date lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public UserCredentials setCreated(final Date created) {
        this.created = created;
        return this;
    }

    @Override
    public String toString() {
        return "UserCredentials{" +
                "key='" + key + '\'' +
                ", passwordHash='XXXXXXXXXXXX'" +
                '}';
    }
}
