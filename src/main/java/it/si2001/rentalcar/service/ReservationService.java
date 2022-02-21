package it.si2001.rentalcar.service;

import it.si2001.rentalcar.entities.Reservation;
import it.si2001.rentalcar.entities.User;
import it.si2001.rentalcar.entities.Vehicle;

import java.util.Date;
import java.util.List;

/*
 *
 * Service interface used to managing the reservation
 *
 */
public interface ReservationService
{

    /*
     *  @Param reservation is the reservation to add
     *
     *  this method add the reservation in the db
     */
    void addReservation(Reservation reservation);

    /*
     *  @Param id is the id of the reservation to find
     *
     *  return the found reservation
     */
    Reservation findReservationByReservationId(int id);

    /*
    *  @Param vehicle is the vehicle used to find all the reservation related to it
    *
    *  this method delete all the reservation related to the same vehicle
    *
    * */
    void removeAllByVehicle(Vehicle vehicle);

    /*
     *
     *  @Param user is the user used to find all the reservation related to it
     *
     */
    List<Reservation> listUserReservation(User user);

    /*
    *  @Param id is the id used to delete a reservation
    *
    *  this method delete a reservation from the id
    *
    * */
    void deleteReservationById(int id);

    /*
    *  return a list of all the reservation
    * */
    List<Reservation> listReservation();


    /*
    *  @Param id is the id of the reservation to control
    *
    *  @Param date is the current date
    *
    *  return true if the reservation isn't active, false if is active
    * */
    boolean isActive(int id,Date date);


    /*
    *  @Param reservation is the reservation to update
    *
    *  this method update a specific reservation
    * */
    void updateReservation(Reservation reservation);


    /*
    *  @Param vehicle is the vehicle used to control if exists reservation related to it
    *
    *  return true if exists reservation related to the vehicle, false if not exists
    * */
    boolean existsReservationByVehicle(Vehicle vehicle);


    /*
    *  @Param user is the user used to control if he have reservations active
    *
    *  return true if the user have reservation active otherwise false
    * */
    boolean reservationActiveByUser(User user);

    List<Reservation> reservationByVehicle(Vehicle vehicle);

    boolean reservationIsActive(int id,Date date, Date date2);

}
