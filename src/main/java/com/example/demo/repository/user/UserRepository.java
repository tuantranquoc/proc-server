package com.example.demo.repository.user;

import com.example.demo.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
    public User findUserById(String id);
    
    public  User findUserByUsername(String username);
    
    public User findUserByEmail(String email);

    public User findUserByNameAndEmail(String name, String email);

    @Query(value="Select * from user where email =?1", nativeQuery = true)
    public Optional<User> findByEmail(String email);

    @Query(nativeQuery =true,value = "SELECT * FROM User as u WHERE u.email NOT IN (:emails)")   // 3. Spring JPA In cause using native query
    List<User> findByEmailList(@Param("emails") List<String> emails);


}
