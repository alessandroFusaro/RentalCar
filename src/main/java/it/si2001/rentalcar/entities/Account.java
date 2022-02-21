package it.si2001.rentalcar.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "account")
@JsonIgnoreProperties(value = {"user"},allowSetters = true)
public class Account {

    @Id
    @NotNull(message = "Id is mandatory")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 45)
    @NotBlank(message = "Username is mandatory")
    private String username;

    @Column(length = 255)
    @NotBlank(message = "Password is mandatory")
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @OneToOne(targetEntity = User.class,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

    public void setUser(User u) {
        this.user = u;
    }

    public User getUser()
    {
        return user;
    }
}
