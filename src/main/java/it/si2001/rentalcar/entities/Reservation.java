package it.si2001.rentalcar.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "reservation")
//@JsonIgnoreProperties(value = {"vehicle","user"},allowSetters = true)
@NoArgsConstructor
public class Reservation {

    @Id
    @NotNull(message = "Id is mandatory")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Start date is mandatory")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+1")
    private Date startDate;

    @NotNull(message = "End date is mandatory")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+1")
    @Future
    private Date endDate;

    @NotNull(message = "Approve is mandatory")
    private Byte approve;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Byte getApprove() {
        return approve;
    }

    public void setApprove(Byte approve) {
        this.approve = approve;
    }

    @OneToOne(targetEntity = User.class, cascade = CascadeType.REFRESH)
    private User user;

    public void setUser(User u) {
        this.user = u;
    }

    public User getUser() {
        return user;
    }

    @OneToOne(targetEntity = Vehicle.class, cascade = CascadeType.REFRESH)
    private Vehicle vehicle;

    public void setVehicle(Vehicle v) {
        this.vehicle = v;
    }

    public Vehicle getVehicle()
    {
        return vehicle;
    }

}
