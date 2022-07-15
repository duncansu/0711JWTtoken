package com.example.demo.controller;

import com.example.demo.model.FinalUser;
import com.example.demo.model.Register;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserService;
import com.example.demo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserServiceImpl userServiceimple;
    @Autowired
    AuthService authService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public List<FinalUser> getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getOneUser(@PathVariable(name="id") String id, Principal principal) {
        String userId = principal.getName();
        if (!id.equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "get another user account is forbidden"));
        }
        return userService.getOneUser(id);
    }

    @ResponseBody
    @PostMapping(value = "/")
    public ResponseEntity<?> createUser(@RequestBody Register register) {
        return userService.createUser(register);
    }
    @GetMapping (value="/delete")
    public ResponseEntity<Map<String,String>> DeleteByName(@RequestParam (name ="name") String name){
        if(userServiceimple.findByName(name).isEmpty()){
            Map<String,String>response1= Collections.singletonMap("刪除狀態:","失敗，名字不在DB裡面");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response1);
        }
        else {
            userServiceimple.deleteByName(name);
            Map<String,String>response1= Collections.singletonMap("刪除狀態:","成功");
            return ResponseEntity.status(HttpStatus.OK).body(response1);
        }
    }

    @PostMapping(value = "/updateinformation")
    public ResponseEntity<Map<String,String>> UpdateTheInformation(@RequestParam (name="name")String name,
                                                                   @RequestParam(name="email") String email){
        if(userServiceimple.findByName(name).isEmpty()||userServiceimple.findByEmail(email).isEmpty()){
            Map<String,String>response1= Collections.singletonMap("更新狀態:","失敗，名字或email不在DB裡面");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response1);
        }
        else {
            userServiceimple.UpdateTheInformation(name, email);
            Map<String,String>response1= Collections.singletonMap("更新狀態:","成功");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response1);
        }
    }
}
