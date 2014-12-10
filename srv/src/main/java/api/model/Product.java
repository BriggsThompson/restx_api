package api.model;

import api.data.core.ICollection;
import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;

import java.util.Date;
import java.util.List;

/**
 * Created by briggs on 12/8/14.
 */
public class Product implements ICollection {
    @Id
    @ObjectId
    private String key;

    private User user;
    private String title;
    private String description;
    private Location location;
    private Double totalCost;
    private List<HashTag> hashTagList;

    private Date created;
    private Date lastUpdated;

    public String getKey() {
        return key;
    }

    @Override
    public Date getCreated() { return created; }

    @Override
    public Date getLastUpdated() { return lastUpdated; }


    public Product setKey(final String key) {
        this.key = key;
        return this;
    }

    public Product setUser(final User user) {
        this.user = user;
        return this;
    }

    public Product setTitle(final String title) {
        this.title = title;
        return this;
    }

    public Product setDescription(final String description) {
        this.description = description;
        return this;
    }

    public Product setLocation(final Location location) {
        this.location = location;
        return this;
    }

    public Product setTotalCost(final Double totalCost) {
        this.totalCost = totalCost;
        return this;
    }

    public Product setHashTags(final List<HashTag> hashTagList) {
        this.hashTagList = hashTagList;
        return this;
    }


    public Product setCreated(Date created) {
        this.created = created;
        return this;
    }
    public Product setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    @Override
    public String toString() {
        return "";
//        return "User{" +
//                "key='" + key + '\'' +
//                ", email='" + email + '\'' +
//                ", facebookId='" + facebookId + '\'' +
//                ", firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", roles=" + roles +
//                '}';
    }
}
