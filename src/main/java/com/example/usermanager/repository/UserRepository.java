package com.example.usermanager.repository;

import com.example.usermanager.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query(value = "SELECT email FROM  users WHERE email=:email", nativeQuery = true)
    List<String> checkUserEmail(@Param("email") String email);

    @Query(value = "SELECT password FROM users WHERE email=:email", nativeQuery = true)
    String checkPasswordByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM users WHERE email=:email", nativeQuery = true)
    User getUserDetailsByEmail(@Param("email") String email);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO USERS (first_name, last_name, email, password, created_at) VALUES (:first_name, :last_name, :email, :password,:created_at)", nativeQuery = true)
    int registerNewUser(@Param("first_name") String first_name,
                        @Param("last_name") String last_name,
                        @Param("email") String email,
                        @Param("password") String password,
                        @Param("created_at") Date created_at);
    @Modifying
    @Query(value = "UPDATE users SET email = :newEmail WHERE email = :oldEmail,updated_at=:updated_at", nativeQuery = true)
    void updateUserEmail(@Param("oldEmail") String oldEmail, @Param("newEmail") String newEmail,@Param("updated_at")Date updated_at);
    @Modifying
    @Query(value = "UPDATE users SET password = :password WHERE email = :email,updated_at=:updated_at", nativeQuery = true)
    void updateUserPassword(@Param("email") String email, @Param("password") String hashedNewPassword,@Param("updated_at")Date updated_at);
}
