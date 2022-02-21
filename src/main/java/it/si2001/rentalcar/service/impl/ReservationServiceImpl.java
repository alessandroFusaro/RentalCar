package it.si2001.rentalcar.service.impl;

import it.si2001.rentalcar.entities.Reservation;
import it.si2001.rentalcar.entities.User;
import it.si2001.rentalcar.entities.Vehicle;
import it.si2001.rentalcar.repository.ReservationRepository;
import it.si2001.rentalcar.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/*
*
* Implementation of the ReservatioService interface
*
* */

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService{

    @Autowired
    ReservationRepository reservationRepository;

    /*
     *  @Param reservation is the reservation to add
     *
     *  this method add the reservation in the db
     */
    @Override
    public void addReservation(Reservation reservation) {
        reservationRepository.saveAndFlush(reservation);
    }


    /*
     *  @Param id is the id of the reservation to find
     *
     *  return the found reservation
     */
    @Override
    public Reservation findReservationByReservationId(int id) {
        return reservationRepository.findReservationById(id);
    }


    /*
     *  @Param vehicle is the vehicle used to find all the reservation related to it
     *
     *  this method delete all the reservation related to the same vehicle
     *
     * */
    @Override
    public void removeAllByVehicle(Vehicle vehicle) {
        reservationRepository.removeAllByVehicle(vehicle);
    }


    /*
     *
     *  @Param user is the user used to find all the reservation related to it
     *
     */
    @Override
    public List<Reservation> listUserReservation(User user) {
        return reservationRepository.findAllByUser(user);
    }


    /*
     *  @Param id is the id used to delete a reservation
     *
     *  this method delete a reservation from the id
     *
     * */
    @Override
    public void deleteReservationById(int id) {
        reservationRepository.deleteReservationById(id);
    }


    /*
     *  return a list of all the reservation
     * */
    @Override
    public List<Reservation> listReservation() {
        return reservationRepository.findAll();
    }


    /*
     *  @Param id is the id of the reservation to control
     *
     *  @Param date is the current date
     *
     *  return true if the reservation isn't active, false if is active
     * */
    @Override
    public boolean isActive(int id,Date date) {
        return reservationRepository.existsReservationByIdAndStartDateIsAfter(id,date);
    }


    /*
     *  @Param reservation is the reservation to update
     *
     *  this method update a specific reservation
     * */
    @Override
    public void updateReservation(Reservation reservation) {

        reservationRepository.save(reservation);
    }

    /*
     *  @Param vehicle is the vehicle used to control if exists reservation related to it
     *
     *  return true if exists reservation related to the vehicle, false if not exists
     * */
    @Override
    public boolean existsReservationByVehicle(Vehicle vehicle) {
        return reservationRepository.existsReservationByVehicleAndStartDateIsAfterOrEndDateIsAfterAndVehicle_Plate(vehicle,new Date(), new Date(),vehicle.getPlate());

    }


    /*
     *  @Param user is the user used to control if he have reservations active
     *
     *  return true if the user have reservation active otherwise false
     * */
    @Override
    public boolean reservationActiveByUser(User user) {
        return reservationRepository.existsReservationByUserAndStartDateIsAfterOrEndDateIsAfterAndUser_Id(user, new Date(), new Date(), user.getId());
    }

    @Override
    public List<Reservation> reservationByVehicle(Vehicle vehicle) {
        return reservationRepository.findReservationByVehicle(vehicle);
    }

    @Override
    public boolean reservationIsActive(int id,Date date, Date date2) {
        return reservationRepository.existsReservationByIdAndStartDateIsAfterOrEndDateIsBefore(id,date, date2);
    }

}
