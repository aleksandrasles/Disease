package com.application.disease.controller;

import com.application.disease.dao.DiseaseRepository;
import com.application.disease.dao.RegionRepository;
import com.application.disease.dao.UserRepository;
import com.application.disease.model.Disease;
import com.application.disease.model.Region;
import com.application.disease.model.User;
import com.application.disease.model.dto.RequestDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    private DiseaseRepository diseaseRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/test_builder")
    public ResponseEntity<RequestDto> testBuilder() {
        RequestDto requestDto = RequestDto.newBuilder()
                .setId("1")
                .setDiseaseName("Covid-19")
                .setRegionName("Kyiv")
                .setNumberOfIll(3)
                .setNumberOfRecovered(20)
                .setUserId("2")
                .build();
        return new ResponseEntity<>(requestDto, HttpStatus.OK);
    }

    @RequestMapping("/test_singleton1")
    public void testSingleton1() {
        System.out.println(bCryptPasswordEncoder);
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

    @GetMapping("/diseases")
    public ResponseEntity<List<Disease>> getAllDiseases(){
        return new ResponseEntity<>(diseaseRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/diseases/add")
    public ResponseEntity<Disease> addDisease(@RequestBody Disease disease) {
        if(disease == null || StringUtils.isBlank(disease.getName())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(diseaseRepository.create(disease), HttpStatus.OK);
    }

    @DeleteMapping("/diseases/{id}")
    public ResponseEntity<Disease> deleteDisease(@PathVariable("id") String id) {
        if(id == null || diseaseRepository.findById(id) == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        diseaseRepository.removeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/regions")
    public ResponseEntity<List<Region>> getAllRegions(){
        return new ResponseEntity<>(regionRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/regions/add")
    public ResponseEntity<Region> addRegion(@RequestBody Region region) {
        if(region == null || StringUtils.isBlank(region.getName())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(regionRepository.create(region), HttpStatus.OK);
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
