package it.si2001.rentalcar.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "vehicle")
@NoArgsConstructor
public class Vehicle {

    @Id
    @Column(length = 7)
    @NotBlank(message = "Plate is mandatory")
    private String plate;

    @Column(length = 45)
    @NotBlank(message = "Producer is mandatory")
    private String producer;

    @Column(length = 45)
    @NotBlank(message = "Model is mandatory")
    private String model;

    @NotNull(message = "Registration year is mandatory")
    private Integer registrationYear;

    @NotNull(message = "Availability is mandatory")
    private Byte availability;

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getRegistrationYear() {
        return registrationYear;
    }

    public void setRegistrationYear(Integer year) {
        this.registrationYear = year;
    }

    public Byte getAvailability() {
        return availability;
    }

    public void setAvailability(Byte availability) {
        this.availability = availability;
    }

    @OneToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    private User user;

    public void setUser(User user)
    {
        this.user = user;
    }

    public User getUser()
    {
        return user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Tipology tipology;
    public void setTipology(Tipology tipology)
    {
        this.tipology = tipology;
    }

    public Tipology getTipology()
    {
        return tipology;
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
