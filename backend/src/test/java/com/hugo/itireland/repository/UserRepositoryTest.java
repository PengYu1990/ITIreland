package com.hugo.itireland.repository;

import com.hugo.itireland.AbstractTestContainers;
import com.hugo.itireland.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest extends AbstractTestContainers {


    @Autowired
    private UserRepository userRepository;


    @Test
    void findByUsernameAndPassword() {
        // Given
        User user = new User();
        user.setUsername("ypydd");
        user.setPassword("123456");
        userRepository.save(user);

        // When
        User user1 = userRepository.findByUsernameAndPassword("ypydd", "123456");

        // Then
        assertEquals(user.getUsername(), user1.getUsername());
        assertEquals(user.getPassword(), user1.getPassword());

    }

    @Test
    void findByUsername() {
        // Given
        User user = new User();
        user.setUsername("ypydd");
        user.setPassword("123456");
        userRepository.save(user);

        // When
        User user1 = userRepository.findByUsername("ypydd");


        // Then
        assertEquals(user.getUsername(), user1.getUsername());
    }

    @Test
    void existsByUsername() {
        // Given
        User user = new User();
        user.setUsername("ypydd");
        user.setPassword("123456");
        userRepository.save(user);

        // When


        // Then
        assertEquals(true, userRepository.existsByUsername("ypydd"));
        assertEquals(false, userRepository.existsByUsername("ypydd99"));
    }

    @Test
    void existsByEmail() {
        // Given
        User user = new User();
        user.setUsername("ypydd");
        user.setPassword("123456");
        user.setEmail("ypydd@163.com");
        userRepository.save(user);

        // When

        // Then
        assertEquals(true, userRepository.existsByEmail("ypydd@163.com"));
        assertEquals(false, userRepository.existsByEmail("ypydd99@163.com"));
    }
}