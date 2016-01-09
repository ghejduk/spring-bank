package it.softwarelabs.bank.domain.user;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class UserFactoryTest {

    @Test
    public void testCreateMethod() {
        String firstName = "James";
        String lastName = "Bond";
        String email = "james.bond@mi6.co.uk";
        UserFactory userFactory = new UserFactory();
        User user = userFactory.create(firstName, lastName, email);

        assertEquals(firstName + " " + lastName, String.valueOf(user.fullName()));
        assertSame(email, String.valueOf(user.email()));
    }

    @Test
    public void createsUserWithId() {
        UserFactory userFactory = new UserFactory();
        User user = userFactory.create("James", "Bond", "james.bond@mi6.co.uk");

        assertThat(user.id(), instanceOf(UserId.class));
    }

    @Test
    public void createUserOnlyWithEmail() {
        UserFactory userFactory = new UserFactory();
        String email = "james.bond@mi6.co.uk";
        User user = userFactory.create(email);

        assertEquals(email, user.email().toString());
    }
}