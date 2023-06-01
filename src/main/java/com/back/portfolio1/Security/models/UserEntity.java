
package com.back.portfolio1.Security.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")

public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Email
    @NotBlank
    @Size(max = 80)
    private String email;
    
    @NotBlank
    @Size(max = 50)
    private String username; 
    
    @NotBlank
    private String password; 
    //no es recomendable colocar un maximo porque las contrasenias estan encriptadas
    
    
    //generar una relacion entre las tablas
    //los colection muy importantes
    //el set basicamente no nos permite tener elementos duplicados
    
    //ncesito que cuando consulte un usuario me traiga todos los roles asociados a ese usuario
    //cascade hace que si se borra un usuario no se borren los permisos para otros
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = RoleEntity.class, cascade = CascadeType.PERSIST) 
    
    //establecemos las llaves foraneas
    //cuando es una relacion muchos a muchos se genera una tabla intermedia, eso estoy haciendo
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id") )
    
    private Set<RoleEntity> roles;
    
    
    
}
