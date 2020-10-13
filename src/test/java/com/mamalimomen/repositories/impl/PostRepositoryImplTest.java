package com.mamalimomen.repositories.impl;

import com.mamalimomen.JUnit5SuitTest;
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

import static junit.framework.TestCase.*;

@Tag("repository")
class PostRepositoryImplTest {
    public static User user;
    public static Account account;
    public static Post post;
    public static Post post2;
    static long time;
    static EntityManager em;
    static PostRepository postRepository;
    static AccountRepository accountRepository;

    @BeforeAll
    static void beforeAll() {
        try {
            em = JUnit5SuitTest.emf.createEntityManager();
            postRepository = new PostRepositoryImpl(em);
            accountRepository = new AccountRepositoryImpl(em);

            user = new User();
            account = new Account();
            post = new Post();
            post2 = new Post();

            user.setPassword("test");
            user.setUsername("test");
            user.setFirstName("test");
            user.setLastName("test");
            user.setAboutMe("test");

            account.setUser(user);

            post.setText("test");
            post.setInsertDate(new Date(System.currentTimeMillis()));
            post.setAccount(account);

            post2.setText("test");
            post2.setInsertDate(new Date(System.currentTimeMillis()));
            post2.setAccount(account);

            accountRepository.saveOne(account);

            assertTrue(postRepository.saveOne(post));
            assertTrue(postRepository.saveOne(post2));
        } catch (Throwable t) {
            fail("exception");
        }
    }

    @BeforeEach
    void beforeEach() {
        time = System.nanoTime();
    }

    @AfterEach
    void afterEach() {
        System.out.println((System.nanoTime() - time) / 1000000000.0);
    }

    @Test
    @Order(2)
    @DisplayName("Update One Account Test")
    void updateOne() {
        List<Post> posts = postRepository.findManyPostsByAccountUsername("test");
        Post post = posts.get(0);
        post.setText("test test");
        assertTrue(postRepository.updateOne(post));

        post = posts.get(1);
        post.setText("test test");
        assertTrue(postRepository.updateOne(post));
    }

    @Test
    @Order(3)
    @DisplayName("Find All Posts Test")
    void findAllPosts() {
        List<Post> posts = postRepository.findAllPosts();
        assertFalse(posts.isEmpty());
    }

    @Test
    @Order(4)
    @DisplayName("Find Many Posts Test")
    void findManyPostsByAccountUsername() {
        List<Post> posts = postRepository.findManyPostsByAccountUsername("test");
        assertFalse(posts.isEmpty());
    }

    @AfterAll
    @DisplayName("Close EntityManager Test")
    static void closeEntityManger() {
        List<Post> posts = postRepository.findManyPostsByAccountUsername("test");
        assertTrue(postRepository.deleteOne(posts.get(0)));

        assertTrue(postRepository.deleteOne(posts.get(1)));

        assertFalse(postRepository.deleteOne(post));

        accountRepository.deleteOne(account);
        postRepository.closeEntityManger();
    }
}