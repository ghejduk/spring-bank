package it.softwarelabs.bank.domain.user;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class UserTest {

    @Test
    public void registerWillReturnNewInstanceOfUser() {
        UserId userId = new UserId();
        Email email = new Email("james.bond@mi6.co.uk");
        FullName fullName = new FullName("James", "Bond");

        assertThat(User.register(userId, fullName, email), instanceOf(User.class));
    }

    @Test
    public void weCanGetUserEmail() {
        UserId userId = new UserId();
        Email email = new Email("james.bond@mi6.co.uk");
        FullName fullName = new FullName("James", "Bond");
        User user = User.register(userId, fullName, email);

        assertEquals(email, user.email());
    }

    @Test
    public void weCanGetUserFullName() {
        UserId userId = new UserId();
        Email email = new Email("james.bond@mi6.co.uk");
        FullName fullName = new FullName("James", "Bond");
        User user = User.register(userId, fullName, email);

        assertEquals(fullName, user.fullName());
    }

    @Test
    public void weCanGetUserId() {
        UserId userId = new UserId();
        Email email = new Email("james.bond@mi6.co.uk");
        FullName fullName = new FullName("James", "Bond");
        User user = User.register(userId, fullName, email);

        assertEquals(userId, user.id());
    }

    @Test
    public void testProcessOfUpdatingAndCheckingPassword() {
        UserFactory userFactory = new UserFactory();
        User user = userFactory.create("James", "Bond", "james.bond@mi6.co.uk");
        String password = "Admin123";

        PasswordEncoder encoder = new PasswordEncoder() {

            public PasswordHash encode(String password) {
                return new PasswordHash(password);
            }

            public boolean isValid(String password, PasswordHash passwordHash) {
                return password.equals(passwordHash.toString());
            }
        };

        user.updatePassword(password, encoder);
        boolean isValid = user.isPasswordValid(password, encoder);

        assertTrue(isValid);
    }

    @Test
    public void registerUserOnlyWithEmail() {
        String email = "james.bond@mi6.co.uk";
        User user = User.register(new Email(email));

        assertEquals(email, user.email().toString());
    }
}
