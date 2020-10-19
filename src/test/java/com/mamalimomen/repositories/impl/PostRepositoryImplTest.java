package com.mamalimomen.repositories.impl;

import com.mamalimomen.base.controllers.utilities.InValidDataException;
import com.mamalimomen.domains.Post;
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
        pr = new PostRepositoryImpl(em);
    }

    @AfterEach
    void afterEach() {
        pr.closeEntityManger();
        pr = null;
    }

    @Test
    @Order(1)
    @DisplayName("Find all Posts")
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void findAllPostsTest() {
        List<Post> posts = pr.findAllPosts();
        assertTrue(posts.isEmpty());

        try {
            Post post = new Post();
            post.setImagePath("test://test/test/test/test");
            post.setCreateDate(new Date(System.currentTimeMillis()));
            post.setText("text text text text");

            pr.saveOne(post);

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
        } catch (InValidDataException e) {
            fail();
        }
    }
}