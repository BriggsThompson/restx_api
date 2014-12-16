package api.model;

import api.data.core.ICollection;
import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;

import java.util.Date;

public class Image implements ICollection {

    @ObjectId
    @Id
    private String key;
    private String userKey;
    private String productKey;
    private String originalFileName;
    private String contentType;
    private Long contentLength;

    private Date created;
    private Date lastUpdated;

    public String getKey() {
        return key;
    }

    public String getUserKey() { return userKey; }

    public String getProductKey() { return productKey; }

    public String getOriginalFileName() { return originalFileName; }

    public String getContentType() { return contentType; }

    public Long getContentLength() { return contentLength; }

    public String getFileName() { return this.key + this.contentType; }

    public Date getCreated() {
        return created;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public Image setUserKey(String userKey) {
        this.userKey = userKey;
        return this;
    }

    public Image setProductKey(String productKey) {
        this.productKey = productKey;
        return this;
    }

    public Image setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
        return this;
    }

    public Image setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public Image setContentLength(Long contentLength) {
        this.contentLength = contentLength;
        return this;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
