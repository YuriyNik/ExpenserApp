package restapp.controller;
import model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import restapp.service.UserDetailsServiceImp;
import restapp.repository.UserRepository;
import restapp.controller.RestappController;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestappControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;

    @Test
    void testPostNewUser() {
        User user = new User("testUser", "password", "ROLE_USER");
       // when(userRepository.findByUsername("testUser")).thenReturn(null);
       // when(userDetailsServiceImp.createUser(user)).thenReturn(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);

        User responseUser = restTemplate.postForObject("/user", requestEntity, User.class);

        //assertEquals("testUser", responseUser.getUsername());
    }

    @Test
    void testPostUserProfile() {
        User user = new User("testUser", "password", "ROLE_USER");
     //   user.setCreated(LocalDateTime.now());

     //   when(userRepository.findByUsername("testUser")).thenReturn(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("testUser", "password");
        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);

        User responseUser = restTemplate.postForObject("/userProfile", requestEntity, User.class);

        assertEquals("testUser", responseUser.getUsername());
    }

    // Добавьте тесты для других методов RestappController по аналогии
}
