package api.model;

import api.data.core.ICollection;
import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;

import java.util.Date;
import java.util.List;

public class Product implements ICollection {
    @Id
    @ObjectId
    private String key;

    private String userKey;
    private String title;
    private String description;
    private Location location;
    private List<HashTag> hashTagList;
    private RentalCost rentalCost;

    private Date created;
    private Date lastUpdated;

    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public List<HashTag> getHashTagList() { return hashTagList; }
    public RentalCost getRentalCost() { return rentalCost; }

    @Override
    public Date getCreated() { return created; }

    @Override
    public Date getLastUpdated() { return lastUpdated; }


    public Product setKey(final String key) {
        this.key = key;
        return this;
    }

    public Product setUserKey(final String userKey) {
        this.userKey = userKey;
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

    public Product setRentalCost(RentalCost rentalCost) {
        this.rentalCost = rentalCost;
        return this;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Product{")
                .append("key='").append(key).append('\'')
                .append(", userKey='").append(userKey).append('\'')
                .append(", title='").append(title).append('\'')
                .append(", description='").append(description).append('\'')
                .append(", ").append(rentalCost.toString())
                .append(", created='").append(created).append('\'')
                .append(", lastUpdated='").append(lastUpdated).append('\'')
                .append("}").toString();
    }
}
