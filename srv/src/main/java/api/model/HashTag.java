package api.model;

import api.data.core.ICollection;
import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;

import java.util.Date;

/**
 * Created by briggs on 12/8/14.
 */
public class HashTag implements ICollection {

    @Id
    @ObjectId
    private String key;
    @ObjectId
    private String userKey;
    private String tag;
    private Date created;
    private Date lastUpdated;

    @Override
    public String getKey() {
        return key;
    }

    public String getUserKey() { return userKey; }

    @Override
    public Date getCreated() {
        return created;
    }

    @Override
    public Date getLastUpdated() {
        return lastUpdated;
    }

    public String getTag() {
        return tag;
    }

    public HashTag setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public HashTag setKey(String key) {
        this.key = key;
        return this;
    }

    public HashTag setCreated(Date created) {
        this.created = created;
        return this;
    }

    public HashTag setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public HashTag setUserKey(String userKey) {
        this.userKey = userKey;
        return this;
    }
}
