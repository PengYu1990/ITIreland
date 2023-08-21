package com.hugo.itireland.service.impl;

import com.hugo.itireland.domain.User;
import com.hugo.itireland.repository.ImageRepository;
import com.hugo.itireland.repository.PostRepository;
import com.hugo.itireland.repository.UserRepository;
import com.hugo.itireland.s3.S3Buckets;
import com.hugo.itireland.s3.S3Service;
import com.hugo.itireland.service.UserService;
import com.hugo.itireland.web.dto.response.UserResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
class UserServiceImplTest {


    private UserService underTest;
    @Mock
    private UserRepository userRepository;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private S3Service s3Service;

    @Mock
    private S3Buckets s3Buckets;

    private AutoCloseable autoCloseable;


    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new UserServiceImpl(userRepository, imageRepository, postRepository, s3Service, s3Buckets);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void findAll() {
        // When
        underTest.findAll();
        // Then
        verify(userRepository).findAll();
    }

    @Test
    void testFindAll() {
        // Given

        // When

        // Then
    }

    @Test
    void findById() {
        // Given
        Long id = 1L;
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        UserResponse except = new UserResponse();
        except.setId(user.getId());

        // When
        UserResponse actual = underTest.findById(id);
        // Then
        assertThat(actual).isEqualTo(except);
    }

    @Test
    void exist() {
        // Given

        // When

        // Then
    }

    @Test
    void findByUsername() {
        // Given

        // When

        // Then
    }

    @Test
    void uploadProfileImage() {
        // Given

        // When

        // Then
    }
}