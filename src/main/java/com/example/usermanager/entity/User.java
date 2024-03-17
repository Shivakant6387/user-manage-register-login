package com.example.usermanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    private int id;
    @Column(length = 50)
    @NotNull
    private String first_name;
    @Column(length = 50)
    @NotNull
    private String last_name;
    @Column(length = 255)
    @Email
    @NotNull
    private String email;
    @Column(length = 50)
    private String password;
    @Timestamp
    private Date created_at;
    @Transient
    @Timestamp
    private Date updated_at;
}
