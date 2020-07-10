package edu.iis.mto.blog.domain.repository;

import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    public static final String UNRELEVANT = "Unrelevant";
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    private User user;

    @Before
    public void setUp() {
        user = new User();
        user.setFirstName("Jan");
        user.setLastName("Nowak");
        user.setEmail("john@domain.com");
        user.setAccountStatus(AccountStatus.NEW);
    }

    @Test
    public void shouldFindNoUsersIfRepositoryIsEmpty() {
        List<User> users = repository.findAll();
        assertThat(users, hasSize(0));
    }

    @Test
    public void shouldFindOneUsersIfRepositoryContainsOneUserEntity() {
        User persistedUser = entityManager.persist(user);
        List<User> users = repository.findAll();

        assertThat(users, hasSize(1));
        assertThat(users.get(0)
                        .getEmail(), equalTo(persistedUser.getEmail()));
    }

    @Test
    public void shouldStoreANewUser() {
        User persistedUser = repository.save(user);

        assertThat(persistedUser.getId(), notNullValue());
    }

    @Test
    public void shouldFindUserByFirstNameAndIgnoreCase() {
        User persistedUser = repository.save(user);
        List<User> result = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase("JAN", UNRELEVANT, UNRELEVANT);
        assertTrue(result.contains(persistedUser));
    }

    @Test
    public void shouldFindUserByLastNameAndIgnoreCase() {
        User persistedUser = repository.save(user);
        List<User> result = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase(UNRELEVANT, "NoWaK", UNRELEVANT);
        assertTrue(result.contains(persistedUser));
    }

    @Test
    public void shouldFindUserByEmailAndIgnoreCase() {
        User persistedUser = repository.save(user);
        List<User> result = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase(UNRELEVANT, UNRELEVANT, "john@domain.com");
        assertTrue(result.contains(persistedUser));
    }

    @Test
    public void shouldNotFindUser() {
        User persistedUser = repository.save(user);
        List<User> result = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase(UNRELEVANT, UNRELEVANT, UNRELEVANT);
        assertFalse(result.contains(persistedUser));
    }
}
