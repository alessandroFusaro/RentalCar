package it.si2001.rentalcar.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

@Entity
@Table(name = "user")
@NoArgsConstructor
//@JsonIgnoreProperties(value = {"vehicle","account","reservation"},allowSetters = true)
public class User {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 45)
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Column(length = 45)
    @NotBlank(message = "Surname is mandatory")
    private String surname;

    @Column(length = 45)
    @NotBlank(message = "Role is mandatory")
    private String role;


    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+1")
    @Past
    private Date date;

    @Column(name = "iban")
    private String iban;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setIban(String iban)
    {
        this.iban = iban;
    }

    public String getIban() {
        return iban;
    }

    @OneToOne(targetEntity = Account.class, cascade = CascadeType.ALL)
    private Account account;

    public void setAccount(Account account)
    {
        this.account = account;
    }

    public Account getAccount()
    {
        return account;
    }

    @OneToOne(targetEntity = Vehicle.class, cascade = CascadeType.ALL)
    private Vehicle vehicle;

    public void setVehicle(Vehicle vehicle)
    {
        this.vehicle = vehicle;
    }

    public Vehicle getVehicle()
    {
        return vehicle;
    }

    @OneToOne(targetEntity = Reservation.class, cascade = CascadeType.ALL)
    private Reservation reservation;

    public void setReservation(Reservation reservation)
    {
        this.reservation = reservation;
    }

    public Reservation getReservation()
    {
        return reservation;
    }


}
