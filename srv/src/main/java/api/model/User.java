package api.model;

import api.data.core.ICollection;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableSet;
import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;
import restx.security.RestxPrincipal;

import java.util.Collection;
import java.util.Date;

public class User implements RestxPrincipal, ICollection {
    @Id
    @ObjectId
    private String key;

    private String email;
    private String facebookId;
    private String firstName;
    private String lastName;
    private Date created;
    private Date lastUpdated;

    private Collection<String> roles;

    public String getKey() {
        return key;
    }

    public String getEmail() {
        return email;
    }

    public String getFacebookId() { return facebookId; }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    @Override
    public Date getCreated() { return created; }

    @Override
    public Date getLastUpdated() { return lastUpdated; }

    public Collection<String> getRoles() {
        return roles;
    }

    public User setKey(final String key) {
        this.key = key;
        return this;
    }

    public User setEmail(final String email) {
        this.email = email;
        return this;
    }

    public User setFacebookId(String facebookId) {
        this.facebookId = facebookId;
        return this;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public User setCreated(Date created) {
        this.created = created;
        return this;
    }
    public User setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public User setRoles(final Collection<String> roles) {
        this.roles = roles;
        return this;
    }

    @Override
    @JsonIgnore
    public ImmutableSet<String> getPrincipalRoles() {
        return ImmutableSet.copyOf(roles);
    }

    @Override
    @JsonIgnore
    public String getName() {
        return getEmail();
    }

    @Override
    public String toString() {
        return "User{" +
                "key='" + key + '\'' +
                ", email='" + email + '\'' +
                ", facebookId='" + facebookId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", roles=" + roles +
                '}';
    }
}
