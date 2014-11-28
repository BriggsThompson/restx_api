package api.model;

public class UserSignup {
    private String email;
    private String firstName;
    private String lastName;
    private String passwordHash;

    public String getEmail() {
        return email;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getPasswordHash() {
        return passwordHash;
    }

    public UserSignup setEmail(final String email) {
        this.email = email;
        return this;
    }

    public UserSignup setFirstName(final String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserSignup setlastName(final String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserSignup setPasswordHash(final String passwordHash) {
        this.passwordHash = passwordHash;
        return this;
    }

    @Override
    public String toString() {
        return "Signup{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                '}';
    }
}