package com.application.disease.controller;
import com.application.disease.model.proxy.User;
import com.application.disease.model.UserRoles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AdminControllerTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @BeforeEach
    void beforeEach() {
        mongoTemplate.dropCollection("users");
        User user1 = new User("Liubov", "Ternenko", "love.ternenko@ukr.net", "lovve4n",
                UserRoles.ROLE_MINISTRY_OF_HEALTH
        );
        User user2 = new User("Petro", "Geraschenko", "pitr.gera@ukr.net", "pitGer45",
                UserRoles.ROLE_STATISTICAL_DEPARTMENT
        );
        user1.setId("1");
        user2.setId("2");
        mongoTemplate.insert(user1);
        mongoTemplate.insert(user2);
    }

    @Test
    public void getUserTest() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(
                new URL("http://localhost:8080/api/v1/admin/users/1").toString(), String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"id\":\"1\",\"firstName\":\"Liubov\",\"lastName\":\"Ternenko\",\"email\":\"love.ternenko@ukr.net\",\"password\":\"lovve4n\",\"userRoles\":\"MINISTRY_OF_HEALTH\",\"locked\":false,\"enabled\":true,\"username\":\"love.ternenko@ukr.net\",\"authorities\":[{\"authority\":\"MINISTRY_OF_HEALTH\"}],\"accountNonLocked\":true,\"accountNonExpired\":true,\"credentialsNonExpired\":true}", response.getBody());
    }

    @Test
    public void getNotExistsUserTest() throws Exception {

        ResponseEntity<String> response = restTemplate.getForEntity(
                new URL("http://localhost:8080/api/v1/admin/users/3").toString(), String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getAllUsersTest() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(
                new URL("http://localhost:8080/api/v1/admin/users/all").toString(), String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("[{\"id\":\"1\",\"firstName\":\"Liubov\",\"lastName\":\"Ternenko\",\"email\":\"love.ternenko@ukr.net\",\"password\":\"lovve4n\",\"userRoles\":\"MINISTRY_OF_HEALTH\",\"locked\":false,\"enabled\":true,\"username\":\"love.ternenko@ukr.net\",\"authorities\":[{\"authority\":\"MINISTRY_OF_HEALTH\"}],\"accountNonLocked\":true,\"accountNonExpired\":true,\"credentialsNonExpired\":true},{\"id\":\"2\",\"firstName\":\"Petro\",\"lastName\":\"Geraschenko\",\"email\":\"pitr.gera@ukr.net\",\"password\":\"pitGer45\",\"userRoles\":\"STATISTICAL_DEPARTMENT\",\"locked\":false,\"enabled\":true,\"username\":\"pitr.gera@ukr.net\",\"authorities\":[{\"authority\":\"STATISTICAL_DEPARTMENT\"}],\"accountNonLocked\":true,\"accountNonExpired\":true,\"credentialsNonExpired\":true}]", response.getBody());
    }

    @Test
    public void registerNewUser() throws IOException {
        String requestJson = "{\"firstName\": \"Ivan\",\"lastName\": \"Sternenko\",\"email\": \"iv.sternenko@ukr.net\",\"password\": \"iv231298\",\"userRoles\": \"ADMIN\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
        String answer = restTemplate.postForObject(new URL("http://localhost:8080/api/v1/admin/users/new").toString(), entity, String.class);
        System.out.println(answer);
        assertTrue(answer.contains("iv.sternenko@ukr.net"));
    }

    @Test
    public void DeleteUser() throws MalformedURLException {
        restTemplate.delete(
                new URL("http://localhost:8080/api/v1/admin/users/1").toString());
        assertNull(mongoTemplate.findById("1", User.class));
    }

}
