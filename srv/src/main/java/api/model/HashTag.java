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
    private String tag;
    private Date created;
    private Date lastUpdated;

    @Override
    public String getKey() {
        return key;
    }

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

    public void setTag(String tag) {
        this.tag = tag;
    }
}
