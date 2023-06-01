
package com.back.portfolio1.Security.repositories;

import com.back.portfolio1.Security.models.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    
   
   Optional<UserEntity> findByUsername(String username);
    
    @Query("select u from UserEntity u where u.username = ?1")
    Optional<UserEntity> getName(String username);
    
}
