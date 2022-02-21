package it.si2001.rentalcar.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "tipology")
//@JsonIgnoreProperties(value = {"vehicles"},allowSetters = true)
public class Tipology {

    @Id
    @NotNull(message = "Id is mandatory")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 45)
    @NotBlank(message = "Descriptio is mandatory")
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "plate")
    private List<Vehicle> vehicles;

    public void setVehicle(List<Vehicle> vehicle)
    {
        this.vehicles = vehicle;
    }

    public List<Vehicle> getVehicle()
    {
        return vehicles;
    }
}
