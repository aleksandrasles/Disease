package com.application.disease.controller;

import com.application.disease.dao.UserRepository;
import com.application.disease.model.*;
import com.application.disease.strategy.GeneralRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/api/v1/admin")
@RestController
public class AdminController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Qualifier("general_disease")
    private GeneralRepository<Disease> diseaseRepository;

    @Autowired
    @Qualifier("general_region")
    private GeneralRepository<Region> regionRepository;

    @GetMapping("/test")
    public void testMomento(){
       User user = new User("Ivan", "Ivanov", "iv.iv@gmail.com","123456", UserRoles.ROLE_ADMIN);

        UserEditor userEditor = new UserEditor(user);
        user.setFirstName("Oleg");
        userEditor.hitSave();
        System.out.println(userEditor.print().getFirstName());
        user.setFirstName("Petro");
        System.out.println(userEditor.print().getFirstName());
        userEditor.hitUndo();
        System.out.println(userEditor.print().getFirstName());
    }

    @GetMapping("/diseases")
    public ResponseEntity<List<Disease>> getAllDiseases(){
        return new ResponseEntity<>(diseaseRepository.findAll(), HttpStatus.OK);
    }
    @GetMapping("/regions")
    public ResponseEntity<List<Region>> getAllRegions(){
        return new ResponseEntity<>(regionRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping("/homepage")
    public ModelAndView homePage() {
        return new ModelAndView("dashboards/admin");
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") String id){
        if(id == null ){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(userRepository.findUserById(id) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userRepository.findUserById(id), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userRepository.findAllUsers(), HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("id") String id){
        if(id == null ){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(userRepository.findUserById(id) == null || user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User updatedUser = updateUserFields(id, user);
        return new ResponseEntity<>(userRepository.updateUser(updatedUser),HttpStatus.OK);
    }

    @PostMapping("/users/add")
    public ResponseEntity<User> registerNewUser(@RequestBody User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable("id") String id){
        userRepository.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/diseases/add")
    public ResponseEntity<Disease> addDisease(@RequestBody Disease disease) {
        if(disease == null || StringUtils.isBlank(disease.getName())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(diseaseRepository.create(disease), HttpStatus.OK);
    }

    @PutMapping("/diseases/{id}")
    public ResponseEntity<Disease> updateDisease(@RequestBody Disease disease, @PathVariable("id") String id) {
        if(disease == null || StringUtils.isBlank(disease.getName())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        disease.setId(id);
        return new ResponseEntity<>(diseaseRepository.update(disease), HttpStatus.OK);
    }

    @DeleteMapping("/diseases/{id}")
    public ResponseEntity<Disease> deleteDisease(@PathVariable("id") String id) {
        if(id == null || diseaseRepository.findById(id) == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        diseaseRepository.removeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/regions/add")
    public ResponseEntity<Region> addRegion(@RequestBody Region region) {
        if(region == null || StringUtils.isBlank(region.getName())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(regionRepository.create(region), HttpStatus.OK);
    }

    @PutMapping("/regions/{id}")
    public ResponseEntity<Region> updateRegion(@RequestBody Region region, @PathVariable("id") String id) {
        if(region == null || StringUtils.isBlank(region.getName())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        region.setId(id);
        return new ResponseEntity<>(regionRepository.update(region), HttpStatus.OK);
    }

    @DeleteMapping("/regions/{id}")
    public ResponseEntity<Disease> deleteRegion(@PathVariable("id") String id) {
        if(id == null || regionRepository.findById(id) == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        regionRepository.removeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private User updateUserFields(String id, User newUser){
        User user = userRepository.findUserById(id);
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setEmail(newUser.getEmail());
        user.setEnabled(newUser.getEnabled());
        return user;
    }

}
