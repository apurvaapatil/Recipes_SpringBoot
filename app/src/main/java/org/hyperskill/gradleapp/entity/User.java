package org.hyperskill.gradleapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    @JsonIgnore
    long id;
    @Column(name = "email")
    @NotBlank
    @Email
    @Pattern(regexp = "[a-z0-9]*@[a-z0-9]*.[a-z]{2,3}")
    String email;
    @Column(name = "password")
    @NotBlank
    @Size(min = 8)
    String password;

    public User() {
    }

    public User(long id, String email, String password, List<Long> recipeId) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
