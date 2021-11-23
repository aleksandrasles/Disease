package com.application.disease;

import com.application.disease.dao.UserRepository;
import com.application.disease.model.User;
import com.application.disease.model.UserRoles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DiseaseMetricsApplicationTests {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void beforeEach() {
        mongoTemplate.dropCollection("users");
    }

    @Test
    void addUserToDbTest() {
        User user = new User("Liubov", "Ternenko", "love.ternenko@ukr.net", "lovve4n",
                UserRoles.MINISTRY_OF_HEALTH
        );
        assertEquals(userRepository.addUser(user), user);
    }

    @Test
    void findUserByIdTest() {
        User user = new User("Petro", "Geraschenko", "pitr.gera@ukr.net", "pitGer45",
                UserRoles.STATISTICAL_DEPARTMENT
        );
        user.setId(UUID.randomUUID().toString());
        userRepository.addUser(user);
        assertEquals(userRepository.findUserById(user.getId()), user);
    }

    @Test
    void findUserByEmailInDbTest() {
        User user = new User("Liubov", "Ternenko", "love.ternenko@ukr.net", "lovve4n",
                UserRoles.MINISTRY_OF_HEALTH
        );
        user.setId(UUID.randomUUID().toString());
        userRepository.addUser(user);
        User foundedUser = userRepository.findUserByEmail(user.getEmail()).get();
        assertEquals(user.getId(), foundedUser.getId());
    }

    @Test
    void updateUserFieldInDbTest(){
        User user = new User("Liubov", "Ternenko", "love.ternenko@ukr.net", "lovve4n",
                UserRoles.MINISTRY_OF_HEALTH
        );
        userRepository.addUser(user);
        user.setPassword("kdjfjfnK");
        userRepository.updateUser(user);
        User foundedUser = userRepository.findUserByEmail(user.getEmail()).get();
        assertEquals(foundedUser.getPassword(), user.getPassword());
    }

    @Test
    void deleteUserFromDbTest(){
        User user = new User("Liubov", "Ternenko", "love.ternenko@ukr.net", "lovve4n",
                UserRoles.MINISTRY_OF_HEALTH
        );
        user.setId(UUID.randomUUID().toString());
        assertNotNull(userRepository.addUser(user));
        userRepository.deleteUser(user.getId());
        assertNull(userRepository.findUserById(user.getId()));
    }

    @Test
    void findAllUsersTest(){
        User user1 = new User("Liubov", "Ternenko", "love.ternenko@ukr.net", "lovve4n",
                UserRoles.MINISTRY_OF_HEALTH
        );
        User user2 = new User("Petro", "Geraschenko", "pitr.gera@ukr.net", "pitGer45",
                UserRoles.STATISTICAL_DEPARTMENT
        );
        userRepository.addUser(user1);
        userRepository.addUser(user2);
        assertEquals(mongoTemplate.findAll(User.class).size(), 2);
    }

}
