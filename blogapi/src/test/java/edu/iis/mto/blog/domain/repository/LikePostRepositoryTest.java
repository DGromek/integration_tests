package edu.iis.mto.blog.domain.repository;

import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.model.LikePost;
import edu.iis.mto.blog.domain.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LikePostRepositoryTest {

    private static final String UNRELEVANT = "UNRELEVANT";

    private User user;
    private BlogPost blogPost;
    private LikePost likePost;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LikePostRepository likePostRepository;

    @Before
    public void setUp() {
        user = new User();
        user.setFirstName(UNRELEVANT);
        user.setLastName(UNRELEVANT);
        user.setEmail(UNRELEVANT);
        user.setAccountStatus(AccountStatus.NEW);
        entityManager.persist(user);

        blogPost = new BlogPost();
        blogPost.setUser(user);
        blogPost.setEntry(UNRELEVANT);
        entityManager.persist(blogPost);

        likePost = new LikePost();
        likePost.setUser(user);
        likePost.setPost(blogPost);
    }

    @Test
    public void shouldStoreANewLikePost() {
        LikePost persistedLikePost = likePostRepository.save(likePost);
        LikePost result = likePostRepository.getOne(persistedLikePost.getId());

        assertTrue(persistedLikePost.equals(result));
    }

    @Test
    public void shouldReturnEditedLikePost() {
        LikePost persistedLikePost = likePostRepository.save(likePost);
        user.setLastName("New Last Name");
        persistedLikePost.setUser(user);
        persistedLikePost = likePostRepository.save(persistedLikePost);

        LikePost result = likePostRepository.getOne(persistedLikePost.getId());

        assertTrue(persistedLikePost.getUser()
                                    .getLastName()
                                    .equals(result.getUser()
                                                  .getLastName()));
    }

    @Test
    public void shouldFindLikePostByUserAndPost() {
        LikePost persistedLikePost = likePostRepository.save(likePost);
        Optional<LikePost> result = likePostRepository.findByUserAndPost(user, blogPost);

        assertTrue(persistedLikePost.equals(result.get()));
    }
}