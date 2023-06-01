
package com.back.portfolio1.Security.controller;

import com.back.portfolio1.Security.models.ERole;
import com.back.portfolio1.Security.models.RoleEntity;
import com.back.portfolio1.Security.models.UserEntity;
import com.back.portfolio1.Security.repositories.UserRepository;
import jakarta.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class PrincipalController {
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/hello")
    public String hello(){
    return "Hello World not secured";
}
    
    
    @GetMapping("/helloSecured")
    public String helloSecured(){
    return "Hello World secured";
}
    
    
    
    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO createUserDTO){
    
        Set<RoleEntity> roles = createUserDTO.getRoles().stream()
                .map(role ->RoleEntity.builder()
                .name(ERole.valueOf(role))
                .build())
                .collect(Collectors.toSet());
        
        UserEntity userEntity = UserEntity.builder()
                .username(createUserDTO.getUsername())
                .password(createUserDTO.getPassword())
                .email(createUserDTO.getEmail())
                .roles(roles)
                .build();
        
        userRepository.save(userEntity);
        
        return ResponseEntity.ok(userEntity);
    }
    
    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam String id){
    
        userRepository.deleteById(Long.parseLong(id));
        
        return "Se ha borrado el user con id ".concat(id);
        
        
    }
    
    
}
