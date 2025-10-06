package com.project.filestorage;

import com.project.filestorage.dto.AuthRequestDto;
import com.project.filestorage.model.User;
import com.project.filestorage.repository.RoleRepository;
import com.project.filestorage.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        classes = FilestorageApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
@Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class AuthIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    private String baseUrl;

    @BeforeEach
    void setUp(){
        baseUrl = "http://localhost:" + port + "/auth";
    }

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    /*
    One method = one transaction (unless you explicitly create nested transactions)
    HTTP request boundary = new transaction because it's like a fresh request from a client
    That's why @Transactional on integration tests causes issues - the test and the actual application code run in separate transactions
     */
    void registerUser_success(){

        AuthRequestDto authRequestDto = new AuthRequestDto();
        authRequestDto.setUsername("testuser");
        authRequestDto.setPassword("password123");

        /*
        Create a new resource by POSTing the given object to the URI template,
        and returns the response as ResponseEntity.
         */
        ResponseEntity<User> response = restTemplate.postForEntity(baseUrl + "/register", authRequestDto, User.class);

        /*
        assertThat → wraps a value (boolean, object, collection, etc.) for assertion
        .isTrue() → performs the actual boolean check
        Together, it reads fluently: “assert that this condition is true”.
         */
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUsername()).isEqualTo(authRequestDto.getUsername());

        // Checking DB
        User savedUser = userRepository.findByUsername(authRequestDto.getUsername()).orElseThrow();
        assertThat(savedUser.getUsername()).isEqualTo("testuser");
        assertThat(savedUser.getRoles()).isNotEmpty();
    }

    @Test
    void loginUser_success(){

        AuthRequestDto registerDto = new AuthRequestDto();
        registerDto.setUsername("userTest");
        registerDto.setPassword("password123");
        restTemplate.postForEntity(baseUrl + "/register", registerDto, User.class);

        AuthRequestDto loginDto = new AuthRequestDto();
        loginDto.setUsername("userTest");
        loginDto.setPassword("password123");

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/login", loginDto, String.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).contains("ey"); // as JWT tokens start with "ey"
    }
}


