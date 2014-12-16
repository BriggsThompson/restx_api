package api.model;

import api.data.core.ICollection;
import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;

import java.io.InputStream;
import java.util.ArrayList;
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
    private List<String> hashTagList;
    private List<String> imageList;
    private RentalCost rentalCost;
    private InputStream photoStream;

    private Date created;
    private Date lastUpdated;
    private Display display;

    public String getKey() {
        return key;
    }
    public String getUserKey() {
        return userKey;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public List<String> getHashTagList() { return hashTagList == null ? hashTagList = new ArrayList<String>() : hashTagList; }
    public List<String> getImageList() { return imageList == null ? imageList = new ArrayList<String>() : imageList; }
    public RentalCost getRentalCost() { return rentalCost; }
    public InputStream getPhotoStream() { return photoStream; }

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

    public Product setHashTags(final List<String> hashTagList) {
        this.hashTagList = hashTagList;
        return this;
    }

    public Product setImageList(final List<String> imageList) {
        this.imageList = imageList;
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

    public Product setPhotoStream(InputStream photoStream) {
        this.photoStream = photoStream;
        return this;
    }

    public Display getDisplay() {

        if (display == null) {
            display = new Display()
                    .setTitle(this.getTitle())
                    .setDescription(this.getDescription())
                    .setKey(this.getKey())
                    .setUserKey(this.getUserKey())
                    .setHashTagList(this.getHashTagList())
                    .setImageList(this.getImageList())
                    .setRentalCost(this.getRentalCost());
        }

        return display;
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

    public class Display {
        private String key;
        private String userKey;
        private String title;
        private String description;
        private List<String> hashTagList;
        private List<String> imageList;
        private RentalCost rentalCost;

        public Display setKey(String key) {
            this.key = key;
            return this;
        }

        public Display setUserKey(String userKey) {
            this.userKey = userKey;
            return this;
        }

        public Display setTitle(String title) {
            this.title = title;
            return this;
        }

        public Display setDescription(String description) {
            this.description = description;
            return this;
        }

        public Display setHashTagList(List<String> hashTagList) {
            this.hashTagList = hashTagList;
            return this;
        }

        public Display setImageList(List<String> imageList) {
            this.imageList = imageList;
            return this;
        }

        public Display setRentalCost(RentalCost rentalCost) {
            this.rentalCost = rentalCost;
            return this;
        }

        public String getKey() { return key; }

        public String getUserKey() { return userKey; }

        public String getTitle() { return title; }

        public String getDescription() { return description; }

        public List<String> getHashTagList() { return hashTagList; }

        public List<String> getImageList() { return imageList; }

        public RentalCost getRentalCost() { return rentalCost; }
    }


}
