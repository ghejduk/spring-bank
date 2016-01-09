package it.softwarelabs.bank.domain.user;

public class User {

    private UserId id;
    private Email email;
    private FullName fullName;
    private PasswordHash passwordHash;

    protected User() {
    }

    private User(UserId id, Email email) {
        this.id = id;
        this.email = email;
    }

    private User(UserId id, FullName fullName, Email email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }

    public static User register(Email email) {
        return new User(new UserId(), email);
    }

    public static User register(UserId id, FullName fullName, Email email) {
        return new User(id, fullName, email);
    }

    public UserId id() {
        return id;
    }

    public Email email() {
        return email;
    }

    public FullName fullName() {
        return fullName;
    }

    public boolean isPasswordValid(String password, PasswordEncoder encoder) {
        return encoder.isValid(password, passwordHash);
    }

    public void updatePassword(String password, PasswordEncoder encoder) {
        passwordHash = encoder.encode(password);
    }

    public PasswordHash passwordHash() {
        return passwordHash;
    }
}

