package com.mamalimomen.repositories.impl;

import com.mamalimomen.base.controllers.utilities.InValidDataException;
import com.mamalimomen.domains.Account;
import com.mamalimomen.domains.Post;
import com.mamalimomen.domains.User;
import com.mamalimomen.repositories.AccountRepository;
import com.mamalimomen.repositories.PostRepository;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.*;

@Tag("repository")
class PostRepositoryImplTest {
    static EntityManagerFactory emf;
    AccountRepository ar;
    PostRepository pr;
    EntityManager em;

    @BeforeAll
    static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("persistence-unit-one");
    }

    @AfterAll
    static void afterAll() {
        emf.close();
    }

    @BeforeEach
    void beforeEach() {
        em = emf.createEntityManager();
        ar = new AccountRepositoryImpl(em);
        pr = new PostRepositoryImpl(em);
    }

    @AfterEach
    void afterEach() {
        ar.closeEntityManger();
        ar = null;
        pr.closeEntityManger();
        pr = null;
    }

    @Test
    @Order(1)
    @DisplayName("Find all Posts")
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void findAllPostsTest() {
        List<Post> posts = pr.findAllPosts();
        assertTrue(posts.isEmpty());

        try {
            User user = new User();
            user.setAboutMe("test test test test test");
            user.setFirstName("test");
            user.setLastName("test");
            user.setUsername("test");
            user.setPassword("Test2120");

            Account account = new Account();
            account.setUser(user);
            ar.saveOne(account);

            Post post = new Post();
            post.setImagePath("test://test/test/test/test");
            post.setCreateDate(new Date(System.currentTimeMillis()));
            post.setText("text text text text text");

            account.addPost(post);
            ar.updateOne(account);

            posts = pr.findAllPosts();
            Post post2 = posts.get(0);
            assertEquals(post, post2);

            post2.setDeleted(true);
            pr.updateOne(post2);
            posts = pr.findAllPosts();
            assertTrue(posts.isEmpty());

            pr.deleteOne(post2);
            posts = pr.findAllPosts();
            assertTrue(posts.isEmpty());

            ar.deleteOne(account);
        } catch (InValidDataException e) {
            fail();
        }
    }
}