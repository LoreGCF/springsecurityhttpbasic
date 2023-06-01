
package com.back.portfolio1.Security.controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor

public class CreateUserDTO {
    
    @Email
    @NotBlank
    private String email; 
    
    @NotBlank
    private String username;
    
    @NotBlank
    private String password;
    
    
    private Set<String> roles;
            
            
    
}
